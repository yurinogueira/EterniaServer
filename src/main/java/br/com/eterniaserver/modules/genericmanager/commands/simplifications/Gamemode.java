package br.com.eterniaserver.modules.genericmanager.commands.simplifications;

import br.com.eterniaserver.configs.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gamemode implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("eternia.gamemode")) {
                if (args.length == 1) {
                    String gm = args[0].toLowerCase();
                    switch (gm) {
                        case "0":
                        case "s":
                        case "survival":
                        case "sobrevivencia":
                            if (player.hasPermission("eternia.gamemode.0")) {
                                player.setGameMode(GameMode.SURVIVAL);
                                Messages.PlayerMessage("simp.gm", "Sobrevivência", player);
                            } else {
                                Messages.PlayerMessage("server.no-perm", player);
                            }
                            break;
                        case "1":
                        case "c":
                        case "creative":
                        case "criativo":
                            if (player.hasPermission("eternia.gamemode.1")) {
                                player.setGameMode(GameMode.CREATIVE);
                                Messages.PlayerMessage("simp.gm", "Criativo", player);
                            } else {
                                Messages.PlayerMessage("server.no-perm", player);
                            }
                            break;
                        case "2":
                        case "a":
                        case "adventure":
                        case "aventura":
                            if (player.hasPermission("eternia.gamemode.2")) {
                                player.setGameMode(GameMode.ADVENTURE);
                                Messages.PlayerMessage("simp.gm", "Aventura", player);
                            } else {
                                Messages.PlayerMessage("server.no-perm", player);
                            }
                            break;
                        case "3":
                        case "sp":
                        case "spectator":
                        case "spect":
                            if (player.hasPermission("eternia.gamemode.3")) {
                                player.setGameMode(GameMode.SPECTATOR);
                                Messages.PlayerMessage("simp.gm", "Espectador", player);
                            } else {
                                Messages.PlayerMessage("server.no-perm", player);
                            }
                            break;
                        default:
                            Messages.PlayerMessage("simp.gmuse", player);
                            break;
                    }
                } else if (args.length == 2) {
                    String gm = args[0].toLowerCase();
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null && target.isOnline()) {
                        switch (gm) {
                            case "0":
                            case "s":
                            case "survival":
                            case "sobrevivencia":
                                if (player.hasPermission("eternia.gamemode.0")) {
                                    target.setGameMode(GameMode.SURVIVAL);
                                    Messages.PlayerMessage("simp.gm-other", "Sobrevivência", player.getName(), target);
                                    Messages.PlayerMessage("simp.other-gm", target.getName(), "Sobrevivência", player);
                                } else {
                                    Messages.PlayerMessage("server.no-perm", player);
                                }
                                break;
                            case "1":
                            case "c":
                            case "creative":
                            case "criativo":
                                if (player.hasPermission("eternia.gamemode.1")) {
                                    target.setGameMode(GameMode.CREATIVE);
                                    Messages.PlayerMessage("simp.gm-other", "Criativo", player.getName(), target);
                                    Messages.PlayerMessage("simp.other-gm", target.getName(), "Criativo", player);
                                } else {
                                    Messages.PlayerMessage("server.no-perm", player);
                                }
                                break;
                            case "2":
                            case "a":
                            case "adventure":
                            case "aventura":
                                if (player.hasPermission("eternia.gamemode.2")) {
                                    target.setGameMode(GameMode.ADVENTURE);
                                    Messages.PlayerMessage("simp.gm-other", "Aventura", player.getName(), target);
                                    Messages.PlayerMessage("simp.other-gm", target.getName(), "Aventura", player);
                                } else {
                                    Messages.PlayerMessage("server.no-perm", player);
                                }
                                break;
                            case "3":
                            case "sp":
                            case "spectator":
                            case "spect":
                                if (player.hasPermission("eternia.gamemode.3")) {
                                    target.setGameMode(GameMode.SPECTATOR);
                                    Messages.PlayerMessage("simp.gm-other", "Espectador", player.getName(), target);
                                    Messages.PlayerMessage("simp.other-gm", target.getName(), "Espectador", player);
                                } else {
                                    Messages.PlayerMessage("server.no-perm", player);
                                }
                                break;
                            default:
                                Messages.PlayerMessage("simp.gmuse", player);
                                break;
                        }
                    } else {
                        Messages.PlayerMessage("server.player-offline", player);
                    }
                } else {
                    Messages.PlayerMessage("simp.gmuse", player);
                }
            } else {
                Messages.PlayerMessage("server.no-perm", player);
            }
        } else {
            if (args.length == 2) {
                String gm = args[0].toLowerCase();
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null && target.isOnline()) {
                    switch (gm) {
                        case "0":
                        case "s":
                        case "survival":
                        case "sobrevivencia":
                            target.setGameMode(GameMode.SURVIVAL);
                            Messages.PlayerMessage("simp.gm-other", "Sobrevivência", "Console", target);
                            Messages.ConsoleMessage("simp.other-gm", target.getName(), "Sobrevivência");
                            break;
                        case "1":
                        case "c":
                        case "creative":
                        case "criativo":
                            target.setGameMode(GameMode.CREATIVE);
                            Messages.PlayerMessage("simp.gm-other", "Criativo", "Console", target);
                            Messages.ConsoleMessage("simp.other-gm", target.getName(), "Criativo");
                            break;
                        case "2":
                        case "a":
                        case "adventure":
                        case "aventura":
                            target.setGameMode(GameMode.ADVENTURE);
                            Messages.PlayerMessage("simp.gm-other", "Aventura", "Console", target);
                            Messages.ConsoleMessage("simp.other-gm", target.getName(), "Aventura");
                            break;
                        case "3":
                        case "sp":
                        case "spectator":
                        case "spect":
                            target.setGameMode(GameMode.SPECTATOR);
                            Messages.PlayerMessage("simp.gm-other", "Espectador", "Console", target);
                            Messages.ConsoleMessage("simp.other-gm", target.getName(), "Espectador");
                            break;
                        default:
                            Messages.ConsoleMessage("simp.gmuse");
                            break;
                    }
                } else {
                    Messages.ConsoleMessage("server.player-offline");
                }
            } else {
                Messages.ConsoleMessage("simp.gmuse");
            }
        }
        return true;
    }
}