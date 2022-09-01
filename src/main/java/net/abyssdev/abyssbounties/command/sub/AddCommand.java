package net.abyssdev.abyssbounties.command.sub;

import net.abyssdev.abyssbounties.AbyssBounties;
import net.abyssdev.abyssbounties.bounty.Bounty;
import net.abyssdev.abyssbounties.utils.BountyUtils;
import net.abyssdev.abysslib.command.SubCommand;
import net.abyssdev.abysslib.command.context.CommandContext;
import net.abyssdev.abysslib.placeholder.PlaceholderReplacer;
import net.abyssdev.abysslib.utils.Utils;
import org.bukkit.entity.Player;
import org.eclipse.collections.api.factory.Sets;

import java.util.Set;

/**
 * The sub command for adding bounties
 * @author Relocation
 */
public final class AddCommand extends SubCommand {

    private final AbyssBounties plugin;
    private final Set<String> aliases = Sets.immutable.of("add").castToSet();

    /**
     * Constructs a new AddCommand
     * @param plugin The abyss bounties plugin
     */
    public AddCommand(final AbyssBounties plugin) {
        super(3, plugin.getMessageCache().getMessage("messages.player-help"));

        this.plugin = plugin;
    }

    @Override
    public Set<String> aliases() {
        return this.aliases;
    }

    @Override
    public void execute(final CommandContext<?> context) {

        final Player target = context.asPlayer(0);
        final String currency = context.asString(2);

        if (target == null || !context.isDouble(1) || !this.plugin.getEconomyRegistry().containsKey(currency)) {
            this.getInvalid().send(context.getSender());
            return;
        }

        final Player player = context.getSender();
        final Bounty bounty;

        if (this.plugin.getBountyStorage().contains(player.getUniqueId())) {
            bounty = this.plugin.getBountyStorage().get(player.getUniqueId());
        } else {
            bounty = new Bounty(target.getUniqueId());

            this.plugin.getMessageCache().getMessage("messages.bounty-created").broadcast(new PlaceholderReplacer()
                    .addPlaceholder("%target%", target.getName())
                    .addPlaceholder("%setter%", player.getName())
                    .addPlaceholder("%amount%", Utils.format(context.asDouble(1)))
                    .addPlaceholder("%currency%", this.plugin.getEconomyRegistry().get(currency).get().getFormattedName()));

            this.plugin.getBountyStorage().save(bounty);
        }

        this.plugin.getMessageCache().getMessage("messages.bounty-added").broadcast(new PlaceholderReplacer()
                .addPlaceholder("%target%", target.getName())
                .addPlaceholder("%setter%", player.getName())
                .addPlaceholder("%amount%", Utils.format(context.asDouble(1)))
                .addPlaceholder("%currency%", this.plugin.getEconomyRegistry().get(currency).get().getFormattedName()));

        BountyUtils.addCurrency(player.getUniqueId(), bounty, currency, context.asDouble(1));

    }

}