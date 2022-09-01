package net.abyssdev.abyssbounties.economy.registry;

import net.abyssdev.abyssbounties.AbyssBounties;
import net.abyssdev.abyssbounties.economy.BountyEconomy;
import net.abyssdev.abysslib.patterns.registry.Registry;
import org.bukkit.configuration.file.FileConfiguration;
import org.eclipse.collections.api.factory.Maps;

import java.util.Map;

/**
 * The bounty economy registry
 * @author Relocation
 */
public final class BountyEconomyRegistry implements Registry<String, BountyEconomy> {

    private final Map<String, BountyEconomy> currencies = Maps.mutable.empty();

    /**
     * Constructs a new BountyEconomyRegistry
     * @param plugin The abyss bounties plugin
     */
    public BountyEconomyRegistry(final AbyssBounties plugin) {
        final FileConfiguration config = plugin.getConfig("currencies");

        for (final String key : config.getConfigurationSection("currencies").getKeys(false)) {
            final BountyEconomy economy = new BountyEconomy(config, key);

            this.currencies.put(key, economy);

            for (final String alias : config.getStringList("currencies." + key + ".aliases")) {
                this.currencies.put(alias, economy);
            }
        }
    }

    @Override
    public Map<String, BountyEconomy> getRegistry() {
        return this.currencies;
    }

}