package net.abyssdev.abyssbounties.economy;

import lombok.Getter;
import net.abyssdev.abysslib.economy.provider.Economy;
import net.abyssdev.abysslib.economy.registry.impl.DefaultEconomyRegistry;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * The bounty economy wrapper
 * @author Relocation
 */
@Getter
public final class BountyEconomy {

    private final String name, formattedName;
    private final Economy economy;

    /**
     * Constructs a new BountyEconomy
     * @param config The bounty economy config
     * @param name The economy name
     */
    public BountyEconomy(final FileConfiguration config, final String name) {
        this.name = name;
        this.formattedName = config.getString("currencies." + name + ".formatted-name");

        if (!DefaultEconomyRegistry.get().hasEconomy(name)) {
            throw new NullPointerException("Cannot find economy with name " + name + ". Please ensure your currencies.yml is properly setup.");
        }

        this.economy = DefaultEconomyRegistry.get().getEconomy(name);
    }

}