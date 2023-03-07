package net.abyssdev.abyssbounties.command.sub;

import net.abyssdev.abyssbounties.AbyssBounties;
import net.abyssdev.abyssbounties.bounty.Bounty;
import net.abyssdev.abysslib.command.SubCommand;
import net.abyssdev.abysslib.command.context.CommandContext;
import net.abyssdev.abysslib.placeholder.PlaceholderReplacer;
import net.abyssdev.abysslib.scheduler.AbyssScheduler;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.eclipse.collections.api.factory.Sets;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * The sub command for removing bounties
 * @author Relocation
 */
public final class RemoveCommand extends SubCommand {

    private final AbyssBounties plugin;
    private final Set<String> aliases = Sets.immutable.of("remove", "delete").castToSet();

    /**
     * Constructs a new RemoveCommand
     * @param plugin The abyss bounties plugin
     */
    public RemoveCommand(final AbyssBounties plugin) {
        super(1, plugin.getMessageCache().getMessage("messages.player-help"));

        this.plugin = plugin;
    }

    @Override
    public Set<String> aliases() {
        return this.aliases;
    }

    @Override
    public void execute(final CommandContext<?> context) {

        final Player player = context.getSender();

        CompletableFuture.runAsync(() -> {

            final OfflinePlayer target = context.asOfflinePlayer(0);

            if (!target.hasPlayedBefore()) {
                this.plugin.getMessageCache().sendMessage(player, "messages.cant-remove");
                return;
            }

            AbyssScheduler.sync().run(() -> {
                if (!this.plugin.getBountyStorage().contains(target.getUniqueId())) {
                    this.plugin.getMessageCache().sendMessage(player, "messages.cant-remove");
                    return;
                }

                final Bounty bounty = this.plugin.getBountyStorage().get(target.getUniqueId());

                if (!bounty.getContributors().containsKey(player.getUniqueId())) {
                    this.plugin.getMessageCache().sendMessage(player, "messages.cant-remove");
                    return;
                }

                for (final Map.Entry<String, Double> entry : bounty.getContributors().get(player.getUniqueId()).entrySet()) {
                    final String currency = entry.getKey();
                    final double amount = entry.getValue();

                    if (!bounty.getRewards().containsKey(currency)) {
                        continue;
                    }

                    final double value = bounty.getRewards().get(currency);

                    this.plugin.getEconomyRegistry().get(currency).get().getEconomy().addBalance(player, value);

                    if (value - amount <= 0) {
                        bounty.getRewards().remove(currency);
                        continue;
                    }

                    bounty.getRewards().put(currency, value - amount);
                }

                bounty.getContributors().remove(player.getUniqueId());
                this.plugin.getBountyStorage().remove(target.getUniqueId());

                this.plugin.getMessageCache().sendMessage(player, "messages.bounty-removed", new PlaceholderReplacer()
                        .addPlaceholder("%player%", target.getName()));
            });

        });


    }
}