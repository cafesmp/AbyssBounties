package net.abyssdev.abyssbounties.bounty.registry;

import net.abyssdev.abyssbounties.bounty.Bounty;
import net.abyssdev.abysslib.patterns.registry.Registry;
import org.eclipse.collections.api.factory.Maps;

import java.util.Map;
import java.util.UUID;

/**
 * The bounty registry
 * @author Relocation
 */
public final class BountyRegistry implements Registry<UUID, Bounty> {

    private final Map<UUID, Bounty> bounties = Maps.mutable.empty();

    @Override
    public Map<UUID, Bounty> getRegistry() {
        return this.bounties;
    }
}