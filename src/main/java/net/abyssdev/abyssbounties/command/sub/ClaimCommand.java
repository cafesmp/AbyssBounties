package net.abyssdev.abyssbounties.command.sub;

import net.abyssdev.abyssbounties.AbyssBounties;
import net.abyssdev.abyssbounties.economy.BountyEconomy;
import net.abyssdev.abysslib.command.SubCommand;
import net.abyssdev.abysslib.command.context.CommandContext;
import net.abyssdev.abysslib.nbt.NBTUtils;
import net.abyssdev.abysslib.placeholder.PlaceholderReplacer;
import net.abyssdev.abysslib.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.eclipse.collections.api.factory.Sets;

import java.util.Map;
import java.util.Set;

/**
 * The sub command for claiming bounties
 * @author Relocation
 */
public final class ClaimCommand extends SubCommand {

    private final AbyssBounties plugin;
    private final Set<String> aliases = Sets.immutable.of("claim").castToSet();

    /**
     * Constructs a new ClaimCommand
     * @param plugin The abyss bounties plugin
     */
    public ClaimCommand(final AbyssBounties plugin) {
        super(1, plugin.getMessageCache().getMessage("messages.admin-help"));

        this.plugin = plugin;
    }

    @Override
    public Set<String> aliases() {
        return this.aliases;
    }

    @Override
    public void execute(final CommandContext<?> context) {

        final Player player = context.getSender();

        if (!player.hasPermission("abyssbounties.admin")) {
            this.plugin.getMessageCache().sendMessage(player, "messages.no-permission");
            return;
        }

        final Player target = context.asPlayer(0);

        if (target == null) {
            this.getInvalid().send(context.getSender());
            return;
        }


        for (final ItemStack item : target.getInventory().getContents()) {
            if (item == null || item.getType() == Material.AIR) {
                continue;
            }

            if (!NBTUtils.get().contains(item, "ABYSSBOUNTY")) {
                continue;
            }

            final PlaceholderReplacer replacer = new PlaceholderReplacer();

            for (final Map.Entry<String, BountyEconomy> entry : this.plugin.getEconomyRegistry().entrySet()) {
                final String name = entry.getKey();
                final BountyEconomy economy = entry.getValue();

                if (!NBTUtils.get().contains(item, name + "-ABYSSBOUNTY")) {
                    replacer.addPlaceholder("%" + name + "%", "0");
                    continue;
                }

                final double amount = NBTUtils.get().getDouble(item, name + "-ABYSSBOUNTY");

                economy.getEconomy().addBalance(target, amount);
                replacer.addPlaceholder("%" + name + "%", Utils.format(amount));
            }

            this.plugin.getMessageCache().sendMessage(target, "messages.sold-head", replacer);
            target.getInventory().remove(item);
        }

    }
}