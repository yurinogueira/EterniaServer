package br.com.eterniaserver.modules.teleportsmanager.commands;

import br.com.eterniaserver.EterniaServer;
import br.com.eterniaserver.configs.Messages;
import br.com.eterniaserver.configs.Vars;
import br.com.eterniaserver.modules.economymanager.sql.Queries;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Back implements CommandExecutor {

    private final EterniaServer plugin;

    public Back(EterniaServer plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("eternia.back")) {
                if (Vars.back.containsKey(player.getName())) {
                    double money = Queries.getMoney(player.getName());
                    double valor = EterniaServer.configs.getInt("money.back");
                    if (player.hasPermission("eternia.backfree") || !(EterniaServer.configs.getBoolean("modules.economy"))) {
                        if (player.hasPermission("eternia.timing.bypass")) {
                            player.teleport(Vars.back.get(player.getName()));
                            Vars.back.remove(player.getName());
                            Messages.PlayerMessage("back.free", player);
                        } else {
                            Messages.PlayerMessage("teleport.timing", EterniaServer.configs.getInt("server.cooldown"), player);
                            Vars.playerposition.put(player.getName(), player.getLocation());
                            Vars.moved.put(player.getName(), false);
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
                            {
                                if (!Vars.moved.get(player.getName())) {
                                    player.teleport(Vars.back.get(player.getName()));
                                    Vars.back.remove(player.getName());
                                    Messages.PlayerMessage("back.free", player);
                                } else {
                                    Messages.PlayerMessage("warps.move", player);
                                }
                            }, 20 * EterniaServer.configs.getInt("server.cooldown"));
                        }
                    } else {
                        if (money >= valor) {
                            if (player.hasPermission("eternia.timing.bypass")) {
                                player.teleport(Vars.back.get(player.getName()));
                                Vars.back.remove(player.getName());
                                Queries.removeMoney(player.getName(), valor);
                                Messages.PlayerMessage("back.nofree", valor, player);
                            } else {
                                Messages.PlayerMessage("teleport.timing", EterniaServer.configs.getInt("server.cooldown"), player);
                                Vars.moved.put(player.getName(), false);
                                Vars.playerposition.put(player.getName(), player.getLocation());
                                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
                                {
                                    if (!Vars.moved.get(player.getName())) {
                                        player.teleport(Vars.back.get(player.getName()));
                                        Vars.back.remove(player.getName());
                                        Queries.removeMoney(player.getName(), valor);
                                        Messages.PlayerMessage("back.nofree", valor, player);
                                    } else {
                                        Messages.PlayerMessage("warps.move", player);
                                    }
                                }, 20 * EterniaServer.configs.getInt("server.cooldown"));
                            }
                        } else {
                            Messages.PlayerMessage("back.nomoney", valor, player);
                        }
                    }
                } else {
                    Messages.PlayerMessage("back.notp", player);
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