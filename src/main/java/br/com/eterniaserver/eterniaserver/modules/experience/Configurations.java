package br.com.eterniaserver.eterniaserver.modules.experience;

import br.com.eterniaserver.eternialib.chat.MessageMap;
import br.com.eterniaserver.eternialib.configuration.CommandLocale;
import br.com.eterniaserver.eternialib.configuration.enums.ConfigurationCategory;
import br.com.eterniaserver.eternialib.configuration.interfaces.CmdConfiguration;
import br.com.eterniaserver.eternialib.configuration.interfaces.MsgConfiguration;
import br.com.eterniaserver.eternialib.configuration.interfaces.ReloadableConfiguration;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.enums.Lists;
import br.com.eterniaserver.eterniaserver.enums.Messages;
import br.com.eterniaserver.eterniaserver.enums.Strings;
import br.com.eterniaserver.eterniaserver.modules.Constants;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

final class Configurations {

    private Configurations() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    static class ExpMessagesConfiguration implements MsgConfiguration<Messages> {

        private final FileConfiguration inFile = YamlConfiguration.loadConfiguration(new File(getFilePath()));
        private final FileConfiguration outFile = new YamlConfiguration();

        private final MessageMap<Messages, String> messageMap;

        public ExpMessagesConfiguration(MessageMap<Messages, String> messageMap) {
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
            return Constants.EXPERIENCE_MODULE_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.EXPERIENCE_MESSAGES_FILE_PATH;
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
            addMessage(Messages.EXP_INVALID_CHOICE,
                    "Argumento inválido#555555, #aaaaaao tipo deve ser #00aaaaxp #aaaaaaou #00aaaalevel#555555."
            );
            addMessage(Messages.EXP_SET_FROM,
                    "Você definiu para #00aaaa{0}#aaaaaa a quantia de #00aaaa{3}#aaaaaa de #00aaaa{2}#555555.",
                    "quantia de exp",
                    "nome do jogador",
                    "apelido do jogador",
                    "exp ou nível"
            );
            addMessage(Messages.EXP_SETED,
                    "A sua quantia de #00aaaa{3}#aaaaaa foi definida para #00aaaa{0}#aaaaaa por #00aaaa{2}#555555.",
                    "quantia de exp",
                    "nome de quem alterou",
                    "apelido de quem alterou",
                    "exp ou nível"
            );
            addMessage(Messages.EXP_REMOVE_FROM,
                    "Você removeu #00aaaa{0} {3}#aaaaaa de #00aaaa{2}#555555.",
                    "quantia de exp",
                    "nome do jogador",
                    "apelido do jogador",
                    "exp ou nível"
            );
            addMessage(Messages.EXP_REMOVED,
                    "Foi retirado #00aaaa{0}#aaaaaa de sua quantia de #00aaaa{3}#aaaaaa por #00aaaa{2}#555555.",
                    "quantia de exp",
                    "nome de quem removeu",
                    "apelido de quem alterou",
                    "exp ou nível"
            );
            addMessage(Messages.EXP_GIVE_FROM,
                    "Você deu #00aaaa{0}#aaaaaa de #00aaaa{3}#aaaaaa para #00aaaa{2}#555555.",
                    "quantia de exp",
                    "nome do jogador",
                    "apelido do jogador",
                    "exp ou nível"
            );
            addMessage(Messages.EXP_GIVED,
                    "Foi recebeu #00aaaa{0} {3}#aaaaaa por #00aaaa{2}#555555.",
                    "quantia de exp",
                    "nome de quem removeu",
                    "apelido de quem alterou",
                    "exp ou nível"
            );
            addMessage(Messages.EXP_BALANCE,
                    "Você possui #00aaaa{0}#aaaaaa níveis de experiência guardados#555555.",
                    "quantia de exp"
            );
            addMessage(Messages.EXP_BOTTLED,
                    "Tome sua garrafinha#555555!"
            );
            addMessage(Messages.EXP_INSUFFICIENT,
                    "Você não possui tudo isso de experiência#555555."
            );
            addMessage(Messages.EXP_WITHDRAW,
                    "Você sacou #00aaaa{0}#aaaaaa níveis de experiência#555555.",
                    "quantia de níveis"
            );
            addMessage(Messages.EXP_DEPOSIT,
                    "Você depositou #00aaaa{0}#aaaaaa níveis de experiência#555555.",
                    "quantia de níveis"
            );
        }

        @Override
        public void executeCritical() {}
    }

    static class ExpCommandsConfiguration implements CmdConfiguration<Enums.Commands> {

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
                return Constants.EXPERIENCE_MODULE_FOLDER_PATH;
            }

            @Override
            public String getFilePath() {
                return Constants.EXPERIENCE_COMMANDS_FILE_PATH;
            }

            @Override
            public ConfigurationCategory category() {
                return ConfigurationCategory.BLOCKED;
            }

            @Override
            public void executeConfig() {}

            @Override
            public void executeCritical() {
                addCommandLocale(Enums.Commands.EXPERIENCE, new CommandLocale(
                        "xp",
                        " <página>",
                        " Ajuda para o sistema de Experiência",
                        "eternia.xp.user",
                        null
                ));
                addCommandLocale(Enums.Commands.EXPERIENCE_SET, new CommandLocale(
                        "set|definir",
                        " <jogador> <quantia> <xp | level>",
                        " Define o nível de experiência de um jogador",
                        "eternia.xp.admin",
                        null
                ));
                addCommandLocale(Enums.Commands.EXPERIENCE_TAKE, new CommandLocale(
                        "take|retirar",
                        " <jogador> <quantia> <xp | level>",
                        " Retira uma quantidade de níveis de experiência de um jogador",
                        "eternia.xp.admin",
                        null
                ));
                addCommandLocale(Enums.Commands.EXPERIENCE_GIVE, new CommandLocale(
                        "give|dar",
                        " <jogador> <quantia> <xp | level>",
                        " Dá uma quantidade de níveis de experiência para um jogador",
                        "eternia.xp.admin",
                        null
                ));
                addCommandLocale(Enums.Commands.EXPERIENCE_CHECK, new CommandLocale(
                        "check|verificar",
                        "",
                        " Verifica a quantia de níveis guardados",
                        "eternia.xp.user",
                        null
                ));
                addCommandLocale(Enums.Commands.EXPERIENCE_BOTTLE, new CommandLocale(
                        "bottle|garrafinhas",
                        " <quantia>",
                        " Enganharra sua experiência atual",
                        "eternia.xp.user",
                        null
                ));
                addCommandLocale(Enums.Commands.EXPERIENCE_WITHDRAW, new CommandLocale(
                        "withdraw|sacar",
                        " <quantia>",
                        " Saca uma quantia de níveis guardados",
                        "eternia.xp.user",
                        null
                ));
                addCommandLocale(Enums.Commands.EXPERIENCE_DEPOSIT, new CommandLocale(
                        "deposit|depositar",
                        " <quantia>",
                        " Deposita uma quantia de níveis",
                        "eternia.xp.user",
                        null
                ));
            }
    }

    static class ExperienceConfiguration implements ReloadableConfiguration {

        private final FileConfiguration inFile;
        private final FileConfiguration outFile;


        private final String[] strings;
        private final List<List<String>> stringLists;

        public ExperienceConfiguration(EterniaServer plugin) {
            this.inFile = YamlConfiguration.loadConfiguration(new File(getFilePath()));
            this.outFile = new YamlConfiguration();

            this.strings = plugin.strings();
            this.stringLists = plugin.stringLists();
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
            return Constants.EXPERIENCE_MODULE_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.EXPERIENCE_CONFIG_FILE_PATH;
        }

        @Override
        public ConfigurationCategory category() {
            return ConfigurationCategory.GENERIC;
        }

        @Override
        public void executeConfig() {
            // Strings
            strings[Strings.MINI_MESSAGES_BOTTLE_EXP_NAME.ordinal()] = inFile.getString("mini-messages.bottle-name", "#aaaaaaGarrafa com #00aaaaExperiência");
            strings[Strings.EXP_XP_LABEL.ordinal()] = inFile.getString("label.xp", "xp");
            strings[Strings.EXP_LEVEL_LABEL.ordinal()] = inFile.getString("label.level", "níveis");
            strings[Strings.EXP_TABLE_NAME.ordinal()] = inFile.getString("table-name.xp", "e_xp");
            // Lists
            List<String> list = inFile.getStringList("mini-messages.bottle-lore");
            stringLists.set(Lists.MINI_MESSAGES_BOTTLE_EXP_LORE.ordinal(), list.isEmpty() ? List.of("#aaaaaaQuantia#555555: #00aaaa%amount%") : list);

            // Strings
            outFile.set("mini-messages.bottle-name", strings[Strings.MINI_MESSAGES_BOTTLE_EXP_NAME.ordinal()]);
            outFile.set("label.xp", strings[Strings.EXP_XP_LABEL.ordinal()]);
            outFile.set("label.level", strings[Strings.EXP_LEVEL_LABEL.ordinal()]);
            outFile.set("table-name.xp", strings[Strings.EXP_TABLE_NAME.ordinal()]);
            // Lists
            outFile.set("mini-messages.bottle-lore", stringLists.get(Lists.MINI_MESSAGES_BOTTLE_EXP_LORE.ordinal()));

        }

        @Override
        public void executeCritical() { }

    }

}
