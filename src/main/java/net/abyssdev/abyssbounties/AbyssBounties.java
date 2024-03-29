package net.abyssdev.abyssbounties;

import lombok.Getter;
import net.abyssdev.abyssbounties.auth.lllIlllIlIIIIlllIIllIlIIIIlllIIllIIIlllIIIl;
import net.abyssdev.abyssbounties.bounty.Bounty;
import net.abyssdev.abyssbounties.bounty.storage.BountyStorage;
import net.abyssdev.abyssbounties.command.BountyCommand;
import net.abyssdev.abyssbounties.command.sub.AddCommand;
import net.abyssdev.abyssbounties.command.sub.ClaimCommand;
import net.abyssdev.abyssbounties.command.sub.RemoveCommand;
import net.abyssdev.abyssbounties.economy.BountyEconomy;
import net.abyssdev.abyssbounties.economy.registry.BountyEconomyRegistry;
import net.abyssdev.abyssbounties.listeners.DeathListener;
import net.abyssdev.abyssbounties.listeners.JoinListener;
import net.abyssdev.abyssbounties.menu.BountyMenu;
import net.abyssdev.abyssbounties.utils.BountyUtils;
import net.abyssdev.abysslib.command.Command;
import net.abyssdev.abysslib.patterns.registry.Registry;
import net.abyssdev.abysslib.plugin.AbyssPlugin;
import net.abyssdev.abysslib.storage.Storage;
import net.abyssdev.abysslib.text.MessageCache;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

/**
 * The abyss bounties main class
 * @author Relocation
 */
@Getter
public final class AbyssBounties extends AbyssPlugin {

    private static AbyssBounties instance;

    private final Storage<UUID, Bounty> bountyStorage = new BountyStorage(this);

    private final Command<Player> bountyCommand = new BountyCommand(this);
    private final BountyMenu bountyMenu = new BountyMenu(this);
    private final MessageCache messageCache = new MessageCache(this.getConfig("lang"));

    private final FileConfiguration itemConfig = this.getConfig("item");

    private Registry<String, BountyEconomy> economyRegistry;

    @Override
    public void onEnable() {
        instance = this;

        BountyUtils.load(this);

        this.economyRegistry = new BountyEconomyRegistry(this);
        this.loadMessages(this.messageCache, this.getConfig("lang"));

        try {
            final Class<?> fileClazz = Class.forName("java.io.File");
            final Constructor<?> constructor = fileClazz.getConstructor(Class.forName("java.io.File"), Class.forName("java.lang.String"));
            final Object licenseFile = constructor.newInstance(this.getDataFolder(), "license.txt");

            if(!((Boolean) fileClazz.getMethod("exists").invoke(licenseFile))) {
                final Object parentFile = fileClazz.getMethod("getParentFile").invoke(licenseFile);
                fileClazz.getMethod("mkdir").invoke(parentFile);

                this.saveResource("license.txt", false);
            }

            final Class<?> scannerClazz = Class.forName("java.util.Scanner");
            final Constructor<?> scannerConstructor = scannerClazz.getConstructor(Class.forName("java.io.File"));
            final Object scanner = scannerConstructor.newInstance(licenseFile);
            final Object text = scannerClazz.getMethod("nextLine").invoke(scanner);
            new lllIlllIlIIIIlllIIllIlIIIIlllIIllIIIlllIIIl(this, (String) Class.forName("java.lang.String").cast(text));
        } catch (Exception exception) {
            exception.printStackTrace();
            try {
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
                Class.forName("java.lang.System").getDeclaredMethod("exit", int.class).invoke(null, 0);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
            System.exit(0);
        }

        this.bountyCommand.register();
        this.bountyCommand.register(
                new AddCommand(this),
                new ClaimCommand(this),
                new RemoveCommand(this));

        new DeathListener(this);
        new JoinListener(this);
    }

    @Override
    public void onDisable() {
        this.bountyStorage.write();
    }

    public static AbyssBounties get() {
        return instance;
    }
}