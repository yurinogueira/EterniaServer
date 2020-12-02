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
import br.com.eterniaserver.eterniaserver.api.EconomyRelated;
import br.com.eterniaserver.eterniaserver.objects.User;
import br.com.eterniaserver.eterniaserver.enums.Doubles;
import br.com.eterniaserver.eterniaserver.enums.Strings;
import br.com.eterniaserver.eterniaserver.enums.Messages;

import org.bukkit.entity.Player;

@CommandAlias("%nick")
public class Nick extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    @Syntax("%nick_syntax")
    @Description("%nick_description")
    @CommandPermission("%nick_perm")
    public void onNick(Player player, String string, @Optional OnlinePlayer targets) {
        string = string.replaceAll("[^a-zA-Z0-9]", "");
        User user = new User(player);

        if (targets == null) {
            user.requestNickChange(string);
            return;
        }

        if (!user.hasPermission(EterniaServer.getString(Strings.PERM_NICK_OTHER))) {
            user.sendMessage(Messages.SERVER_NO_PERM);
            return;
        }

        User target = new User(targets.getPlayer());

        if (string.equals(EterniaServer.getString(Strings.CLEAR_STRING))) {
            target.clearNickName();
            target.sendMessage(Messages.CHAT_NICK_CLEAR_BY, user.getName(), user.getDisplayName());
            user.sendMessage(Messages.CHAT_NICK_CLEAR_FROM, target.getName(), target.getDisplayName());
            return;
        }

        target.setTempNickName(string);
        target.updateNickName();
        target.sendMessage(Messages.CHAT_NICK_CHANGED_BY, string, user.getName(), user.getDisplayName());
        user.sendMessage(Messages.CHAT_NICK_CHANGED_FROM, string, target.getName(), target.getDisplayName());
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

        if (!EconomyRelated.hasMoney(user.getUUID(), EterniaServer.getDouble(Doubles.NICK_COST))) {
            user.sendMessage(Messages.ECO_NO_MONEY);
            return;
        }

        EconomyRelated.removeMoney(user.getUUID(), EterniaServer.getDouble(Doubles.NICK_COST));
        EterniaServer.sendMessage(player, Messages.CHAT_NICK_CHANGED, player.getDisplayName());
        user.updateNickName();
    }

}
