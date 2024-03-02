package br.com.eterniaserver.eterniaserver.modules.bed;

import br.com.eterniaserver.eternialib.chat.MessageMap;
import br.com.eterniaserver.eternialib.configuration.enums.ConfigurationCategory;
import br.com.eterniaserver.eternialib.configuration.interfaces.MsgConfiguration;
import br.com.eterniaserver.eternialib.configuration.interfaces.ReloadableConfiguration;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.enums.Integers;
import br.com.eterniaserver.eterniaserver.enums.Lists;
import br.com.eterniaserver.eterniaserver.enums.Messages;
import br.com.eterniaserver.eterniaserver.modules.Constants;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

final class Configurations {

    private Configurations() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    static class BedMessages implements MsgConfiguration<Messages> {

        private final FileConfiguration inFile = YamlConfiguration.loadConfiguration(new File(getFilePath()));
        private final FileConfiguration outFile = new YamlConfiguration();

        private final MessageMap<Messages, String> messageMap;

        public BedMessages(MessageMap<Messages, String> messageMap) {
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
            return Constants.BED_MODULE_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.BED_MESSAGES_FILE_PATH;
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
            addMessage(Messages.NIGHT_SKIPPED,
                    "A noite passou em #00aaaa{0}#555555.",
                    "mundo"
            );
            addMessage(Messages.NIGHT_SKIPPING,
                    "A noite está passando em #00aaaa{0}#555555.",
                    "mundo"
            );
            addMessage(Messages.NIGHT_PLAYER_SLEEPING,
                    "#00aaaa{0}#aaaaaa está dormindo#555555, #aaaaaadurma também para passar a noite mais rápido#555555.",
                    "jogador",
                    "mundo"
            );
        }

        @Override
        public void executeCritical() { }

    }

    static class BedConfiguration implements ReloadableConfiguration {

        private final EterniaServer plugin;

        private final FileConfiguration inFile;
        private final FileConfiguration outFile;

        BedConfiguration(EterniaServer plugin) {
            this.plugin = plugin;
            this.inFile = YamlConfiguration.loadConfiguration(new File(getFilePath()));
            this.outFile = new YamlConfiguration();
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
            return Constants.BED_MODULE_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.BED_CONFIG_FILE_PATH;
        }

        @Override
        public ConfigurationCategory category() {
            return ConfigurationCategory.BLOCKED;
        }

        @Override
        public void executeConfig() {
            int[] integers = plugin.integers();
            List<List<String>> stringLists = plugin.stringLists();

            integers[Integers.BED_CHECK_TIME.ordinal()] = inFile.getInt("bed-check-time", 40);
            integers[Integers.NIGHT_SPEED.ordinal() ] = inFile.getInt("night-speed", 300);

            List<String> blackListedWorldsSleeping = inFile.getStringList("blacklisted-worlds-sleeping");
            stringLists.set(Lists.BLACKLISTED_WORLDS_SLEEP.ordinal(), blackListedWorldsSleeping.isEmpty() ? List.of("world_nether", "world_the_end") : blackListedWorldsSleeping);

            outFile.set("bed-check-time", integers[Integers.BED_CHECK_TIME.ordinal()]);
            outFile.set("night-speed", integers[Integers.NIGHT_SPEED.ordinal()]);

            outFile.set("blacklisted-worlds-sleeping", blackListedWorldsSleeping);
        }

        @Override
        public void executeCritical() { }
    }

}
