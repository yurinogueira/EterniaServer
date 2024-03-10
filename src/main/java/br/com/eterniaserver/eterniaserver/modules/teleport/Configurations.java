package br.com.eterniaserver.eterniaserver.modules.teleport;

import br.com.eterniaserver.eternialib.chat.MessageMap;
import br.com.eterniaserver.eternialib.configuration.CommandLocale;
import br.com.eterniaserver.eternialib.configuration.enums.ConfigurationCategory;
import br.com.eterniaserver.eternialib.configuration.interfaces.CmdConfiguration;
import br.com.eterniaserver.eternialib.configuration.interfaces.MsgConfiguration;
import br.com.eterniaserver.eternialib.configuration.interfaces.ReloadableConfiguration;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.enums.Integers;
import br.com.eterniaserver.eterniaserver.enums.ItemsKeys;
import br.com.eterniaserver.eterniaserver.enums.Messages;
import br.com.eterniaserver.eterniaserver.enums.Strings;
import br.com.eterniaserver.eterniaserver.modules.Constants;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

final class Configurations {

    private Configurations() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    static class TeleportMessagesConfiguration implements MsgConfiguration<Messages> {

        private final FileConfiguration inFile = YamlConfiguration.loadConfiguration(new File(getFilePath()));
        private final FileConfiguration outFile = new YamlConfiguration();

        private final MessageMap<Messages, String> messageMap;

        public TeleportMessagesConfiguration(MessageMap<Messages, String> messageMap) {
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
            return Constants.TELEPORT_MODULE_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.TELEPORT_MESSAGES_FILE_PATH;
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
            addMessage(Messages.TELEPORT_ON_GOING,
                    "Teleportando para #00aaaa{0}",
                    "local"
            );
            addMessage(Messages.HOME_DELETED,
                    "Home #00aaaa{0}#aaaaaa deletada com sucesso#555555.",
                    "home"
            );
            addMessage(Messages.HOME_NOT_FOUND,
                    "Home #00aaaa{0}#aaaaaa não encontrada#555555.",
                    "home"
            );
            addMessage(Messages.HOME_LIST,
                    "Lista de homes#555555: #00aaaa{0}#555555.",
                    "lista de homes"
            );
            addMessage(Messages.HOME_STRING_LIMIT,
                    "O nome da home não pode ter mais de #00aaaa{0}#aaaaaa caracteres#555555.",
                    "limite de caracteres"
            );
            addMessage(Messages.HOME_CREATED,
                    "Home #00aaaa{0}#aaaaaa criada com sucesso#555555.",
                    "home"
            );
            addMessage(Messages.HOME_NO_PERM_TO_COMPASS,
                    "Você não tem permissão para criar homes extras com bússolas#555555."
            );
            addMessage(Messages.HOME_ITEM_NAME,
                    "#555555[#00aaaa{0}#555555]",
                    "home"
            );
            addMessage(Messages.HOME_LIMIT_REACHED,
                    "Você atingiu o limite de homes#555555."
            );
            addMessage(Messages.TPA_REQUESTED_TO,
                    "Solicitação de teleporte para #00aaaa{1}#aaaaaa enviada#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.TPA_REQUESTED_FROM,
                    "Solicitação de teleporte de #00aaaa{1}#aaaaaa recebida#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.TPA_HERE_REQUESTED_TO,
                    "Solicitação de teleporte de #00aaaa{1}#aaaaaa até você enviada#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.TPA_HERE_REQUESTED_FROM,
                    "Solicitação de teleporte para #00aaaa{1}#aaaaaa recebida#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.WARP_UPDATED,
                    "Warp #00aaaa{0}#aaaaaa atualizada com sucesso#555555.",
                    "warp"
            );
            addMessage(Messages.WARP_CREATED,
                    "Warp #00aaaa{0}#aaaaaa criada com sucesso#555555.",
                    "warp"
            );
            addMessage(Messages.WARP_NOT_FOUND,
                    "Warp #00aaaa{0}#aaaaaa não encontrada#555555.",
                    "warp"
            );
            addMessage(Messages.WARP_TELEPORTING,
                    "Teleportando para #00aaaa{0}#aaaaaa.",
                    "warp"
            );
            addMessage(Messages.WARP_DELETED,
                    "Warp #00aaaa{0}#aaaaaa deletada com sucesso#555555.",
                    "warp"
            );
            addMessage(Messages.WARP_LIST,
                    "Lista de warps#555555: #00aaaa{0}#555555.",
                    "lista de warps"
            );
            addMessage(Messages.SPAWN_NOT_DEFINED,
                    "O spawn não foi definido#555555."
            );
            addMessage(Messages.SPAWN_TELEPORTING,
                    "Teleportando para o spawn#555555."
            );
        }

        @Override
        public void executeCritical() { }
    }

    static class TeleportCommandsConfiguration implements CmdConfiguration<Enums.Commands> {

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
            return Constants.TELEPORT_MODULE_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.TELEPORT_COMMANDS_FILE_PATH;
        }

        @Override
        public ConfigurationCategory category() {
            return ConfigurationCategory.BLOCKED;
        }

        @Override
        public void executeConfig() { }

        @Override
        public void executeCritical() {
            addCommandLocale(Enums.Commands.HOME, new CommandLocale(
                    "home",
                    " <nome>",
                    " Teleporta para uma home",
                    "eternia.home.user",
                    null
            ));
            addCommandLocale(Enums.Commands.HOMES, new CommandLocale(
                    "homes",
                    null,
                    " Veja a lista de suas homes",
                    "eternia.home.user",
                    null
            ));
            addCommandLocale(Enums.Commands.SETHOME, new CommandLocale(
                    "sethome",
                    " <nome>",
                    " Define uma home",
                    "eternia.sethome.user",
                    null
            ));
            addCommandLocale(Enums.Commands.DELHOME, new CommandLocale(
                    "delhome",
                    " <nome>",
                    " Deleta uma home",
                    "eternia.delhome.user",
                    null
            ));
            addCommandLocale(Enums.Commands.TPA, new CommandLocale(
                    "tpa",
                    " <jogador>",
                    " Solicita um teleporte até um jogador",
                    "eternia.tpa",
                    null
            ));
            addCommandLocale(Enums.Commands.TPAHERE, new CommandLocale(
                    "tpahere",
                    " <jogador>",
                    " Solicita que um jogador se teleporte até você",
                    "eternia.tpahere",
                    null
            ));
            addCommandLocale(Enums.Commands.TPALL, new CommandLocale(
                    "tpall",
                    null,
                    " Teleporta todos os jogadores para você",
                    "eternia.tpall",
                    null
            ));
            addCommandLocale(Enums.Commands.WARPS, new CommandLocale(
                    "warps",
                    null,
                    " Veja a lista de warps",
                    "eternia.warp.user",
                    null
            ));
            addCommandLocale(Enums.Commands.WARP, new CommandLocale(
                    "warp",
                    " <nome>",
                    " Teleporta para um warp",
                    "eternia.warp.user",
                    null
            ));
            addCommandLocale(Enums.Commands.SETWARP, new CommandLocale(
                    "setwarp",
                    " <nome>",
                    " Define um warp",
                    "eternia.setwarp",
                    null
            ));
            addCommandLocale(Enums.Commands.DELWARP, new CommandLocale(
                    "delwarp",
                    " <nome>",
                    " Deleta um warp",
                    "eternia.delwarp",
                    null
            ));
            addCommandLocale(Enums.Commands.SPAWN, new CommandLocale(
                    "spawn",
                    null,
                    " Teleporta para o spawn",
                    "eternia.spawn.user",
                    null
            ));
            addCommandLocale(Enums.Commands.SETSPAWN, new CommandLocale(
                    "setspawn",
                    null,
                    " Define o spawn",
                    "eternia.setspawn",
                    null
            ));
        }
    }

    static class TeleportConfiguration implements ReloadableConfiguration {

        private final EterniaServer plugin;

        private final FileConfiguration inFile = YamlConfiguration.loadConfiguration(new File(getFilePath()));
        private final FileConfiguration outFile = new YamlConfiguration();

        public TeleportConfiguration(EterniaServer plugin) {
            this.plugin = plugin;

            NamespacedKey[] namespaceKeys = plugin.namespacedKeys();

            namespaceKeys[ItemsKeys.TAG_WORLD.ordinal()] = new NamespacedKey(plugin, Constants.TAG_WORLD);
            namespaceKeys[ItemsKeys.TAG_COORD_X.ordinal()] = new NamespacedKey(plugin, Constants.TAG_COORD_X);
            namespaceKeys[ItemsKeys.TAG_COORD_Y.ordinal()] = new NamespacedKey(plugin, Constants.TAG_COORD_Y);
            namespaceKeys[ItemsKeys.TAG_COORD_Z.ordinal()] = new NamespacedKey(plugin, Constants.TAG_COORD_Z);
            namespaceKeys[ItemsKeys.TAG_COORD_YAW.ordinal()] = new NamespacedKey(plugin, Constants.TAG_COORD_YAW);
            namespaceKeys[ItemsKeys.TAG_COORD_PITCH.ordinal()] = new NamespacedKey(plugin, Constants.TAG_COORD_PITCH);
            namespaceKeys[ItemsKeys.TAG_LOC_NAME.ordinal()] = new NamespacedKey(plugin, Constants.TAG_LOC_NAME);
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
            return Constants.TELEPORT_MODULE_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.TELEPORT_CONFIG_FILE_PATH;
        }

        @Override
        public ConfigurationCategory category() {
            return ConfigurationCategory.GENERIC;
        }

        @Override
        public void executeConfig() {
            String[] strings = plugin.strings();
            int[] integers = plugin.integers();

            strings[Strings.TELEPORT_TABLE_NAME_HOME.ordinal()] = inFile.getString("database.table_name_home", "e_home_location");
            strings[Strings.PERM_TELEPORT_TIME_BYPASS.ordinal()] = inFile.getString("permissions.teleport_time_bypass", "eternia.teleport.time.bypass");
            strings[Strings.PERM_HOME_OTHER.ordinal()] = inFile.getString("permissions.home_others", "eternia.home.others");
            strings[Strings.PERM_HOME_COMPASS.ordinal()] = inFile.getString("permissions.home_compass", "eternia.home.compass");
            strings[Strings.PERM_SETHOME_LIMIT_PREFIX.ordinal()] = inFile.getString("permissions.sethome_limit_prefix", "eternia.sethome.limit.");
            strings[Strings.TELEPORT_TABLE_NAME_WARP.ordinal()] = inFile.getString("database.table_name_warp", "e_warp_location");
            strings[Strings.PERM_TELEPORT_PREFIX.ordinal()] = inFile.getString("permissions.teleport_prefix", "eternia.warp.");

            integers[Integers.TELEPORT_TIMER.ordinal()] = inFile.getInt("teleport_timer", 5);

            outFile.set("database.table_name_home", strings[Strings.TELEPORT_TABLE_NAME_HOME.ordinal()]);
            outFile.set("permissions.teleport_time_bypass", strings[Strings.PERM_TELEPORT_TIME_BYPASS.ordinal()]);
            outFile.set("permissions.home_others", strings[Strings.PERM_HOME_OTHER.ordinal()]);
            outFile.set("permissions.home_compass", strings[Strings.PERM_HOME_COMPASS.ordinal()]);
            outFile.set("permissions.sethome_limit_prefix", strings[Strings.PERM_SETHOME_LIMIT_PREFIX.ordinal()]);
            outFile.set("database.table_name_warp", strings[Strings.TELEPORT_TABLE_NAME_WARP.ordinal()]);
            outFile.set("permissions.teleport_prefix", strings[Strings.PERM_TELEPORT_PREFIX.ordinal()]);

            outFile.set("teleport_timer", integers[Integers.TELEPORT_TIMER.ordinal()]);
        }

        @Override
        public void executeCritical() { }
    }

}
