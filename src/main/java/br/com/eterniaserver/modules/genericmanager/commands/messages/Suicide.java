package br.com.eterniaserver.modules.genericmanager.commands.messages;

import br.com.eterniaserver.configs.Messages;
import br.com.eterniaserver.configs.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Suicide implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, java.lang.String label, java.lang.String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("eternia.suicide")) {
                if (args.length >= 1) {
                    StringBuilder sb = new StringBuilder();
                    for (java.lang.String arg : args) {
                        sb.append(arg).append(" ");
                    }
                    sb.append("&8- &3").append(player.getName());
                    java.lang.String s = sb.toString();
                    player.setHealth(0);
                    Messages.BroadcastMessage("text.suicide", Strings.getColor(s));
                } else {
                    player.setHealth(0);
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