package br.com.eterniaserver.eterniaserver;

import br.com.eterniaserver.acf.ConditionFailedException;
import br.com.eterniaserver.eternialib.EterniaLib;
import br.com.eterniaserver.eterniaserver.generics.BaseCmdGamemode;
import br.com.eterniaserver.eterniaserver.generics.BaseCmdGlow;
import br.com.eterniaserver.eterniaserver.generics.BaseCmdItem;
import br.com.eterniaserver.eterniaserver.generics.PluginClear;
import br.com.eterniaserver.eterniaserver.generics.PluginTimer;
import br.com.eterniaserver.eterniaserver.generics.UtilAccelerateWorld;
import br.com.eterniaserver.eterniaserver.generics.UtilAdvancedChatTorch;
import br.com.eterniaserver.eterniaserver.commands.Cash;
import br.com.eterniaserver.eterniaserver.commands.Channels;
import br.com.eterniaserver.eterniaserver.commands.Chat;
import br.com.eterniaserver.eterniaserver.generics.BaseCmdEconomy;
import br.com.eterniaserver.eterniaserver.generics.BaseCmdExperience;
import br.com.eterniaserver.eterniaserver.generics.BaseCmdGeneric;
import br.com.eterniaserver.eterniaserver.generics.BaseCmdHome;
import br.com.eterniaserver.eterniaserver.generics.BaseCmdInventory;
import br.com.eterniaserver.eterniaserver.generics.BaseCmdMute;
import br.com.eterniaserver.eterniaserver.generics.BaseCmdRewards;
import br.com.eterniaserver.eterniaserver.generics.BaseCmdSpawner;
import br.com.eterniaserver.eterniaserver.generics.BaseCmdTeleport;
import br.com.eterniaserver.eterniaserver.generics.BaseCmdWarp;
import br.com.eterniaserver.eterniaserver.generics.PluginTicks;
import br.com.eterniaserver.eterniaserver.generics.BaseCmdKit;
import br.com.eterniaserver.eterniaserver.generics.PluginVars;
import br.com.eterniaserver.eterniaserver.generics.PluginConstants;
import br.com.eterniaserver.eterniaserver.generics.PluginMSGs;

import org.bukkit.Bukkit;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Managers {

    private final EterniaServer plugin;

    public Managers(EterniaServer plugin) {

        EterniaLib.getManager().enableUnstableAPI("help");
        
        this.plugin = plugin;

        loadConditions();
        loadCompletions();
        loadGenericManager();
        loadBedManager();
        loadBlockRewardsManager();
        loadCashManager();
        loadCommandsManager();
        loadChatManager();
        loadEconomyManager();
        loadElevatorManager();
        loadExperienceManager();
        loadHomesManager();
        loadPlayerChecks();
        loadClearManager();
        loadKitManager();
        loadRewardsManager();
        loadSpawnersManager();
        loadTeleportsManager();
        loadScheduleTasks();

    }

    private void loadConditions() {

        EterniaLib.getManager().getCommandConditions().addCondition(Integer.class, "limits", (c, exec, value) -> {
            if (value == null) {
                return;
            }
            if (c.getConfigValue("min", 0) > value) {
                throw new ConditionFailedException("O valor mínimo precisa ser &3" + c.getConfigValue("min", 0));
            }
            if (c.getConfigValue("max", 3) < value) {
                throw new ConditionFailedException("O valor máximo precisa ser &3 " + c.getConfigValue("max", 3));
            }
        });

        EterniaLib.getManager().getCommandConditions().addCondition(Double.class, "limits", (c, exec, value) -> {
            if (value == null) {
                return;
            }
            if (c.getConfigValue("min", 0) > value) {
                throw new ConditionFailedException("O valor mínimo precisa ser &3" + c.getConfigValue("min", 0));
            }
            if (c.getConfigValue("max", 3) < value) {
                throw new ConditionFailedException("O valor máximo precisa ser &3 " + c.getConfigValue("max", 3));
            }
        });

    }

    private void loadCompletions() {
        EterniaLib.getManager().getCommandCompletions().registerStaticCompletion("colors", PluginVars.colorsString);
        EterniaLib.getManager().getCommandCompletions().registerStaticCompletion("entidades", PluginVars.entityList);
    }

    private void loadBedManager() {
        if (sendModuleStatus(EterniaServer.serverConfig.getBoolean("modules.bed"), "Bed")) {
            plugin.getServer().getScheduler().runTaskTimer(plugin, new UtilAccelerateWorld(plugin), 0L, (long) EterniaServer.serverConfig.getInt(PluginConstants.SERVER_CHECKS) * 40);
        }
    }

    private void loadBlockRewardsManager() {
        if (sendModuleStatus(EterniaServer.serverConfig.getBoolean("modules.block-reward"), "Block-Reward")) {
            plugin.getFiles().loadBlocksRewards();
        }
    }

    private void loadCommandsManager() {
        if (sendModuleStatus(EterniaServer.serverConfig.getBoolean("modules.commands"), "Commands")) {
            plugin.getFiles().loadCommands();
        }
    }

    private void loadCashManager() {
        if (sendModuleStatus(EterniaServer.serverConfig.getBoolean("modules.cash"), "Cash")) {
            plugin.getFiles().loadCashGui();
            EterniaLib.getManager().registerCommand(new Cash());
        }
    }

    private void loadChatManager() {
        if (sendModuleStatus(EterniaServer.serverConfig.getBoolean("modules.chat"), "Chat")) {
            plugin.getFiles().loadChat();
            EterniaLib.getManager().registerCommand(new Channels());
            EterniaLib.getManager().registerCommand(new BaseCmdMute());
            EterniaLib.getManager().registerCommand(new Chat(plugin));
            new UtilAdvancedChatTorch();
        }
    }

    private void loadEconomyManager() {
        if (sendModuleStatus(EterniaServer.serverConfig.getBoolean("modules.economy"), "Economy")) {
            EterniaLib.getManager().registerCommand(new BaseCmdEconomy());
        }
    }

    private void loadElevatorManager() {
        sendModuleStatus(EterniaServer.serverConfig.getBoolean("modules.elevator"), "Elevator");
    }

    private void loadExperienceManager() {
        if (sendModuleStatus(EterniaServer.serverConfig.getBoolean("modules.experience"), "Experience")) {
            EterniaLib.getManager().registerCommand(new BaseCmdExperience());
        }
    }

    private void loadGenericManager() {
        sendModuleStatus(true, "Generic");
        EterniaLib.getManager().registerCommand(new BaseCmdInventory());
        EterniaLib.getManager().registerCommand(new BaseCmdGeneric(plugin));
        EterniaLib.getManager().registerCommand(new BaseCmdGamemode());
        EterniaLib.getManager().registerCommand(new BaseCmdGlow());
        EterniaLib.getManager().registerCommand(new BaseCmdItem());
    }

    private void loadHomesManager() {
        if (sendModuleStatus(EterniaServer.serverConfig.getBoolean("modules.home"), "Homes")) {
            EterniaLib.getManager().registerCommand(new BaseCmdHome());
        }
    }

    private void loadKitManager() {
        if (sendModuleStatus(EterniaServer.serverConfig.getBoolean("modules.kits"), "Kits")) {
            plugin.getFiles().loadKits();
            EterniaLib.getManager().registerCommand(new BaseCmdKit());
        }
    }

    private void loadPlayerChecks() {
        sendModuleStatus(true, "PlayerChecks");
        if (EterniaServer.serverConfig.getBoolean("server.async-check")) {
            new PluginTicks(plugin).runTaskTimerAsynchronously(plugin, 20L, (long) EterniaServer.serverConfig.getInt(PluginConstants.SERVER_CHECKS) * 20);
            return;
        }
        new PluginTicks(plugin).runTaskTimer(plugin, 20L, (long) EterniaServer.serverConfig.getInt(PluginConstants.SERVER_CHECKS) * 20);
    }

    private void loadClearManager() {
        if (sendModuleStatus(EterniaServer.serverConfig.getBoolean("modules.clear"), "Mob Control")) {
            new PluginClear().runTaskTimer(plugin, 20L, 600L);
        }
    }

    private void loadRewardsManager() {
        if (sendModuleStatus(EterniaServer.serverConfig.getBoolean("modules.rewards"), "Rewards")) {
            plugin.getFiles().loadRewards();
            EterniaLib.getManager().registerCommand(new BaseCmdRewards());
        }
    }

    private void loadSpawnersManager() {
        if (sendModuleStatus(EterniaServer.serverConfig.getBoolean("modules.spawners"), "Spawners")) {
            EterniaLib.getManager().registerCommand(new BaseCmdSpawner());
        }
    }

    private void loadTeleportsManager() {
        if (sendModuleStatus(EterniaServer.serverConfig.getBoolean("modules.teleports"), "Teleports")) {
            EterniaLib.getManager().registerCommand(new BaseCmdWarp());
            EterniaLib.getManager().registerCommand(new BaseCmdTeleport());
        }
    }

    private void loadScheduleTasks() {
        if (sendModuleStatus(EterniaServer.serverConfig.getBoolean("modules.schedule"), "Schedule")) {
            plugin.getFiles().loadSchedules();
            long start = ChronoUnit.MILLIS.between(LocalTime.now(), LocalTime.of(
                    EterniaServer.scheduleConfig.getInt("schedule.hour"),
                    EterniaServer.scheduleConfig.getInt("schedule.minute"),
                    EterniaServer.scheduleConfig.getInt("schedule.second")));
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleWithFixedDelay(new PluginTimer(plugin), start, TimeUnit.HOURS.toMillis(
                    EterniaServer.scheduleConfig.getInt("schedule.delay")
            ), TimeUnit.MILLISECONDS);
        }
    }

    private boolean sendModuleStatus(final boolean enable, final String module) {
        if (enable) {
            Bukkit.getConsoleSender().sendMessage(PluginMSGs.MSG_MODULE_ENABLE.replace(PluginConstants.MODULE, module));
            return true;
        }
        Bukkit.getConsoleSender().sendMessage(PluginMSGs.MSG_MODULE_DISABLE.replace(PluginConstants.MODULE, module));
        return false;
    }

}
