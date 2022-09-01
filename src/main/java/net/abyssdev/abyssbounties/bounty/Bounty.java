package net.abyssdev.abyssbounties.bounty;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.abyssdev.abysslib.storage.id.Id;
import org.eclipse.collections.api.factory.Maps;

import java.util.Map;
import java.util.UUID;

/**
 * The bounty object
 * @author Relocation
 */
@Getter
@RequiredArgsConstructor
public final class Bounty {

    @Id @dev.morphia.annotations.Id
    private final UUID target;
    private final Map<UUID, Map<String, Double>> contributors = Maps.mutable.empty();
    private final Map<String, Double> rewards = Maps.mutable.empty();

}