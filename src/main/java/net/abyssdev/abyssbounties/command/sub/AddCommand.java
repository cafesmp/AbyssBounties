package net.abyssdev.abyssbounties.command.sub;

import net.abyssdev.abyssbounties.AbyssBounties;
import net.abyssdev.abyssbounties.bounty.Bounty;
import net.abyssdev.abyssbounties.economy.BountyEconomy;
import net.abyssdev.abyssbounties.utils.BountyUtils;
import net.abyssdev.abysslib.command.SubCommand;
import net.abyssdev.abysslib.command.context.CommandContext;
import net.abyssdev.abysslib.placeholder.PlaceholderReplacer;
import net.abyssdev.abysslib.utils.Utils;
import org.bukkit.entity.Player;
import org.eclipse.collections.api.factory.Sets;

import java.util.Optional;
import java.util.Set;

/**
 * The sub command for adding bounties
 * @author Relocation
 */
public final class AddCommand extends SubCommand {

    private final AbyssBounties plugin;
    private final Set<String> aliases = Sets.immutable.of("add", "set", "create").castToSet();

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

        if (target == null || !context.isDouble(1) || context.asDouble(1) <= 0 || !this.plugin.getEconomyRegistry().containsKey(currency)) {
            this.getInvalid().send(context.getSender());
            return;
        }

        final Player player = context.getSender();
        final BountyEconomy economy = this.plugin.getEconomyRegistry().get(currency).get();

        if (!economy.getEconomy().hasBalance(player, context.asDouble(1))) {
            this.plugin.getMessageCache().sendMessage(player, "messages.not-enough");
            return;
        }

        economy.getEconomy().withdrawBalance(player, context.asDouble(1));

        final Bounty bounty;

        if (this.plugin.getBountyStorage().contains(target.getUniqueId())) {
            bounty = this.plugin.getBountyStorage().get(target.getUniqueId());

            this.plugin.getMessageCache().getMessage("messages.bounty-added").broadcast(new PlaceholderReplacer()
                    .addPlaceholder("%victim%", target.getName())
                    .addPlaceholder("%setter%", player.getName())
                    .addPlaceholder("%amount%", Utils.format(context.asDouble(1)))
                    .addPlaceholder("%currency%", economy.getFormattedName()));
        } else {
            bounty = new Bounty(target.getUniqueId());

            this.plugin.getMessageCache().getMessage("messages.bounty-created").broadcast(new PlaceholderReplacer()
                    .addPlaceholder("%victim%", target.getName())
                    .addPlaceholder("%setter%", player.getName())
                    .addPlaceholder("%amount%", Utils.format(context.asDouble(1)))
                    .addPlaceholder("%currency%", economy.getFormattedName()));

            this.plugin.getBountyStorage().save(bounty);
        }

        BountyUtils.addCurrency(player.getUniqueId(), bounty, economy.getName(), context.asDouble(1));

    }
}