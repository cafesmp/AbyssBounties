package net.abyssdev.abyssbounties.menu;

import net.abyssdev.abyssbounties.AbyssBounties;
import net.abyssdev.abyssbounties.bounty.Bounty;
import net.abyssdev.abyssbounties.economy.BountyEconomy;
import net.abyssdev.abyssbounties.menu.item.PageItem;
import net.abyssdev.abysslib.builders.ItemBuilder;
import net.abyssdev.abysslib.builders.PageBuilder;
import net.abyssdev.abysslib.menu.MenuBuilder;
import net.abyssdev.abysslib.menu.templates.AbyssMenu;
import net.abyssdev.abysslib.placeholder.PlaceholderReplacer;
import net.abyssdev.abysslib.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The paginated bounty menu
 * @author Relocation
 */
public final class BountyMenu extends AbyssMenu {

    private final AbyssBounties plugin;
    private final PageItem next, prev, current;
    private final IntArrayList slots = new IntArrayList();
    private final ItemBuilder bountyItem;

    /**
     * Constructs a new BountyMenu
     * @param plugin The abyss bounties plugin
     */
    public BountyMenu(final AbyssBounties plugin) {
        super(plugin.getConfig("menu"), "");

        this.plugin = plugin;

        final FileConfiguration config = plugin.getConfig("menu");

        for (final int slot : config.getIntegerList("head-slots")) {
            this.slots.add(slot);
        }

        this.next = new PageItem(new ItemBuilder(config, "next-page"), config.getInt("next-page.slot"));
        this.prev = new PageItem(new ItemBuilder(config, "prev-page"), config.getInt("prev-page.slot"));
        this.current = new PageItem(new ItemBuilder(config, "current-page"), config.getInt("current-page.slot"));
        this.bountyItem = new ItemBuilder(config, "bounty-item");
    }

    public void open(final Player player, final int page) {

        final MenuBuilder builder = this.createBase();
        final PageBuilder<Bounty> pageBuilder = new PageBuilder<>(
                new ArrayList<>(this.plugin.getBountyStorage().allValues()),
                this.slots.size());

        final PlaceholderReplacer replacer = new PlaceholderReplacer()
                .addPlaceholder("%page%", Utils.format(page + 1));

        builder.setItem(this.next.getSlot(), this.next.getItem().parse(replacer));
        builder.setItem(this.prev.getSlot(), this.prev.getItem().parse(replacer));
        builder.setItem(this.current.getSlot(), this.current.getItem().parse(replacer));

        builder.addClickEvent(this.next.getSlot(), event -> {
            if (pageBuilder.hasPage(page + 1)) {
                this.open(player, page + 1);
            }
        });

        builder.addClickEvent(this.prev.getSlot(), event -> {
            if (page - 1 > -1) {
                this.open(player, page - 1);
            }
        });

        final List<Bounty> bounties = pageBuilder.getPage(page);
        int index = 0;

        for (final int slot : this.slots.toArray()) {
            if (index >= bounties.size()) {
                break;
            }

            final Bounty bounty = bounties.get(index);

            index++;

            final PlaceholderReplacer placeholders = new PlaceholderReplacer()
                    .addPlaceholder("%player%", Bukkit.getOfflinePlayer(bounty.getTarget()).getName());

            for (final Map.Entry<String, BountyEconomy> entry : this.plugin.getEconomyRegistry().entrySet()) {
                placeholders.addPlaceholder("%" + entry.getKey() + "%", Utils.format(bounty.getRewards().getOrDefault(entry.getKey(), 0.0)));
            }

            builder.setItem(slot, this.bountyItem.parse(placeholders));
        }

        player.openInventory(builder.build());
    }
}