package br.com.eterniaserver.eterniaserver.events;

import br.com.eterniaserver.eternialib.EFiles;
import br.com.eterniaserver.eterniaserver.EterniaServer;

import me.clip.placeholderapi.PlaceholderAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class OnPlayerCommandPreProcess implements Listener {

    private final EterniaServer plugin;
    private final EFiles messages;

    public OnPlayerCommandPreProcess(EterniaServer plugin) {
        this.plugin = plugin;
        this.messages = plugin.getEFiles();
    }

    @EventHandler
    public void onPlayerCommandPreProcess(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) return;

        final Player player = event.getPlayer();
        final String playerName = player.getName();
        String message = event.getMessage().toLowerCase();

        if (message.equalsIgnoreCase("/tps") && plugin.hasPlaceholderAPI) {
            String s = PlaceholderAPI.setPlaceholders(player, "%server_tps%");
            messages.sendMessage("replaces.tps", "%server_tps%", s.substring(0, s.length() - 2), player);
            event.setCancelled(true);
            return;
        }
        if (plugin.serverConfig.getBoolean("modules.commands")) {
            if (plugin.cmdConfig.contains("commands." + message)) {
                String cmd = message.replace("/", "");
                if (player.hasPermission("eternia." + cmd)) {
                    for (String line : plugin.cmdConfig.getStringList("commands." + message + ".command")) {
                        String modifiedCommand;

                        if (plugin.hasPlaceholderAPI) modifiedCommand = PlaceholderAPI.setPlaceholders(player, line);
                        else modifiedCommand = line.replace("%player_name%", playerName);

                        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), modifiedCommand);
                    }
                    for (String line : plugin.cmdConfig.getStringList("commands." + message + ".text")) {
                        String modifiedText;

                        if (plugin.hasPlaceholderAPI) modifiedText =PlaceholderAPI.setPlaceholders(player, line);
                        else modifiedText = line.replace("%player_name%", playerName);

                        player.sendMessage(messages.getColor(modifiedText));
                    }
                } else {
                    messages.sendMessage("server.no-perm", player);
                }
                event.setCancelled(true);
            }
        }
    }

}
