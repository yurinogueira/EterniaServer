package br.com.eterniaserver.eterniaserver.modules.elevator;

import br.com.eterniaserver.eternialib.EterniaLib;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.modules.Module;

public class ElevatorManager implements Module {

    private final EterniaServer plugin;

    public ElevatorManager(EterniaServer plugin) {
        this.plugin = plugin;
    }

    @Override
    public void loadConfigurations() {
        Configurations.ElevatorConfiguration configuration = new Configurations.ElevatorConfiguration(plugin);

        EterniaLib.getCfgManager().registerConfiguration("eterniaserver", "elevator", true, configuration);
    }

    @Override
    public void loadCommandsCompletions() { }

    @Override
    public void loadConditions() { }

    @Override
    public void loadListeners() {
        plugin.getServer().getPluginManager().registerEvents(new Handlers(plugin), plugin);
    }

    @Override
    public void loadSchedules() { }

    @Override
    public void loadCommands() { }

}
