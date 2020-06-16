package br.com.eterniaserver.eterniaserver.dependencies.vault;

import br.com.eterniaserver.eterniaserver.EterniaServer;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

public class VaultHook {

    public VaultHook(EterniaServer plugin) {
        if (plugin.getServer().getPluginManager().isPluginEnabled("Vault") && plugin.serverConfig.getBoolean("modules.economy")) {
            ServicesManager servicesManager = plugin.getServer().getServicesManager();
            servicesManager.register(Economy.class, new VaultMethods(plugin), plugin, ServicePriority.High);
        } else {
            plugin.getEFiles().sendConsole("server.no-vault");
        }
    }

}
