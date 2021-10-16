package br.com.eterniaserver.eterniaserver.modules.core;

import br.com.eterniaserver.eternialib.CommandManager;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.api.Module;
import br.com.eterniaserver.eterniaserver.core.EterniaTick;
import br.com.eterniaserver.eterniaserver.enums.Integers;

import java.util.logging.Level;


public class CoreManager implements Module {

    private final EterniaServer plugin;
    private Services.Afk afkServices;

    public CoreManager(final EterniaServer plugin) {
        this.plugin = plugin;
    }

    @Override
    public void loadConfigurations() {
        new Configurations.Configs(plugin);
        new Configurations.Locales(plugin);

        loadCommandsLocales(new Configurations.CommandsLocales(), Enums.Commands.class);

        this.afkServices = new Services.Afk(plugin);
    }

    @Override
    public void loadCommandsCompletions() {
        plugin.getLogger().log(Level.INFO, "Core module: no commands completions");
    }

    @Override
    public void loadListeners() {
        plugin.getServer().getPluginManager().registerEvents(new Handlers(plugin), plugin);
    }

    @Override
    public void loadSchedules() {
        new Schedules.MainTick(plugin, afkServices).runTaskTimer(plugin, 20L, (long) plugin.getInteger(Integers.PLUGIN_TICKS));
    }

    @Override
    public void loadCommands() {
        CommandManager.registerCommand(new Commands.EGameMode(plugin));
    }

    @Override
    public void reloadConfigurations() {
        new Configurations.Configs(plugin);
        new Configurations.Locales(plugin);
    }

}
