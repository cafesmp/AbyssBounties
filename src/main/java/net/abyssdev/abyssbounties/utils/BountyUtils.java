package net.abyssdev.abyssbounties.utils;

import com.cryptomorin.xseries.XMaterial;
import lombok.experimental.UtilityClass;
import net.abyssdev.abyssbounties.AbyssBounties;
import net.abyssdev.abyssbounties.bounty.Bounty;
import net.abyssdev.abyssbounties.economy.BountyEconomy;
import net.abyssdev.abysslib.builders.ItemBuilder;
import net.abyssdev.abysslib.nbt.NBTUtils;
import net.abyssdev.abysslib.text.Color;
import net.abyssdev.abysslib.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The bounty utils
 * @author Relocation
 */
@UtilityClass
public final class BountyUtils {

    private static final List<String> SKULL_FILTERS = Lists.immutable.of("SKULL_ITEM", "PLAYER_HEAD").castToList();

    private static ItemStack bountyItem;
    private static String itemName, currencyFormat;
    private static List<String> itemLore;

    public void addCurrency(final UUID uuid, final Bounty bounty, final String currency, final double amount) {
        bounty.getRewards().put(currency, bounty.getRewards().getOrDefault(currency, 0.0) + amount);

        if (bounty.getContributors().containsKey(uuid)) {
            final Map<String, Double> currencies = bounty.getContributors().get(uuid);
            currencies.put(currency, currencies.getOrDefault(currency, 0.0) + amount);
        } else {
            final Map<String, Double> currencies = Maps.mutable.empty();
            currencies.put(currency, amount);
            bounty.getContributors().put(uuid, currencies);
        }
    }

    public void takeCurrency(final Bounty bounty, final String currency, final double amount) {
        final double total = bounty.getRewards().getOrDefault(currency, 0.0) - amount;
        bounty.getRewards().put(currency, total < 0 ? 0 : total);
    }

    public ItemStack createBountySkull(final Player victim, final Bounty bounty) {

        ItemStack item = bountyItem.clone();

        for (final Map.Entry<String, Double> entry : bounty.getRewards().entrySet()) {
            item = NBTUtils.get().setDouble(item, entry.getKey() + "-ABYSSBOUNTY", entry.getValue());
        }

        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(itemName.replace("%player%", victim.getName()));

        final List<String> lore = Lists.mutable.empty();

        for (final String line : itemLore) {
            if (!line.contains("%currencies%")) {
                lore.add(line);
                continue;
            }

            for (final Map.Entry<String, Double> entry : bounty.getRewards().entrySet()) {
                final BountyEconomy economy = AbyssBounties.get().getEconomyRegistry().get(entry.getKey()).get();

                lore.add(currencyFormat
                        .replace("%currency%", economy.getFormattedName())
                        .replace("%amount%", Utils.format(entry.getValue())));
            }
        }

        meta.setLore(lore);

        if (XMaterial.matchXMaterial(item.getType()).isOneOf(SKULL_FILTERS)) {
            ((SkullMeta) meta).setOwner(victim.getName());
        }

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Do not run this. It is only used on start.
     */
    public void load(final AbyssBounties plugin) {
        bountyItem = new ItemBuilder(plugin.getItemConfig(), "").parse();

        NBTUtils.get().setString(bountyItem, "ABYSSBOUNTY", "");

        itemName = Color.parse(plugin.getItemConfig().getString("name"));
        itemLore = Color.parse(plugin.getItemConfig().getStringList("lore"));
        currencyFormat = Color.parse(plugin.getItemConfig().getString("currency-format"));
    }

}