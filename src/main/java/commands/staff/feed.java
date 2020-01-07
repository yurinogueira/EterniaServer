package commands.staff;

import center.vars;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class feed implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            if(player.hasPermission("eternia.comandos.staff.feed"))
            {
                if (args.length == 0)
                {
                    if (command.getName().equalsIgnoreCase("comandos.staff.feed"))
                    {
                        player.setFoodLevel(20);
                        player.sendMessage(vars.c(center.looper.c.getString("me-enchi")));
                        return true;
                    }
                }
                else if (args.length == 1)
                {
                    if (player.hasPermission("eternia.comandos.staff.feed.other"))
                    {
                        String targetS = args[0];
                        Player target = Bukkit.getPlayer(targetS);
                        assert target != null;
                        if (target.isOnline())
                        {
                            target.setFoodLevel(20);
                            player.sendMessage(vars.c(replaced(Objects.requireNonNull(center.looper.c.getString("encheu-barra")), target.getName())));
                            target.sendMessage(vars.c(replaced(Objects.requireNonNull(center.looper.c.getString("recebeu-barra")), player.getName())));
                            return true;
                        }
                        else
                        {
                            player.sendMessage(vars.c(center.looper.c.getString("jogador-offline")));
                            return true;
                        }
                    }
                    else
                    {
                        player.sendMessage(vars.c(center.looper.c.getString("sem-permissao")));
                        return true;
                    }
                }
            }
            else
            {
                player.sendMessage(vars.c(center.looper.c.getString("sem-permissao")));
                return true;
            }
        }
        return false;
    }
    private String replaced(String args, String valor)
    {
        return args.replace("%s", valor);
    }
}