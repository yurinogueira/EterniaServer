package br.com.eterniaserver.modules.teleportsmanager.commands;

import br.com.eterniaserver.configs.Messages;
import br.com.eterniaserver.configs.Vars;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportToPlayer implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("eternia.tpa")) {
                if (args.length == 1) {
                    try {
                        Player target = Bukkit.getPlayer(args[0]);
                        assert target != null;
                        if (target.isOnline()) {
                            if (target != player) {
                                Vars.tpa_requests.remove(target.getName());
                                Vars.tpa_requests.put(target.getName(), player.getName());
                                Messages.PlayerMessage("teleport.receiver", player.getName(), target);
                                Messages.PlayerMessage("teleport.send", target.getName(), player);
                            } else {
                                Messages.PlayerMessage("teleport.auto", player);
                            }
                        } else {
                            Messages.PlayerMessage("server.player-offline", player);
                        }
                    } catch (Exception e) {
                        Messages.PlayerMessage("server.player-offline", player);
                    }
                } else {
                    Messages.PlayerMessage("teleport.use", player);
                }
            } else {
                Messages.PlayerMessage("server.no-perm", player);
            }
        } else {
            Messages.ConsoleMessage("server.only-player");
        }
        return true;
    }

}