package br.com.eterniaserver.eterniaserver.modules.cash;

import br.com.eterniaserver.eternialib.EterniaLib;
import br.com.eterniaserver.eternialib.database.Entity;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.modules.Module;
import br.com.eterniaserver.eterniaserver.enums.Strings;
import br.com.eterniaserver.eterniaserver.modules.cash.Entities.CashBalance;
import br.com.eterniaserver.eterniaserver.modules.cash.Configurations.CashConfiguration;
import br.com.eterniaserver.eterniaserver.modules.cash.Configurations.CashCommands;
import br.com.eterniaserver.eterniaserver.modules.cash.Configurations.CashMessages;

import java.util.List;
import java.util.logging.Level;


public class CashManager implements Module {

    private final EterniaServer plugin;
    private Services.CashService cashService;

    public CashManager(EterniaServer plugin) {
        this.plugin = plugin;
    }

    @Override
    public void loadConfigurations() {
        CashMessages messages = new CashMessages(plugin.messages());
        CashCommands commands = new CashCommands();
        CashConfiguration configuration = new CashConfiguration(plugin);

        EterniaLib.getCfgManager().registerConfiguration("eterniaserver", "cash_messages", true, messages);
        EterniaLib.getCfgManager().registerConfiguration("eterniaserver", "cash_commands", true, commands);
        EterniaLib.getCfgManager().registerConfiguration("eterniaserver", "cash", true, configuration);

        try {
            Entity<CashBalance> cashEntity = new Entity<>(CashBalance.class);

            EterniaLib.getDatabase().addTableName("%eternia_server_cash%", plugin.getString(Strings.CASH_TABLE_NAME));
            EterniaLib.getDatabase().addTableName("%eternia_server_profile%", plugin.getString(Strings.PROFILE_TABLE_NAME));

            EterniaLib.getDatabase().register(CashBalance.class, cashEntity);
        }
        catch (Exception exception) {
            plugin.getLogger().log(Level.SEVERE, "Error while registering cash entity", exception);
        }

        List<CashBalance> cashBalances = EterniaLib.getDatabase().listAll(CashBalance.class);
        this.plugin.getLogger().log(Level.INFO, "cash module: {0} cash balances loaded", cashBalances.size());

        EterniaServer.setCashAPI(new Services.CraftCash());
        this.cashService = new Services.CashService(plugin);
    }

    @Override
    public void loadCommandsCompletions() { }

    @Override
    public void loadConditions() { }

    @Override
    public void loadListeners() {
        plugin.getServer().getPluginManager().registerEvents(new Handlers(plugin, cashService), plugin);
    }

    @Override
    public void loadSchedules() { }

    @Override
    public void loadCommands() {
        EterniaLib.getCmdManager().registerCommand(new Commands.Cash(plugin, cashService));
    }

}
