package br.com.eterniaserver.eterniaserver.commands;

import br.com.eterniaserver.acf.BaseCommand;
import br.com.eterniaserver.acf.CommandHelp;
import br.com.eterniaserver.acf.annotation.CommandAlias;
import br.com.eterniaserver.acf.annotation.CommandCompletion;
import br.com.eterniaserver.acf.annotation.CommandPermission;
import br.com.eterniaserver.acf.annotation.Conditions;
import br.com.eterniaserver.acf.annotation.Default;
import br.com.eterniaserver.acf.annotation.Description;
import br.com.eterniaserver.acf.annotation.HelpCommand;
import br.com.eterniaserver.acf.annotation.Optional;
import br.com.eterniaserver.acf.annotation.Subcommand;
import br.com.eterniaserver.acf.annotation.Syntax;
import br.com.eterniaserver.acf.bukkit.contexts.OnlinePlayer;
import br.com.eterniaserver.eternialib.UUIDFetcher;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.generics.APICash;
import br.com.eterniaserver.eterniaserver.generics.APIPlayer;
import br.com.eterniaserver.eterniaserver.generics.PluginConstants;
import br.com.eterniaserver.eterniaserver.generics.PluginMSGs;
import br.com.eterniaserver.eterniaserver.generics.UtilInternMethods;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

@CommandAlias("cash")
@CommandPermission("eternia.cash")
public class Cash extends BaseCommand {

    @Subcommand("help")
    @HelpCommand
    @Syntax("<página>")
    @Description(" Ajuda para o sistema de Cash")
    public void onCashHelp(CommandHelp help) {
        help.showHelp();
    }

    @Default
    @Description(" Abre a GUI da loja de Cash")
    public void onCash(Player player) {
        Inventory gui = Bukkit.getServer().createInventory(player, APICash.getCashGuiSize(), "Cash");

        for (int i = 0; i < APICash.getCashGuiSize(); i++) {
            gui.setItem(i, APICash.getItemCashGui(i));
        }

        player.openInventory(gui);
    }

    @Subcommand("balance")
    @CommandCompletion("@players")
    @Syntax("<jogador>")
    @Description(" Mostra o saldo atual de cash de um jogador")
    public void onCashBalance(Player player, @Optional String playerName) {
        if (playerName == null) {
            player.sendMessage(PluginMSGs.M_CASH_BALANCE.replace(PluginConstants.AMOUNT, String.valueOf(APICash.getCash(UUIDFetcher.getUUIDOf(player.getName())))));
            return;
        }

        final UUID uuid = UUIDFetcher.getUUIDOf(playerName);

        if (APIPlayer.hasProfile(uuid)) {
            player.sendMessage(PluginMSGs.M_CASH_BALANCE_OTHER.replace(PluginConstants.AMOUNT, String.valueOf(APICash.getCash(uuid))));
        }
        else player.sendMessage(PluginMSGs.M_CASH_NO_PLAYER);
    }

    @Subcommand("accept")
    @Description(" Aceita uma compra da loja de cash")
    public void onCashAccept(Player player) {
        final UUID uuid = UUIDFetcher.getUUIDOf(player.getName());

        if (!APICash.isBuying(uuid)) {
            player.sendMessage(PluginMSGs.M_CASH_NO_BUY);
            return;
        }

        final String cashString = APICash.getCashBuy(uuid);

        for (String line : EterniaServer.cashConfig.getStringList(cashString + ".commands")) {
            final String modifiedCommand = UtilInternMethods.setPlaceholders(player, line);
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), modifiedCommand);
        }

        for (String line : EterniaServer.cashConfig.getStringList(cashString + ".messages")) {
            final String modifiedText = UtilInternMethods.setPlaceholders(player, line);
            player.sendMessage(PluginMSGs.getColor(modifiedText));
        }

        APICash.removeCash(uuid, EterniaServer.cashConfig.getInt(cashString + ".cost"));
        player.sendMessage(PluginMSGs.M_CASH_SUCESS);
        APICash.removeCashBuy(uuid);
    }

    @Subcommand("deny")
    @Description(" Recusa uma compra da loja de cash")
    public void onCashDeny(Player player) {
        final UUID uuid = UUIDFetcher.getUUIDOf(player.getName());

        if (!APICash.isBuying(uuid)) {
            player.sendMessage(PluginMSGs.M_CASH_NO_BUY);
            return;
        }

        player.sendMessage(PluginMSGs.M_CASH_CANCEL);
        APICash.removeCashBuy(uuid);
    }

    @Subcommand("pay")
    @CommandCompletion("@players 10")
    @Syntax("<jogador> <quantia>")
    @Description(" Paga uma quantia de cash a um jogador")
    public void onCashPay(Player player, OnlinePlayer targetP, @Conditions("limits:min=1,max=9999999") Integer value) {
        final UUID uuid = UUIDFetcher.getUUIDOf(player.getName());
        final Player target = targetP.getPlayer();

        if (!APICash.hasCash(uuid, value)) {
            player.sendMessage(PluginMSGs.MSG_NO_MONEY);
            return;
        }

        APICash.removeCash(uuid, value);
        APICash.addCash(UUIDFetcher.getUUIDOf(target.getName()), value);
        target.sendMessage(PluginMSGs.M_CASH_RECEIVED.replace(PluginConstants.AMOUNT, String.valueOf(value)));
        player.sendMessage(UtilInternMethods.putName(target, PluginMSGs.M_CASH_SEND).replace(PluginConstants.AMOUNT, String.valueOf(value)));
    }

    @Subcommand("give")
    @CommandCompletion("@players 10")
    @Syntax("<jogador> <quantia>")
    @Description(" Dá uma quantia de cash a um jogador")
    @CommandPermission("eternia.cash.admin")
    public void onCashGive(CommandSender player, OnlinePlayer targetP, @Conditions("limits:min=1,max=9999999") Integer value) {
        final Player target = targetP.getPlayer();
        APICash.addCash(UUIDFetcher.getUUIDOf(target.getName()), value);
        target.sendMessage(PluginMSGs.M_CASH_RECEIVED.replace(PluginConstants.AMOUNT, String.valueOf(value)));
        player.sendMessage(UtilInternMethods.putName(target, PluginMSGs.M_CASH_SEND).replace(PluginConstants.AMOUNT, String.valueOf(value)));
    }

    @Subcommand("remove")
    @CommandCompletion("@players 10")
    @Syntax("<jogador> <quantia>")
    @CommandPermission("eternia.cash.admin")
    @Description(" Remove uma quantia de cash de um jogador")
    public void onCashRemove(CommandSender player, OnlinePlayer targetP, @Conditions("limits:min=1,max=9999999") Integer value) {
        final Player target = targetP.getPlayer();
        APICash.removeCash(UUIDFetcher.getUUIDOf(target.getName()), value);
        target.sendMessage(PluginMSGs.M_CASH_REMOVED.replace(PluginConstants.AMOUNT, String.valueOf(value)));
        player.sendMessage(UtilInternMethods.putName(target, PluginMSGs.M_CASH_REMOVE).replace(PluginConstants.AMOUNT, String.valueOf(value)));
    }

}
