package commands.staff;

import center.vars;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class fly implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            if(player.hasPermission("eternia.comandos.staff.fly"))
            {
                if (args.length == 0)
                {
                    if(player.getAllowFlight())
                    {
                        player.setAllowFlight(false);
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_HIT, 1, 1);
                        player.sendMessage(vars.c(center.looper.c.getString("desativar-voar")));
                        return true;
                    }
                    else
                    {
                        player.setAllowFlight(true);
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_HIT, 1, 1);
                        player.sendMessage(vars.c(center.looper.c.getString("ativar-voar")));
                        return true;
                    }
                }
                else if (args.length == 1)
                {
                    if (player.hasPermission("eternia.comandos.staff.fly.other"))
                    {
                        String targetS = args[0];
                        Player target = Bukkit.getPlayer(targetS);
                        assert target != null;
                        if(target.isOnline())
                        {
                            if (target.getAllowFlight())
                            {
                                target.setAllowFlight(false);
                                target.playSound(target.getLocation(), Sound.BLOCK_ANVIL_HIT, 1, 1);
                                target.sendMessage(vars.c(replaced(Objects.requireNonNull(center.looper.c.getString("desativaram-voar")), player.getName())));
                                player.sendMessage(vars.c(replaced(Objects.requireNonNull(center.looper.c.getString("desativar-voar-de")), target.getName())));
                                return true;
                            }
                            else
                            {
                                target.setAllowFlight(true);
                                target.playSound(target.getLocation(), Sound.BLOCK_ANVIL_HIT, 1, 1);
                                target.sendMessage(vars.c(replaced(Objects.requireNonNull(center.looper.c.getString("ativaram-voar")), player.getName())));
                                player.sendMessage(vars.c(replaced(Objects.requireNonNull(center.looper.c.getString("ativar-voar-de")), target.getName())));
                                return true;
                            }
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