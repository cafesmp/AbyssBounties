package net.abyssdev.abyssbounties.bounty.storage;

import net.abyssdev.abyssbounties.AbyssBounties;
import net.abyssdev.abyssbounties.bounty.Bounty;
import net.abyssdev.abyssbounties.bounty.registry.BountyRegistry;
import net.abyssdev.abysslib.storage.json.JsonStorage;
import net.abyssdev.abysslib.utils.file.Files;

import java.util.UUID;

/**
 * The bounty json storage
 * @author Relocation
 */
public final class BountyStorage extends JsonStorage<UUID, Bounty> {

    /**
     * Constructs a new BountyStorage
     * @param plugin The abyss bounties plugin
     */
    public BountyStorage(final AbyssBounties plugin) {
        super(Files.file("data.json", plugin), Bounty.class, new BountyRegistry());
    }

    @Override
    public Bounty constructValue(final UUID key) {
        return new Bounty(key);
    }

}