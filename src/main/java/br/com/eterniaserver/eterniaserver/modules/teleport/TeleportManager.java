package br.com.eterniaserver.eterniaserver.modules.teleport;

import br.com.eterniaserver.eternialib.EterniaLib;
import br.com.eterniaserver.eternialib.database.Entity;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.modules.Module;
import br.com.eterniaserver.eterniaserver.enums.Strings;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class TeleportManager implements Module {

    private final Services.HomeService homeService;
    private final Services.WarpService warpService;
    private final EterniaServer plugin;

    public TeleportManager(EterniaServer plugin) {
        this.plugin = plugin;
        this.homeService = new Services.HomeService(plugin);
        this.warpService = new Services.WarpService(plugin);
    }

    @Override
    public void loadConfigurations() {
        Configurations.TeleportConfiguration configuration = new Configurations.TeleportConfiguration(plugin);
        Configurations.TeleportMessagesConfiguration messages = new Configurations.TeleportMessagesConfiguration(plugin.messages());
        Configurations.TeleportCommandsConfiguration commands = new Configurations.TeleportCommandsConfiguration();

        EterniaLib.getCfgManager().registerConfiguration("eterniaserver", "teleport", true, configuration);
        EterniaLib.getCfgManager().registerConfiguration("eterniaserver", "teleport_messages", true, messages);
        EterniaLib.getCfgManager().registerConfiguration("eterniaserver", "teleport_commands", true, commands);

        try {
            Entity<Entities.HomeLocation> homeLocationEntity = new Entity<>(Entities.HomeLocation.class);
            Entity<Entities.WarpLocation> warpLocationEntity = new Entity<>(Entities.WarpLocation.class);

            EterniaLib.getDatabase().addTableName("%eternia_home%", plugin.getString(Strings.TELEPORT_TABLE_NAME_HOME));
            EterniaLib.getDatabase().addTableName("%eternia_warp%", plugin.getString(Strings.TELEPORT_TABLE_NAME_WARP));

            EterniaLib.getDatabase().register(Entities.HomeLocation.class, homeLocationEntity);
            EterniaLib.getDatabase().register(Entities.WarpLocation.class, warpLocationEntity);
        }
        catch (Exception exception) {
            plugin.getLogger().log(Level.SEVERE, "Error while registering the database tables for the Teleport module {0}", exception.getMessage());
        }

        warpService.updateSpawnLocation();
        warpService.loadWarpNames();
        homeService.setHomes(EterniaLib.getDatabase().listAll(Entities.HomeLocation.class));
    }

    @Override
    public void loadCommandsCompletions() {
        EterniaLib.getCmdManager().getCommandCompletions().registerCompletion("homes", homes -> {
            Player player = homes.getPlayer();
            return homeService.getHomeNames(player.getUniqueId());
        });
        EterniaLib.getCmdManager().getCommandCompletions().registerCompletion("warps", warps -> {
            Player player = warps.getPlayer();
            List<String> warpsAvailable = new ArrayList<>();
            for (String warp : warpService.getWarpNames()) {
                if (player.hasPermission(plugin.getString(Strings.PERM_TELEPORT_PREFIX) + warp)) {
                    warpsAvailable.add(warp);
                }
            }
            return warpsAvailable;
        });
    }

    @Override
    public void loadConditions() {
    }

    @Override
    public void loadListeners() {
        plugin.getServer().getPluginManager().registerEvents(new Handlers(plugin, warpService), plugin);
    }

    @Override
    public void loadSchedules() {
    }

    @Override
    public void loadCommands() {
        EterniaLib.getCmdManager().registerCommand(new Commands.Tpa(plugin));
        EterniaLib.getCmdManager().registerCommand(new Commands.Warp(plugin, warpService));
        EterniaLib.getCmdManager().registerCommand(new Commands.Home(plugin, homeService));
    }
}
