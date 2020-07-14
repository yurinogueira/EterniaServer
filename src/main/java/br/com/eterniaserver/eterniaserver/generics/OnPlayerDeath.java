package br.com.eterniaserver.eterniaserver.generics;

import br.com.eterniaserver.eterniaserver.EterniaServer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (EterniaServer.serverConfig.getBoolean("modules.teleports")) {
            final Player player = event.getEntity();
            Vars.back.put(player.getName(), player.getLocation());
        }
    }

}
