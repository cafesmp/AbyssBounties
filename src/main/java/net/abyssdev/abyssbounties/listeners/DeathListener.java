package net.abyssdev.abyssbounties.listeners;

import net.abyssdev.abyssbounties.AbyssBounties;
import net.abyssdev.abyssbounties.utils.BountyUtils;
import net.abyssdev.abysslib.listener.AbyssListener;
import net.abyssdev.abysslib.placeholder.PlaceholderReplacer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * The death listener
 * @author Relocation
 */
public final class DeathListener extends AbyssListener<AbyssBounties> {

    /**
     * Constructs a new DeathListener
     * @param plugin The abyss bounties plugin
     */
    public DeathListener(final AbyssBounties plugin) {
        super(plugin);
    }

    @EventHandler
    public void onDeath(final PlayerDeathEvent event) {

        final Player player = event.getEntity();
        final Player killer = player.getKiller();

        if (killer == null) {
            return;
        }

        if (!this.getPlugin().getBountyStorage().contains(player.getUniqueId())) {
            return;
        }

        player.getWorld().dropItemNaturally(
                player.getLocation(),
                BountyUtils.createBountySkull(player, this.getPlugin().getBountyStorage().get(player.getUniqueId())));

        this.getPlugin().getBountyStorage().remove(player.getUniqueId());

        this.getPlugin().getMessageCache().getMessage("messages.bounty-dropped").broadcast(new PlaceholderReplacer()
                .addPlaceholder("%victim%", player.getName())
                .addPlaceholder("%killer%", killer.getName()));
    }
}