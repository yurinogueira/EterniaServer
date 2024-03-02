package br.com.eterniaserver.eterniaserver.modules.cash;

import br.com.eterniaserver.eternialib.EterniaLib;
import br.com.eterniaserver.eternialib.chat.MessageMap;
import br.com.eterniaserver.eternialib.configuration.CommandLocale;
import br.com.eterniaserver.eternialib.configuration.enums.ConfigurationCategory;
import br.com.eterniaserver.eternialib.configuration.interfaces.CmdConfiguration;
import br.com.eterniaserver.eternialib.configuration.interfaces.MsgConfiguration;
import br.com.eterniaserver.eternialib.configuration.interfaces.ReloadableConfiguration;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.enums.ItemsKeys;
import br.com.eterniaserver.eterniaserver.enums.Messages;
import br.com.eterniaserver.eterniaserver.enums.Strings;
import br.com.eterniaserver.eterniaserver.modules.Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


final class Configurations {

    private Configurations() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    static class CashMessages implements MsgConfiguration<Messages> {


        private final FileConfiguration inFile = YamlConfiguration.loadConfiguration(new File(getFilePath()));
        private final FileConfiguration outFile = new YamlConfiguration();

        private final MessageMap<Messages, String> messageMap;

        public CashMessages(MessageMap<Messages, String> messageMap) {
            this.messageMap = messageMap;
        }

        @Override
        public FileConfiguration inFileConfiguration() {
            return inFile;
        }

        @Override
        public FileConfiguration outFileConfiguration() {
            return outFile;
        }

        @Override
        public String getFolderPath() {
            return Constants.CASH_MODULE_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.CASH_MESSAGES_FILE_PATH;
        }

        @Override
        public MessageMap<Messages, String> messages() {
            return messageMap;
        }

        @Override
        public ConfigurationCategory category() {
            return ConfigurationCategory.GENERIC;
        }

        @Override
        public void executeConfig() {
            addMessage(Messages.CASH_BALANCE,
                    "Você possui #00aaaa{0} C$#555555.",
                    "quantia de cash"
            );
            addMessage(Messages.CASH_BALANCE_OTHER,
                    "#00aaaa{2}#aaaaaa possui #00aaaa{0} C$#555555.",
                    "quantia de cash",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.CASH_NOTHING_TO_BUY,
                    "Você não está comprando nada#555555."
            );
            addMessage(Messages.CASH_BOUGHT,
                    "Compra confirmada com sucesso#555555."
            );
            addMessage(Messages.CASH_CANCELED,
                    "Compra cancelada com sucesso#555555."
            );
            addMessage(Messages.CASH_RECEVEID,
                    "Você recebeu #00aaaa{0} C$ #aaaaaapor #00aaaa{2}#555555.",
                    "quantia de cash",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.CASH_SENT,
                    "Você enviou #00aaaa{0} C$ #aaaaaapara #00aaaa{2}#555555.",
                    "quantia de cash",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.CASH_LOST,
                    "Você perdeu #00aaaa{0} C$ #aaaaaapor #00aaaa{2}#555555.",
                    "quantia de cash",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.CASH_REMOVED,
                    "Você removeu #00aaaa{0} C$ #aaaaaade #00aaaa{2}#555555.",
                    "quantia de cash",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.CASH_COST,
                    "Isso irá custar #00aaaa{0} C$#555555.",
                    "quantia de cash"
            );
            addMessage(Messages.CASH_CHOOSE,
                    "Use #ffaa00/cash accept #aaaaaaou #ffaa00/cash deny#aaaaaa para aceitar ou negar a compra#555555."
            );
            addMessage(Messages.CASH_NO_HAS,
                    "Você não possui #00aaaa{0} C$#555555.",
                    "quantia de cash"
            );
            addMessage(Messages.CASH_ALREADY_BUYING,
                    "Você já possui uma compra em andamento#555555."
            );
            addMessage(Messages.CASH_NO_CASH,
                    "Você não possui CASH suficiênte#555555."
            );
        }

        @Override
        public void executeCritical() { }

    }

    static class CashCommands implements CmdConfiguration<Enums.Commands> {

        private final FileConfiguration inFile = YamlConfiguration.loadConfiguration(new File(getFilePath()));
        private final FileConfiguration outFile = new YamlConfiguration();

        @Override
        public FileConfiguration inFileConfiguration() {
            return inFile;
        }

        @Override
        public FileConfiguration outFileConfiguration() {
            return outFile;
        }

        @Override
        public String getFolderPath() {
            return Constants.CASH_MODULE_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.CASH_COMMANDS_FILE_PATH;
        }

        @Override
        public ConfigurationCategory category() {
            return ConfigurationCategory.BLOCKED;
        }

        @Override
        public void executeConfig() { }

        @Override
        public void executeCritical() {
            addCommandLocale(Enums.Commands.CASH, new CommandLocale(
                    "cash",
                    "",
                    " Abre a loja de CASH",
                    "eternia.cash",
                    null
            ));
            addCommandLocale(Enums.Commands.CASH_HELP, new CommandLocale(
                    "help|ajuda",
                    " <página>",
                    " Ajuda para o CASH",
                    "eternia.cash",
                    null
            ));
            addCommandLocale(Enums.Commands.CASH_PAY, new CommandLocale(
                    "pay|pagar",
                    " <jogador> <quantia>",
                    " Paga uma quantia de CASH a um jogador",
                    "eternia.cash",
                    null
            ));
            addCommandLocale(Enums.Commands.CASH_ACCEPT, new CommandLocale(
                    "accept|aceitar",
                    "",
                    " Confirma uma compra de CASH",
                    "eternia.cash",
                    null
            ));
            addCommandLocale(Enums.Commands.CASH_DENY, new CommandLocale(
                    "deny|negar",
                    "",
                    " Nega uma compra de CASH",
                    "eternia.cash",
                    null
            ));
            addCommandLocale(Enums.Commands.CASH_BALANCE, new CommandLocale(
                    "balance|saldo",
                    " <jogador>",
                    " Mostra o saldo atual de CASH de um jogador",
                    "eternia.cash",
                    null
            ));
            addCommandLocale(Enums.Commands.CASH_GIVE, new CommandLocale(
                    "give|dar",
                    " <jogador> <quantia>",
                    " Dá uma quantia de CASH a um jogador",
                    "eternia.cash.admin",
                    null
            ));
            addCommandLocale(Enums.Commands.CASH_REMOVE, new CommandLocale(
                    "remove|retirar",
                    " <jogador> <quantia>",
                    " Remove uma quantia de CASH de um jogador",
                    "eternia.cash.admin",
                    null
            ));
        }
    }

    static class CashConfiguration implements ReloadableConfiguration {

        private final FileConfiguration inFile;
        private final FileConfiguration outFile;

        private final EterniaServer plugin;

        protected CashConfiguration(EterniaServer plugin) {
            this.inFile = YamlConfiguration.loadConfiguration(new File(getFilePath()));
            this.outFile = new YamlConfiguration();
            this.plugin = plugin;

            NamespacedKey[] namespacedKeys = plugin.namespacedKeys();
            namespacedKeys[ItemsKeys.CASH_GUI_NAME.ordinal()] = new NamespacedKey(
                    plugin, Constants.TAG_CASH_GUI_NAME
            );
            namespacedKeys[ItemsKeys.CASH_ITEM_COST.ordinal()] = new NamespacedKey(
                    plugin, Constants.TAG_CASH_ITEM_COST
            );
            namespacedKeys[ItemsKeys.CASH_ITEM_MESSAGE.ordinal()] = new NamespacedKey(
                    plugin, Constants.TAG_CASH_ITEM_MESSAGE
            );
            namespacedKeys[ItemsKeys.CASH_ITEM_COMMANDS.ordinal()] = new NamespacedKey(
                    plugin, Constants.TAG_CASH_ITEM_COMMANDS
            );
            namespacedKeys[ItemsKeys.CASH_ITEM_LORE.ordinal()] = new NamespacedKey(
                    plugin, Constants.TAG_CASH_ITEM_LORE
            );
        }

        @Override
        public FileConfiguration inFileConfiguration() {
            return inFile;
        }

        @Override
        public FileConfiguration outFileConfiguration() {
            return outFile;
        }

        @Override
        public String getFolderPath() {
            return Constants.CASH_MODULE_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.CASH_CONFIG_FILE_PATH;
        }

        @Override
        public ConfigurationCategory category() {
            return ConfigurationCategory.GENERIC;
        }

        @Override
        public void executeConfig() {
            final String[] strings = plugin.strings();

            strings[Strings.CASH_MENU_TITLE.ordinal()] = inFile.getString("menu.name.value", "#aaaaaaCash%s");
            strings[Strings.CASH_TITLE.ordinal()] = inFile.getString("menu.glass.name", "#aaaaaaLoja de #00aaaaC.A.S.H.#555555!");
            strings[Strings.CASH_TABLE_NAME.ordinal()] = inFile.getString("table-name.cash", "e_cash");

            PreGUI menu = loadMenuFromFile();
            menu = menu == null ? loadDefaultMenu() : menu;
            PreGUI[] guis = loadGUIsFromFile(menu.getSubGUIs());
            guis = guis.length == 0 ? loadDefaultGUIs() : guis;

            outFile.set("menu.name.value", strings[Strings.CASH_MENU_TITLE.ordinal()]);
            outFile.set("menu.glass.name", strings[Strings.CASH_TITLE.ordinal()]);
            outFile.set("table-name.cash", strings[Strings.CASH_TABLE_NAME.ordinal()]);
            outFile.set("menu.name.info-pt", "Não altere o final (%s)");
            outFile.set("menu.name.info-en", "Don't change the end (%s)");
            outFile.set("menu.size", menu.getItems().length);

            for (int i = 0; i < menu.getItems().length; i++) {
                ItemStack itemStack = menu.getItems()[i];
                if (itemStack != null) {
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    String guiName = itemMeta.getPersistentDataContainer().get(plugin.getKey(ItemsKeys.CASH_GUI_NAME), PersistentDataType.STRING);
                    String itemLore = itemMeta.getPersistentDataContainer().get(plugin.getKey(ItemsKeys.CASH_ITEM_LORE), PersistentDataType.STRING);

                    outFile.set("menu." + i + ".material", itemStack.getType().name());
                    outFile.set("menu." + i + ".name", guiName);
                    if (itemLore != null) {
                        outFile.set("menu." + i + ".lore", Arrays.asList(itemLore.split(";")));
                    }
                }
            }

            for (PreGUI preGUI : guis) {
                if (preGUI != null) {
                    String name = preGUI.getTitle();
                    String entry = name.split(" ")[0].substring(15);
                    ItemStack[] itemStacks = preGUI.getItems();
                    int size = preGUI.getItems().length;

                    outFile.set("guis." + entry + ".size", size);
                    outFile.set("guis." + entry + ".title", name);

                    for (int i = 0; i < size; i++) {
                        final ItemStack itemStack = itemStacks[i];
                        if (itemStack != null) {
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            String guiName = itemMeta.getPersistentDataContainer().get(plugin.getKey(ItemsKeys.CASH_GUI_NAME), PersistentDataType.STRING);
                            String itemLore = itemMeta.getPersistentDataContainer().get(plugin.getKey(ItemsKeys.CASH_ITEM_LORE), PersistentDataType.STRING);
                            String commands = itemMeta.getPersistentDataContainer().get(plugin.getKey(ItemsKeys.CASH_ITEM_COMMANDS), PersistentDataType.STRING);
                            Integer cost = itemMeta.getPersistentDataContainer().get(plugin.getKey(ItemsKeys.CASH_ITEM_COST), PersistentDataType.INTEGER);
                            String messages = itemMeta.getPersistentDataContainer().get(plugin.getKey(ItemsKeys.CASH_ITEM_MESSAGE), PersistentDataType.STRING);

                            outFile.set("guis." + entry + "." + i + ".cost", cost);
                            outFile.set("guis." + entry + "." + i + ".material", itemStack.getType().name());
                            outFile.set("guis." + entry + "." + i + ".name", guiName);
                            if (commands != null) {
                                outFile.set("guis." + entry + "." + i + ".commands", Arrays.asList(commands.split(";")));
                            }
                            if (itemLore != null) {
                                outFile.set("guis." + entry + "." + i + ".lore", Arrays.asList(itemLore.split(";")));
                            }
                            if (messages != null) {
                                outFile.set("guis." + entry + "." + i + ".messages", Arrays.asList(messages.split(";")));
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < menu.getItems().length; i++) {
                if (menu.getItems()[i] == null) {
                    menu.getItems()[i] = getGlass();
                }
            }
            EterniaServer.getGuiAPI().createGUI(menu.getTitle(), menu.getItems());
            for (PreGUI preGUI : guis) {
                if (preGUI != null) {
                    for (int i = 0; i < preGUI.getItems().length; i++) {
                        if (preGUI.getItems()[i] == null) {
                            preGUI.getItems()[i] = getGlass();
                        }
                    }
                    EterniaServer.getGuiAPI().createGUI(preGUI.getTitle(), preGUI.getItems());
                }
            }
        }

        @Override
        public void executeCritical() { }

        private PreGUI loadMenuFromFile() {
            int menuSize = inFile.getInt("menu.size", 0);
            ItemStack[] itemStacks = new ItemStack[menuSize];

            if (menuSize == 0) {
                return null;
            }

            int subGUIs = 0;
            for (int i = 0; i < menuSize; i++) {
                if (inFile.contains("menu." + i)) {
                    ItemStack itemStack = new ItemStack(Material.valueOf(inFile.getString("menu." + i + ".material")));
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    String guiName = inFile.getString("menu." + i + ".name", "");
                    List<String> itemLore = inFile.getStringList("menu." + i + ".lore");

                    itemMeta.displayName(EterniaLib.getChatCommons().parseColor(guiName));
                    itemMeta.lore(itemLore.stream().map(EterniaLib.getChatCommons()::parseColor).collect(Collectors.toList()));

                    itemMeta.getPersistentDataContainer().set(plugin.getKey(ItemsKeys.CASH_ITEM_LORE), PersistentDataType.STRING, String.join(";", itemLore));
                    itemMeta.getPersistentDataContainer().set(plugin.getKey(ItemsKeys.CASH_GUI_NAME), PersistentDataType.STRING, guiName);

                    itemStack.setItemMeta(itemMeta);
                    itemStacks[i] = itemStack;
                    ++subGUIs;
                }
            }

            return subGUIs == 0 ? null : new PreGUI(plugin.getString(Strings.CASH_MENU_TITLE), itemStacks, subGUIs);
        }

        private PreGUI[] loadGUIsFromFile(int guiAmount) {
            PreGUI[] preGUIS = new PreGUI[guiAmount];
            ConfigurationSection section = inFile.getConfigurationSection("guis");

            if (section == null) {
                return new PreGUI[0];
            }

            int guis = 0;
            for (String guiName : section.getKeys(false)) {
                final int guiSize = inFile.getInt("guis." + guiName + ".size", 0);
                final String guiTitle = inFile.getString("guis." + guiName + ".title", "");
                final ItemStack[] itemStacks = new ItemStack[guiSize];

                if (guiSize == 0) {
                    return new PreGUI[0];
                }

                for (int i = 0; i < guiSize; i++) {
                    if (inFile.contains("guis." + guiName + "." + i)) {
                        ItemStack itemStack = new ItemStack(Material.valueOf(inFile.getString("guis." + guiName + "." + i + ".material")));
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        List<String> itemLore = inFile.getStringList("guis." + guiName + "." + i + ".lore");
                        String itemName = inFile.getString("guis." + guiName + "." + i + ".name", "");

                        itemMeta.displayName(EterniaLib.getChatCommons().parseColor(itemName));
                        itemMeta.lore(itemLore.stream().map(EterniaLib.getChatCommons()::parseColor).collect(Collectors.toList()));

                        itemMeta.getPersistentDataContainer().set(
                                plugin.getKey(ItemsKeys.CASH_GUI_NAME),
                                PersistentDataType.STRING,
                                itemName
                        );
                        itemMeta.getPersistentDataContainer().set(
                                plugin.getKey(ItemsKeys.CASH_ITEM_LORE),
                                PersistentDataType.STRING,
                                String.join(
                                        ";",
                                        itemLore
                                )
                        );
                        itemMeta.getPersistentDataContainer().set(
                                plugin.getKey(ItemsKeys.CASH_ITEM_COST),
                                PersistentDataType.INTEGER,
                                inFile.getInt("guis." + guiName + "." + i + ".cost", 0)
                        );
                        itemMeta.getPersistentDataContainer().set(
                                plugin.getKey(ItemsKeys.CASH_ITEM_COMMANDS),
                                PersistentDataType.STRING,
                                String.join(
                                        ";",
                                        inFile.getStringList("guis." + guiName + "." + i + ".commands")
                                )
                        );
                        itemMeta.getPersistentDataContainer().set(
                                plugin.getKey(ItemsKeys.CASH_ITEM_MESSAGE),
                                PersistentDataType.STRING,
                                String.join(
                                        ";",
                                        inFile.getStringList("guis." + guiName + "." + i + ".messages")
                                )
                        );

                        itemStack.setItemMeta(itemMeta);
                        itemStacks[i] = itemStack;
                    }
                }

                preGUIS[guis++] = new PreGUI(guiTitle, itemStacks, 0);
            }

            return preGUIS;
        }

        private PreGUI loadDefaultMenu() {
            ItemStack[] itemStacks = new ItemStack[27];
            ItemStack itemStack = new ItemStack(Material.OAK_SIGN);
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> itemLore = List.of(
                    "#aaaaaaCompre permissões",
                    "#aaaaaaPara lhe ajudar",
                    "#aaaaaanessa jornada#555555."
            );

            itemMeta.displayName(EterniaLib.getChatCommons().parseColor("#aaaaaaPermissões"));
            itemMeta.lore(itemLore.stream().map(EterniaLib.getChatCommons()::parseColor).collect(Collectors.toList()));
            itemMeta.getPersistentDataContainer().set(plugin.getKey(ItemsKeys.CASH_ITEM_LORE), PersistentDataType.STRING, String.join(";", itemLore));
            itemMeta.getPersistentDataContainer().set(plugin.getKey(ItemsKeys.CASH_GUI_NAME), PersistentDataType.STRING, "#aaaaaaPermissões");

            itemStack.setItemMeta(itemMeta);
            itemStacks[10] = itemStack;

            return new PreGUI(plugin.getString(Strings.CASH_MENU_TITLE), itemStacks, 1);
        }

        private PreGUI[] loadDefaultGUIs() {
            PreGUI[] preGUIS = new PreGUI[1];
            ItemStack[] itemStacks = new ItemStack[27];
            ItemStack itemStack = new ItemStack(Material.EXPERIENCE_BOTTLE);
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> itemLore = List.of(
                    "#aaaaaaUpe mais rápido em",
                    "#aaaaaasua especialidade#555555!",
                    "#aaaaaaNão acumulativo#555555.",
                    "",
                    "#aaaaaaDuração#555555: #00aaaa30 dias",
                    "#aaaaaaPreço#555555: #00aaaa70 C$"
            );

            String guiName = "#00aaaa25% #aaaaaaBônus de XP no McMMO#555555!";
            itemMeta.displayName(EterniaLib.getChatCommons().parseColor(guiName));
            itemMeta.lore(itemLore.stream().map(EterniaLib.getChatCommons()::parseColor).collect(Collectors.toList()));

            itemMeta.getPersistentDataContainer().set(
                    plugin.getKey(ItemsKeys.CASH_GUI_NAME),
                    PersistentDataType.STRING,
                    guiName
            );
            itemMeta.getPersistentDataContainer().set(
                    plugin.getKey(ItemsKeys.CASH_ITEM_LORE),
                    PersistentDataType.STRING,
                    String.join(";", itemLore)
            );
            itemMeta.getPersistentDataContainer().set(
                    plugin.getKey(ItemsKeys.CASH_ITEM_COST),
                    PersistentDataType.INTEGER,
                    70
            );
            itemMeta.getPersistentDataContainer().set(
                    plugin.getKey(ItemsKeys.CASH_ITEM_COMMANDS),
                    PersistentDataType.STRING,
                    String.join(
                            ";",
                            List.of(
                                    "lp user %player_name% permission settemp mcmmo.perks.xp.customboost.all true 30d",
                                    "broadcast #aaaaaaO jogador #00aaaa%player_name% #aaaaaacomprou um boost de XP#555555."
                            )
                    )
            );
            itemMeta.getPersistentDataContainer().set(
                    plugin.getKey(ItemsKeys.CASH_ITEM_MESSAGE),
                    PersistentDataType.STRING,
                    String.join(
                            ";",
                            List.of(
                                    "#aaaaaaParabéns pela sua compra#555555!"
                            )
                    )
            );

            itemStack.setItemMeta(itemMeta);
            itemStacks[10] = itemStack;

            preGUIS[0] = new PreGUI("#aaaaaaPermissões", itemStacks, 0);

            return preGUIS;
        }

        private ItemStack getGlass() {
            ItemStack itemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.getPersistentDataContainer().set(
                    plugin.getKey(ItemsKeys.CASH_ITEM_LORE),
                    PersistentDataType.STRING,
                    "a"
            );
            itemMeta.displayName(EterniaLib.getChatCommons().parseColor(plugin.getString(Strings.CASH_TITLE)));
            itemStack.setItemMeta(itemMeta);

            return itemStack;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        private static final class PreGUI {

            private String title;
            private ItemStack[] items;
            private int subGUIs;

        }

    }

}
