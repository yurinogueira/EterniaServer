package br.com.eterniaserver.eterniaserver.commands;

import br.com.eterniaserver.acf.annotation.CommandAlias;
import br.com.eterniaserver.acf.annotation.CommandPermission;
import br.com.eterniaserver.acf.annotation.Description;
import br.com.eterniaserver.acf.annotation.Syntax;
import br.com.eterniaserver.acf.BaseCommand;
import br.com.eterniaserver.eternialib.SQL;
import br.com.eterniaserver.eternialib.sql.queries.Select;
import br.com.eterniaserver.eternialib.sql.queries.Update;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.api.PlayerRelated;
import br.com.eterniaserver.eterniaserver.api.ServerRelated;
import br.com.eterniaserver.eterniaserver.enums.Strings;
import br.com.eterniaserver.eterniaserver.enums.Messages;
import br.com.eterniaserver.eterniaserver.objects.CustomKit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Kit extends BaseCommand {

    public Kit() {
        try (Connection connection = SQL.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(new Select(EterniaServer.getString(Strings.TABLE_KITS)).queryString()); ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                PlayerRelated.putKitCooldown(resultSet.getString("name"), resultSet.getLong("cooldown"));
            }
        } catch (SQLException ignored) {
            ServerRelated.logError("Erro ao pegar arquivos da database", 3);
        }
    }

    @CommandAlias("%kits")
    @Description("%kits_description")
    @CommandPermission("%kits_perm")
    public void onKits(Player player) {
        StringBuilder str = new StringBuilder();
        for (String key : EterniaServer.getKitList().keySet()) {
            str.append(ChatColor.DARK_AQUA).append(key).append(ChatColor.DARK_GRAY).append(", ");
        }
        str.setLength(str.length() - 2);
        EterniaServer.sendMessage(player, Messages.KIT_LIST, str.toString());
    }

    @CommandAlias("%kit")
    @Syntax("%kit_syntax")
    @Description("%kit_description")
    @CommandPermission("%kit_perm")
    public void onKit(Player player, String kit) {
        if (EterniaServer.getKitList().containsKey(kit)) {
            if (player.hasPermission(EterniaServer.getString(Strings.PERM_KIT_PREFIX) + kit)) {
                giveKit(player, kit);
            } else {
                EterniaServer.sendMessage(player, Messages.SERVER_NO_PERM);
            }
        } else {
            EterniaServer.sendMessage(player, Messages.KIT_NOT_FOUND, kit);
        }
    }

    private void giveKit(Player player, String kit) {
        final long time = System.currentTimeMillis();
        CustomKit kitObject = EterniaServer.getKitList().get(kit);
        final String kitName = kit + "." + player.getName();
        int cooldown = kitObject.getDelay();
        final long cd = PlayerRelated.getKitCooldown(kitName);

        if (ServerRelated.hasCooldown(cd, cooldown)) {
            for (String command : kitObject.getCommands()) {
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), ServerRelated.setPlaceholders(player, command));
            }
            for (String text : kitObject.getMessages()) {
                player.sendMessage(ServerRelated.getColor(ServerRelated.setPlaceholders(player, text)));
            }
            PlayerRelated.putKitCooldown(kitName, time);

            Update update = new Update(EterniaServer.getString(Strings.TABLE_KITS));
            update.set.set("cooldown", time);
            update.where.set("name", kitName);
            SQL.executeAsync(update);
        } else {
            EterniaServer.sendMessage(player, Messages.SERVER_TIMING, ServerRelated.getTimeLeftOfCooldown(cooldown, cd));
        }
    }

}
