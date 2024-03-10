package br.com.eterniaserver.eterniaserver.modules.item;

import br.com.eterniaserver.acf.BaseCommand;
import br.com.eterniaserver.acf.CommandHelp;
import br.com.eterniaserver.acf.annotation.*;
import br.com.eterniaserver.acf.bukkit.contexts.OnlinePlayer;
import br.com.eterniaserver.eternialib.EterniaLib;
import br.com.eterniaserver.eternialib.chat.MessageOptions;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.enums.Messages;
import br.com.eterniaserver.eterniaserver.modules.Constants;

import com.google.gson.Gson;

import net.kyori.adventure.text.Component;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

final class Commands {

    private Commands() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    @CommandAlias("%ITEM")
    static class Item extends BaseCommand {

        private final Gson gson = new Gson();

        private final EterniaServer plugin;

        public Item(EterniaServer plugin) {
            this.plugin = plugin;
        }

        @Default
        @HelpCommand
        @Syntax("%ITEM_SYNTAX")
        @Description("%ITEM_DESCRIPTION")
        @CommandPermission("%ITEM_PERM")
        public void onHelp(CommandHelp help) {
            help.showHelp();
        }

        @Subcommand("%ITEM_SEND_CUSTOM")
        @CommandPermission("%ITEM_SEND_CUSTOM_PERM")
        @Description("%ITEM_SEND_CUSTOM_DESCRIPTION")
        @Syntax("%ITEM_SEND_CUSTOM_SYNTAX")
        @CommandCompletion("@players string")
        public void sendItemCustom(CommandSender sender,
                                   OnlinePlayer target,
                                   String jsonItem) {
            Utils.JsonItem item = gson.fromJson(jsonItem, Utils.JsonItem.class);

            ItemStack itemStack = item.getItemStack(plugin);

            Player player = target.getPlayer();
            if (player.getInventory().firstEmpty() != -1) {
                player.getInventory().addItem(itemStack);
            } else {
                player.getWorld().dropItem(player.getLocation(), itemStack);
            }

            EterniaLib.getChatCommons().sendMessage(sender, Messages.ITEM_CUSTOM_GIVE);
            EterniaLib.getChatCommons().sendMessage(player, Messages.ITEM_CUSTOM_RECEIVED);
        }

        @Subcommand("%ITEM_CUSTOM_MODEL")
        @CommandPermission("%ITEM_CUSTOM_MODEL_PERM")
        @Description("%ITEM_CUSTOM_MODEL_DESCRIPTION")
        @Syntax("%ITEM_CUSTOM_MODEL_SYNTAX")
        @CommandCompletion("0")
        public void setCustomModelData(Player player, @Conditions("limits:min=0,max=2147483647") Integer integer) {
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setCustomModelData(integer);
            itemStack.setItemMeta(itemMeta);
            player.getInventory().setItemInMainHand(itemStack);
            MessageOptions messageOptions = new MessageOptions(String.valueOf(integer));
            EterniaLib.getChatCommons().sendMessage(player, Messages.ITEM_SET_CUSTOM, messageOptions);
        }

        @Subcommand("%ITEM_CLEAR")
        public class Clear extends BaseCommand {

            @Default
            @HelpCommand
            @Syntax("%ITEM_CLEAR_SYNTAX")
            @Description("%ITEM_CLEAR_DESCRIPTION")
            @CommandPermission("%ITEM_CLEAR_PERM")
            public void onClear(CommandHelp help) {
                help.showHelp();
            }

            @Subcommand("%ITEM_CLEAR_LORE")
            @CommandPermission("%ITEM_CLEAR_LORE_PERM")
            @Description("%ITEM_CLEAR_LORE_DESCRIPTION")
            public void onItemClearLore(Player player) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getType() == Material.AIR || item.lore() == null) {
                    EterniaLib.getChatCommons().sendMessage(player, Messages.ITEM_NOT_FOUND);
                    return;
                }

                item.lore(new ArrayList<>());
                player.getInventory().setItemInMainHand(item);
                EterniaLib.getChatCommons().sendMessage(player, Messages.ITEM_CLEAR_LORE);
            }

            @Subcommand("%ITEM_CLEAR_NAME")
            @CommandPermission("%ITEM_CLEAR_NAME_PERM")
            @Description("%ITEM_CLEAR_NAME_DESCRIPTION")
            public void onItemClearName(Player player) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getType() == Material.AIR) {
                    EterniaLib.getChatCommons().sendMessage(player, Messages.ITEM_NOT_FOUND);
                    return;
                }

                item.getItemMeta().displayName(item.getItemMeta().displayName());
                player.getInventory().setItemInMainHand(item);
                EterniaLib.getChatCommons().sendMessage(player, Messages.ITEM_CLEAR_NAME);
                plugin.getLogger().log(Level.INFO, "Item name cleared by {0}", player.getName());
            }

        }

        @Subcommand("%ITEM_ADD_LORE")
        @Syntax("%ITEM_ADD_LORE_SYNTAX")
        @Description("%ITEM_ADD_LORE_DESCRIPTION")
        @CommandPermission("%ITEM_ADD_LORE_PERM")
        public void onItemAddLore(Player player, String loreStr) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == Material.AIR) {
                EterniaLib.getChatCommons().sendMessage(player, Messages.ITEM_NOT_FOUND);
                return;
            }

            Component loreComponent = EterniaLib.getChatCommons().parseColor(loreStr);
            List<Component> lore = item.lore();
            if (lore != null) {
                lore.add(loreComponent);
                item.lore(lore);
            } else {
                item.lore(List.of(loreComponent));
            }


            player.getInventory().setItemInMainHand(item);
            MessageOptions messageOptions = new MessageOptions(loreStr);
            EterniaLib.getChatCommons().sendMessage(player, Messages.ITEM_ADD_LORE, messageOptions);
        }

        @Subcommand("%ITEM_SET")
        public class Set extends BaseCommand {

            @Default
            @HelpCommand
            @Syntax("%ITEM_SET_SYNTAX")
            @Description("%ITEM_SET_DESCRIPTION")
            @CommandPermission("%ITEM_SET_PERM")
            public void onSet(CommandHelp help) {
                help.showHelp();
            }

            @Subcommand("%ITEM_SET_LORE")
            @Syntax("%ITEM_SET_LORE_SYNTAX")
            @Description("%ITEM_SET_LORE_DESCRIPTION")
            @CommandPermission("%ITEM_SET_LORE_PERM")
            public void onItemSetLore(Player player, String loreStr) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getType() == Material.AIR) {
                    EterniaLib.getChatCommons().sendMessage(player, Messages.ITEM_NOT_FOUND);
                    return;
                }

                Component loreComponent = EterniaLib.getChatCommons().parseColor(loreStr);
                item.lore(List.of(loreComponent));
                player.getInventory().setItemInMainHand(item);
                MessageOptions messageOptions = new MessageOptions(loreStr);
                EterniaLib.getChatCommons().sendMessage(player, Messages.ITEM_SET_LORE, messageOptions);
                plugin.getLogger().log(Level.INFO, "Item lore set by {0}", player.getName());
            }

            @Subcommand("%ITEM_SET_NAME")
            @Syntax("%ITEM_SET_NAME_SYNTAX")
            @Description("%ITEM_SET_NAME_DESCRIPTION")
            @CommandPermission("%ITEM_SET_NAME_PERM")
            public void onItemSetName(Player player, String nameStr) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getType() == Material.AIR) {
                    EterniaLib.getChatCommons().sendMessage(player, Messages.ITEM_NOT_FOUND);
                    return;
                }

                Component nameComponent = EterniaLib.getChatCommons().parseColor(nameStr);
                ItemMeta meta = item.getItemMeta();
                meta.displayName(nameComponent);
                item.setItemMeta(meta);
                player.getInventory().setItemInMainHand(item);
                MessageOptions options = new MessageOptions(nameStr);
                EterniaLib.getChatCommons().sendMessage(player, Messages.ITEM_SET_NAME, options);
            }

        }

    }

}
