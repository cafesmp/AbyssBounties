package net.abyssdev.abyssbounties.command;

import net.abyssdev.abyssbounties.AbyssBounties;
import net.abyssdev.abysslib.command.Command;
import net.abyssdev.abysslib.command.context.CommandContext;
import org.bukkit.entity.Player;
import org.eclipse.collections.api.factory.Lists;

/**
 * The abyss bounties base command
 * @author Relocation
 */
public final class BountyCommand extends Command<Player> {

    private final AbyssBounties plugin;

    /**
     * Constructs a new BountyCommand
     * @param plugin The abyss bounties plugin
     */
    public BountyCommand(final AbyssBounties plugin) {
        super("bounty",
                "The main bounty command for Abyss Bounties",
                Lists.immutable.of("bounties",
                        "abyssbounties",
                        "abyssbounty",
                        "abounty",
                        "abounties").castToList(),
                Player.class);

        this.plugin = plugin;
    }

    @Override
    public void execute(final CommandContext<Player> context) {
        final Player player = context.getSender();

        if (context.getArguments().length == 0) {
            this.plugin.getBountyMenu().open(player, 0);
            return;
        }

        if (!player.hasPermission("abyssbounties.admin")) {
            this.plugin.getMessageCache().sendMessage(player, "messages.player-help");
            return;
        }

        this.plugin.getMessageCache().sendMessage(player, "messages.admin-help");
    }
}