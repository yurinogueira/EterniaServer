package br.com.eterniaserver.eterniaserver.commands;

import br.com.eterniaserver.acf.BaseCommand;
import br.com.eterniaserver.acf.annotation.CommandAlias;
import br.com.eterniaserver.acf.annotation.CommandCompletion;
import br.com.eterniaserver.acf.annotation.CommandPermission;
import br.com.eterniaserver.acf.annotation.Default;
import br.com.eterniaserver.acf.annotation.Description;
import br.com.eterniaserver.acf.annotation.Optional;
import br.com.eterniaserver.acf.annotation.Subcommand;
import br.com.eterniaserver.acf.annotation.Syntax;
import br.com.eterniaserver.acf.bukkit.contexts.OnlinePlayer;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.core.User;
import br.com.eterniaserver.eterniaserver.enums.Messages;
import br.com.eterniaserver.eterniaserver.core.APIEconomy;

import org.bukkit.entity.Player;

@CommandAlias("%nick")
public class Nick extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    @Syntax("%nick_syntax")
    @Description("%nick_description")
    @CommandPermission("%nick_perm")
    public void onNick(Player player, String string, @Optional OnlinePlayer target) {
        string = string.replaceAll("[^a-zA-Z0-9]", "");
        User user = new User(player);

        if (target == null) {
            user.playerNick(string);
            return;
        }

        if (user.hasPermission("eternia.nickname.others")) {
            user.staffNick(target, player, string);
            return;
        }

        user.sendMessage(Messages.SERVER_NO_PERM);
    }

    @Subcommand("%nick_deny")
    @Description("%nick_deny_description")
    @CommandPermission("%nick_deny_perm")
    public void onNickDeny(Player player) {
        User user = new User(player);

        if (user.hasNickRequest()) {
            user.removeNickRequest();
            user.sendMessage(Messages.CHAT_NICK_DENIED);
            return;
        }

        user.sendMessage(Messages.CHAT_NICK_NOT_REQUESTED);
    }

    @Subcommand("%nick_accept")
    @CommandPermission("%nick_accept_perm")
    @Description("%nick_accept_description")
    public void onNickAccept(Player player) {
        User user = new User(player);

        if (!user.hasNickRequest()) {
            user.sendMessage(Messages.CHAT_NICK_NOT_REQUESTED);
            return;
        }

        if (!APIEconomy.hasMoney(user.getUUID(), EterniaServer.configs.nickCost)) {
            user.sendMessage(Messages.ECO_NO_MONEY);
            return;
        }

        APIEconomy.removeMoney(user.getUUID(), EterniaServer.configs.nickCost);
        user.updateNickName();
    }

}
