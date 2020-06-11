package br.com.eterniaserver.eterniaserver.modules.chatmanager.commands;

import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.configs.Messages;
import br.com.eterniaserver.eterniaserver.configs.Strings;
import br.com.eterniaserver.eterniaserver.configs.Vars;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Others extends BaseCommand {

    private final Messages messages;
    private final Strings strings;
    private final Vars vars;

    public Others(EterniaServer plugin) {
        this.messages = plugin.getMessages();
        this.strings = plugin.getStrings();
        this.vars = plugin.getVars();
    }

    @CommandAlias("limparchat|chatclear|clearchat")
    @CommandPermission("eternia.clearchat")
    public void onClearChat() {
        for (int i = 0; i < 150; i ++) Bukkit.broadcastMessage("");
    }

    @CommandAlias("broadcast|advice|aviso")
    @CommandPermission("eternia.advice")
    public void onBroadcast(String[] message) {
        messages.broadcastMessage("chat.global-advice", "%advice%", strings.getColor(getMessage(message)));
    }

    @CommandAlias("spy|socialspy")
    @CommandPermission("eternia.spy")
    public void onSpy(Player player) {
        if (vars.spy.getOrDefault(player, false)) {
            vars.spy.put(player, false);
            messages.sendMessage("chat.spyd", player);
        } else {
            vars.spy.put(player, true);
            messages.sendMessage("chat.spye", player);
        }
    }

    @CommandAlias("nickname|nick|name|apelido")
    @Syntax("<jogador> <novo_nome> ou <novo_nome>")
    @CommandPermission("eternia.nickname")
    public void onNickname(Player sender, @Optional OnlinePlayer target, String string) {
        if (target != null) {
            if (string.equalsIgnoreCase("clear")) {
                target.getPlayer().setDisplayName(target.getPlayer().getName());
                messages.sendMessage("chat.remove-nick", target.getPlayer());
                messages.sendMessage("chat.remove-nick", sender);
            } else {
                target.getPlayer().setDisplayName(strings.getColor(string));
                messages.sendMessage("chat.newnick", "%player_display_name%", strings.getColor(string), sender);
                messages.sendMessage("chat.newnick", "%player_display_name%", strings.getColor(string), target.getPlayer());
            }
        } else {
            if (string.equalsIgnoreCase("clear")) {
                sender.setDisplayName(sender.getName());
                messages.sendMessage("chat.remove-nick", sender);
            } else {
                sender.setDisplayName(strings.getColor(string));
                messages.sendMessage("chat.newnick", "%player_display_name%", strings.getColor(string), sender);
            }
        }
    }

    @CommandAlias("resp|r|w|reply")
    @Syntax("<mensagem>")
    @CommandPermission("eternia.tell")
    public void onResp(Player sender, String[] msg) {
        final Player target = Bukkit.getPlayer(vars.tell.get(sender.getName()));
        if (target != null && target.isOnline()) {
            sendPrivate(sender, target.getPlayer(), getMessage(msg));
        } else {
            messages.sendMessage("chat.rnaote", sender);
        }
    }

    @CommandAlias("tell|msg|whisper|emsg")
    @Syntax("<jogador> <mensagem>")
    @CommandCompletion("@players Oi.")
    @CommandPermission("eternia.tell")
    public void onTell(CommandSender player, OnlinePlayer target, String[] msg) {
        sendPrivate(player, target.getPlayer(), getMessage(msg));
    }

    private void sendPrivate(final CommandSender player, final Player target, final String s) {
        final String targetName = target.getName();
        final String playerName = player.getName();
        vars.tell.put(targetName, playerName);
        messages.sendMessage("chat.toplayer", "%player_name%", playerName, "%target_name%", targetName, "%message%", s, player);
        messages.sendMessage("chat.fromplayer", "%player_name%", targetName, "%target_name%", playerName, "%message%", s, target);
        for (Player p : vars.spy.keySet()) {
            if (vars.spy.get(p) && p != player && p != target) {
                p.sendMessage(strings.getColor("&8[&7SPY-&6P&8] &8" + playerName + "->" + targetName + ": " + s));
            }
        }
    }

    private String getMessage(String[] message) {
        StringBuilder sb = new StringBuilder();
        for (String arg : message) sb.append(arg).append(" ");
        return sb.substring(0, sb.length() - 1);
    }

}
