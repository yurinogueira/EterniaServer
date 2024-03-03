package br.com.eterniaserver.eterniaserver.modules.core;

import br.com.eterniaserver.eternialib.chat.MessageMap;
import br.com.eterniaserver.eternialib.configuration.CommandLocale;
import br.com.eterniaserver.eternialib.configuration.enums.ConfigurationCategory;
import br.com.eterniaserver.eternialib.configuration.interfaces.CmdConfiguration;
import br.com.eterniaserver.eternialib.configuration.interfaces.MsgConfiguration;
import br.com.eterniaserver.eternialib.configuration.interfaces.ReloadableConfiguration;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.enums.Booleans;
import br.com.eterniaserver.eterniaserver.enums.Integers;
import br.com.eterniaserver.eterniaserver.enums.ItemsKeys;
import br.com.eterniaserver.eterniaserver.enums.Lists;
import br.com.eterniaserver.eterniaserver.enums.Messages;
import br.com.eterniaserver.eterniaserver.enums.Strings;
import br.com.eterniaserver.eterniaserver.modules.Constants;
import br.com.eterniaserver.eterniaserver.modules.core.Utils.CommandData;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

final class Configurations {

    private Configurations() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    static class CommandsConfiguration implements CmdConfiguration<Enums.Commands> {

        private final FileConfiguration inFile;
        private final FileConfiguration outFile;

        CommandsConfiguration() {
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
            return Constants.DATA_LAYER_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.CORE_COMMANDS_FILE_PATH;
        }

        @Override
        public ConfigurationCategory category() {
            return ConfigurationCategory.BLOCKED;
        }

        @Override
        public void executeConfig() { }

        @Override
        public void executeCritical() {
            addCommandLocale(Enums.Commands.BROADCAST, new CommandLocale(
                    "broadcast",
                    " <prefix> <mensagem>",
                    " Envia uma mensagem global ao servidor",
                    "eternia.broadcast",
                    null
            ));
            addCommandLocale(Enums.Commands.GAMEMODE, new CommandLocale(
                    "gamemode|gm",
                    " <página>",
                    " Ajuda para o sistema de Gamemode",
                    "eternia.gamemode",
                    null
            ));
            addCommandLocale(Enums.Commands.GAMEMODE_SURVIVAL, new CommandLocale(
                    "survival|s|0",
                    " <página>",
                    " Define o modo de jogo seu, ou de outro jogador para Sobrevivência",
                    "eternia.gamemode",
                    null
            ));
            addCommandLocale(Enums.Commands.GAMEMODE_CREATIVE, new CommandLocale(
                    "creative|c|1",
                    " <página>",
                    " Define o modo de jogo seu, ou de outro jogador para Criativo",
                    "eternia.gamemode",
                    null
            ));
            addCommandLocale(Enums.Commands.GAMEMODE_ADVENTURE, new CommandLocale(
                    "adventure|a|2",
                    " <página>",
                    " Define o modo de jogo seu, ou de outro jogador para Aventura",
                    "eternia.gamemode",
                    null
            ));
            addCommandLocale(Enums.Commands.GAMEMODE_SPECTATOR, new CommandLocale(
                    "spectator|sp|3",
                    " <página>",
                    " Define o modo de jogo seu, ou de outro jogador para Espectador",
                    "eternia.gamemode",
                    null
            ));
            addCommandLocale(Enums.Commands.AFK, new CommandLocale(
                    "afk",
                    null,
                    " Entra no modo AFK",
                    "eternia.afk",
                    null
            ));
            addCommandLocale(Enums.Commands.GODMODE, new CommandLocale(
                    "god",
                    null,
                    " Entra em GodMode",
                    "eternia.god",
                    null
            ));
            addCommandLocale(Enums.Commands.HAT, new CommandLocale(
                    "hat|capacete",
                    null,
                    " Coloca o item na sua mão na sua cabeça",
                    "eternia.hat",
                    null
            ));
            addCommandLocale(Enums.Commands.WORKBENCH, new CommandLocale(
                    "workbench|craftingtable|crafting",
                    null,
                    " Abre uma mesa de trabalho",
                    "eternia.workbench",
                    null
            ));
            addCommandLocale(Enums.Commands.ENDERCHEST, new CommandLocale(
                    "enderchest|ender|chest",
                    null,
                    " Abre um baú de ender",
                    "eternia.enderchest",
                    null
            ));
            addCommandLocale(Enums.Commands.OPENINV, new CommandLocale(
                    "openinv|inv",
                    " <jogador>",
                    " Abre o inventário de outro jogador",
                    "eternia.openinv",
                    null
            ));
            addCommandLocale(Enums.Commands.MEM, new CommandLocale(
                    "mem",
                    null,
                    " Veja informações sobre o servidor",
                    "eternia.mem",
                    null
            ));
            addCommandLocale(Enums.Commands.MEM_ALL, new CommandLocale(
                    "memall",
                    null,
                    " Mostre globalmente as informações sobre o servidor",
                    "eternia.mem.all",
                    null
            ));
            addCommandLocale(Enums.Commands.CONDENSER, new CommandLocale(
                    "blocks|condenser|compactar",
                    null,
                    " Comprima seus minérios em blocos para liberar espaço",
                    "eternia.blocks",
                    null
            ));
            addCommandLocale(Enums.Commands.PROFILE, new CommandLocale(
                    "profile",
                    " <jogador>",
                    " Veja o seu perfil ou o de outro jogador",
                    "eternia.profile",
                    null
            ));
            addCommandLocale(Enums.Commands.SUICIDE, new CommandLocale(
                    "suicide|suicidio",
                    " <mensagem>",
                    " Envie uma mensagem e se mate",
                    "eternia.suicide",
                    null
            ));
            addCommandLocale(Enums.Commands.SPEED, new CommandLocale(
                    "speed",
                    " <velocidade>",
                    " Altere a sua velocidade",
                    "eternia.speed",
                    null
            ));
            addCommandLocale(Enums.Commands.FEED, new CommandLocale(
                    "feed",
                    " <jogador>",
                    " Sacie-se ou sacie outra pessoa",
                    "eternia.feed",
                    null
            ));
            addCommandLocale(Enums.Commands.THOR, new CommandLocale(
                    "thor",
                    " <jogador>",
                    " Chame o Thor para punir um jogador",
                    "eternia.thor",
                    null
            ));
            addCommandLocale(Enums.Commands.FLY, new CommandLocale(
                    "fly|voar",
                    " <jogador>",
                    " VOA BRABULETA!!!",
                    "eternia.fly",
                    null
            ));
        }

    }

    static class MessagesConfiguration implements MsgConfiguration<Messages> {

        private final FileConfiguration inFile = YamlConfiguration.loadConfiguration(new File(getFilePath()));
        private final FileConfiguration outFile = new YamlConfiguration();

        private final MessageMap<Messages, String> messages;

        public MessagesConfiguration(MessageMap<Messages, String> messages) {
            this.messages = messages;
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
            return Constants.DATA_LAYER_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.CORE_MESSAGES_FILE_PATH;
        }

        @Override
        public MessageMap<Messages, String> messages() {
            return messages;
        }

        @Override
        public ConfigurationCategory category() {
            return ConfigurationCategory.GENERIC;
        }

        @Override
        public void executeConfig() {
            addMessage(Messages.SERVER_PREFIX,
                    "#555555[#34eb40E#3471ebS#555555]#AAAAAA "
            );
            addMessage(Messages.SERVER_NO_PLAYER,
                    "Somente jogadores podem utilizar esse comando#555555."
            );
            addMessage(Messages.GAMEMODE_SETED,
                    "Seu modo de jogo foi definido para #00aaaa{0}#555555.",
                    "modo de jogo"
            );
            addMessage(Messages.GAMEMODE_SET_FROM,
                    "O modo de jogo de #00aaaa{2}#aaaaaa foi definido para #00aaaa{0}#555555.",
                    "modo de jogo",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.GAMEMODE_NOT_BY_CONSOLE,
                    "Você precisa informar o nome de um jogador online#555555."
            );
            addMessage(Messages.AFK_AUTO_ENTER,
                    "#00aaaa{1} #aaaaaaficou ausente e agora está AFK#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.AFK_ENTER,
                    "#00aaaa{1} #aaaaaaestá AFK#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.AFK_LEAVE,
                    "#00aaaa{1} #aaaaaanão está mais AFK#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.AFK_BROADCAST_KICK,
                    "#00aaaa{1} #aaaaaaficou ausente e foi kickado#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.AFK_KICKED,
                    "#aaaaaaVocê foi kickado por estar ausente#555555."
            );
            addMessage(Messages.GODMODE_ENABLED,
                    "Você ativou o God Mode#555555."
            );
            addMessage(Messages.GODMODE_DISABLED,
                    "Vocẽ desativou o God Mode#555555."
            );
            addMessage(Messages.GODMODE_DISABLED_TO,
                    "Você desativou o God Mode de #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.GODMODE_DISABLED_BY,
                    "God Mode desativado por #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.GODMODE_ENABLED_TO,
                    "Você ativou o God Mode de #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.GODMODE_ENABLED_BY,
                    "God Mode ativado por #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.SERVER_NO_PERM,
                    "Você não possui permissão para isso#555555."
            );
            addMessage(Messages.ITEM_NOT_FOUND,
                    "Nenhum item foi encontrado em sua mão#555555."
            );
            addMessage(Messages.ITEM_HELMET,
                    "Você equipou seu caçapete#555555."
            );
            addMessage(Messages.STATS_MEM,
                    "Memória#555555: #00aaaa{0}MB#555555/#00aaaa{1}MB#555555.",
                    "memória usada",
                    "memória total"
            );
            addMessage(Messages.STATS_HOURS,
                    "Tempo online#555555: #aaaaaaDias#555555: #00aaaa{0} #aaaaaahoras#555555: #00aaaa{1} #aaaaaaminutos#555555: #00aaaa{2} #aaaaaasegundos#555555: #00aaaa{3}#555555.",
                    "dias",
                    "horas",
                    "minutos",
                    "segundos"
            );
            addMessage(Messages.ITEM_CONDENSER,
                    "Compactando blocos."
            );
            addMessage(Messages.PROFILE_TITLE,
                    "#555555[]====[#aaaaaaPerfil#555555]====[]"
            );
            addMessage(Messages.PROFILE_REGISTER_DATA,
                    "#aaaaaaRegistro#555555: #00aaaa{0}#555555."
            );
            addMessage(Messages.PROFILE_LAST_LOGIN,
                    "#aaaaaaUltimo login#555555: #00aaaa{0}#555555."
            );
            addMessage(Messages.PROFILE_ACCOUNT_PLAYED_TIME,
                    "#aaaaaaTempo jogado#555555: "
            );
            addMessage(Messages.SUICIDE_BROADCAST,
                    "#00aaaa{1} #aaaaaadisse#555555: #00aaaa{2} #aaaaaae logo após se matou#555555.",
                    "nome do jogador",
                    "apelido do jogador",
                    "mensagem"
            );
            addMessage(Messages.SPEED_INVALID,
                    "Você deve informar um valor entre 1 e 10#555555."
            );
            addMessage(Messages.SPEED_SETED,
                    "Você alterou a velocidade de movimento para #00aaaa{0}#555555.",
                    "velocidade de movimento"
            );
            addMessage(Messages.FEED_SETED_TO,
                    "Você alimentou #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.FEED_SETED_BY,
                    "Você foi alimentado por #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.FEED_SETED,
                    "Você alimentou-se#555555."
            );
            addMessage(Messages.THOR_SETED_TO,
                    "Você chamou o Thor para punir #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.THOR_SETED_BY,
                    "Você foi punido por #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.LEFT_PVP,
                    "Você saiu do PvP#555555."
            );
            addMessage(Messages.ENTERED_PVP,
                    "Você entrou em PvP#555555."
            );
            addMessage(Messages.FLY_DISABLED_ENTERED_PVP,
                    "Vôo desativado por estar em PvP#555555."
            );
            addMessage(Messages.FLY_DISABLED_TO,
                    "Você desativou o vôo de #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.FLY_DISABLED_BY,
                    "Vôo desativado por #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.FLY_ENABLED_TO,
                    "Você ativou o vôo de #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.FLY_ENABLED_BY,
                    "Vôo ativado por #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.FLY_PVP_BLOCKED,
                    "Você não pode ativar o vôo em PvP#555555."
            );
            addMessage(Messages.FLY_WORLD_BLOCKED,
                    "Você não pode ativar o vôo nesse mundo#555555."
            );
            addMessage(Messages.FLY_DISABLED,
                    "Você desativou o vôo#555555."
            );
            addMessage(Messages.FLY_ENABLED,
                    "Você ativou o vôo#555555."
            );
            addMessage(Messages.SERVER_TPS,
                    "TPS#555555: #00aaaa{0}#555555.",
                    "valor do TPS"
            );
            addMessage(Messages.SERVER_LOGIN,
                    "#00aaaa{1} #555555[#55FF55+#555555]",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.SERVER_LOGOUT,
                    "#00aaaa{1} #555555[#FF5555-#555555]",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.ALREADY_IN_TIMING,
                    "Você já está em um processo de teleporte#555555."
            );
            addMessage(Messages.ALREADY_IN_CONFIRMATION,
                    "Você já está em um processo de confirmação#555555."
            );
        }

        @Override
        public void executeCritical() { }

    }

    static class MainConfiguration implements ReloadableConfiguration {

        private final FileConfiguration inFile;
        private final FileConfiguration outFile;

        private final boolean[] booleans;
        private final int[] integers;
        private final String[] strings;

        private final List<List<String>> stringLists;
        private final Map<String, CommandData> commandDataMap;

        protected MainConfiguration(EterniaServer plugin, Map<String, CommandData> commandDataMap) {
            this.inFile = YamlConfiguration.loadConfiguration(new File(getFilePath()));
            this.outFile = new YamlConfiguration();

            this.booleans = plugin.booleans();
            this.integers = plugin.integers();
            this.strings = plugin.strings();
            this.stringLists = plugin.stringLists();
            this.commandDataMap = commandDataMap;

            NamespacedKey[] namespacedKeys = plugin.namespacedKeys();

            namespacedKeys[ItemsKeys.TAG_FUNCTION.ordinal()] = new NamespacedKey(
                    plugin, Constants.TAG_FUNCTION
            );
            namespacedKeys[ItemsKeys.TAG_INT_VALUE.ordinal()] = new NamespacedKey(
                    plugin, Constants.TAG_INT_VALUE
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
            return Constants.DATA_MODULE_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.CORE_CONFIG_FILE_PATH;
        }

        @Override
        public ConfigurationCategory category() {
            return ConfigurationCategory.BLOCKED;
        }

        @Override
        public void executeConfig() {
            // Booleans
            booleans[Booleans.MODULE_SPAWNERS.ordinal()] = inFile.getBoolean("modules.spawners", true);
            booleans[Booleans.MODULE_EXPERIENCE.ordinal()] = inFile.getBoolean("modules.experience", true);
            booleans[Booleans.MODULE_ELEVATOR.ordinal()] = inFile.getBoolean("modules.elevator", true);
            booleans[Booleans.MODULE_REWARDS.ordinal()] = inFile.getBoolean("modules.rewards", true);
            booleans[Booleans.MODULE_GLOW.ordinal()] = inFile.getBoolean("modules.glow", true);
            booleans[Booleans.MODULE_PAPI.ordinal()] = inFile.getBoolean("modules.papi", true);
            booleans[Booleans.MODULE_CASH.ordinal()] = inFile.getBoolean("modules.cash", true);
            booleans[Booleans.MODULE_ENTITY.ordinal()] = inFile.getBoolean("modules.entity", true);
            booleans[Booleans.MODULE_ECONOMY.ordinal()] = inFile.getBoolean("modules.economy", true);
            booleans[Booleans.MODULE_BED.ordinal()] = inFile.getBoolean("modules.bed", true);
            booleans[Booleans.MODULE_ITEM.ordinal()] = inFile.getBoolean("modules.item", true);
            booleans[Booleans.MODULE_KIT.ordinal()] = inFile.getBoolean("modules.kit", true);
            booleans[Booleans.MODULE_TELEPORT.ordinal()] = inFile.getBoolean("modules.teleport", true);
            booleans[Booleans.MODULE_CHAT.ordinal()] = inFile.getBoolean("modules.chat", true);
            booleans[Booleans.AFK_KICK.ordinal()] = inFile.getBoolean("afk.kick-if-no-perm", true);
            booleans[Booleans.HAS_ECONOMY_PLUGIN.ordinal()] = inFile.getBoolean("critical-configs.has-economy-plugin", true);
            booleans[Booleans.CUSTOM_COMMANDS.ordinal()] = inFile.getBoolean("server.custom-commands", true);
            // Integers
            integers[Integers.PLUGIN_TICKS.ordinal()] = inFile.getInt("critical-configs.plugin-ticks", 20);
            integers[Integers.AFK_TIMER.ordinal()] = inFile.getInt("afk.limit-time", 900);
            integers[Integers.COOLDOWN.ordinal()] = inFile.getInt("critical-configs.teleport-ticks", 80);
            integers[Integers.PVP_TIME.ordinal()] = inFile.getInt("critical-configs.pvp-time", 10);
            // Strings
            strings[Strings.DATA_FORMAT.ordinal()] = inFile.getString("format.data-time", "dd/MM/yyyy HH:mm");
            strings[Strings.MINI_MESSAGES_SERVER_SERVER_LIST.ordinal()] = inFile.getString("mini-messages.motd", "            <color:#69CEDB>⛏ <gradient:#111111:#112222>❱---❰</gradient> <gradient:#6FE657:#6892F2>EterniaServer</gradient> <gradient:#112222:#111111>❱---❰</gradient> <color:#69CEDB>⛏\n                     <gradient:#926CEB:#6892F2>COMEBACK UPDATE</gradient>");
            strings[Strings.PERM_AFK.ordinal()] = inFile.getString("afk.perm-to-stay-afk", "eternia.afk");
            strings[Strings.PERM_TIMING_BYPASS.ordinal()] = inFile.getString("teleport.timing-bypass", "eternia.timing.bypass");
            strings[Strings.GUI_SECRET.ordinal()] = inFile.getString("secret.value", String.format("#%06x", new Random().nextInt(0xffffff + 1)));
            strings[Strings.PERM_EC_OTHER.ordinal()] = inFile.getString("permissions.ec-other", "eternia.enderchest.other");
            strings[Strings.REVISION_TABLE_NAME.ordinal()] = inFile.getString("table-name.revision", "e_revision");
            strings[Strings.PROFILE_TABLE_NAME.ordinal()] = inFile.getString("table-name.player-profile", "e_player_profile");
            strings[Strings.CONS_ADVENTURE.ordinal()] = inFile.getString("const.gm.adventure", "aventura");
            strings[Strings.CONS_CREATIVE.ordinal()] = inFile.getString("const.gm.creative", "criativo");
            strings[Strings.CONS_SPECTATOR.ordinal()] = inFile.getString("const.gm.spectator", "espectador");
            strings[Strings.CONS_SURVIVAL.ordinal()] = inFile.getString("const.gm.survival", "sobrevivência");
            strings[Strings.PROFILE_PLAYED_TIME.ordinal()] = inFile.getString("mini-messages.profile.played-time", "#aaaaaaDias#555555: #00aaaa%02d #aaaaaaHoras#555555: #00aaaa%02d #aaaaaaMinutos#555555: #00aaaa%02d#555555.");
            strings[Strings.PERM_FLY_OTHER.ordinal()] = inFile.getString("permissions.fly.other", "eternia.fly.other");
            strings[Strings.PERM_FLY_BYPASS.ordinal()] = inFile.getString("permissions.fly.bypass", "eternia.fly.bypass");
            strings[Strings.JOIN_NAMES.ordinal()] = inFile.getString("messages.join-names-display", "#00aaaa{0}#AAAAAA, ");
            strings[Strings.PERM_BASE_COMMAND.ordinal()] = inFile.getString("permissions.base-command", "eternia.");
            strings[Strings.PERM_GOD_MODE.ordinal()] = inFile.getString("permissions.god-mode", "eternia.god");

            // Lists
            List<String> blackedCommands = inFile.getStringList("critical-configs.blocked-commands");
            stringLists.set(Lists.BLACKLISTED_COMMANDS.ordinal(), blackedCommands.isEmpty() ? List.of("/op", "/deop", "/stop") : blackedCommands);
            List<String> profileMessages = inFile.getStringList("mini-messages.profile.custom-messages");
            stringLists.set(Lists.PROFILE_CUSTOM_MESSAGES.ordinal(), profileMessages.isEmpty() ? List.of("#aaaaaaSaldo em C.A.S.H.#555555: #00aaaa%eterniaserver_cash%#555555.") : profileMessages);
            List<String> flyBlockedWorlds = inFile.getStringList("critical-configs.blocked-worlds-fly");
            stringLists.set(Lists.BLACKLISTED_WORLDS_FLY.ordinal(), flyBlockedWorlds.isEmpty() ? List.of("world_nether", "world_the_end") : flyBlockedWorlds);

            commandDataMap.put(
                    "discord",
                    new CommandData(
                            "Informa o link do discord",
                            new ArrayList<>(),
                            new ArrayList<>(),
                            List.of("#555555[#34eb40E#3471ebS#555555]#AAAAAA Entre em nosso discord#555555: #00aaaa<a:https://discord.gg/Qs3RxMqEterniaServer</a#555555."),
                            false
                    )
            );
            commandDataMap.put(
                    "facebook",
                    new CommandData(
                            "Informa o link do facebook",
                            new ArrayList<>(),
                            new ArrayList<>(),
                            List.of("#555555[#34eb40E#3471ebS#555555]#AAAAAA Curta nossa página no facebook#555555: #00aaaa<a:https://www.facebook.com/EterniaServerEterniaServer</a#555555."),
                            false
                    )
            );
            commandDataMap.put(
                    "global",
                    new CommandData(
                            "Envia uma mensagem global",
                            List.of("g"),
                            List.of("channel global"),
                            new ArrayList<>(),
                            false
                    )
            );
            commandDataMap.put(
                    "local",
                    new CommandData(
                            "Envia uma mensagem local",
                            List.of("l"),
                            List.of("channel local"),
                            new ArrayList<>(),
                            false
                    )
            );

            Map<String, CommandData> tempCustomCommandMap = new HashMap<>();
            ConfigurationSection commandsConfig = inFile.getConfigurationSection("custom-commands");
            if (commandsConfig != null) {
                for (String key : commandsConfig.getKeys(false)) {
                    tempCustomCommandMap.put(
                            key,
                            new CommandData(
                                    inFile.getString("custom-commands." + key + ".description"),
                                    inFile.getStringList("custom-commands." + key + ".aliases"),
                                    inFile.getStringList("custom-commands." + key + ".command"),
                                    inFile.getStringList("custom-commands." + key + ".text"),
                                    inFile.getBoolean("custom-commands." + key + ".console")
                            )
                    );
                }
            }

            if (tempCustomCommandMap.isEmpty()) {
                tempCustomCommandMap = new HashMap<>(commandDataMap);
            }

            commandDataMap.clear();
            commandDataMap.putAll(tempCustomCommandMap);

            tempCustomCommandMap.forEach((k, v) -> {
                outFile.set("custom-commands." + k + ".description", v.description());
                outFile.set("custom-commands." + k + ".aliases", v.aliases());
                outFile.set("custom-commands." + k + ".command", v.commands());
                outFile.set("custom-commands." + k + ".text", v.text());
                outFile.set("custom-commands." + k + ".console", v.console());
            });

            // Booleans
            outFile.set("modules.spawners", booleans[Booleans.MODULE_SPAWNERS.ordinal()]);
            outFile.set("modules.experience", booleans[Booleans.MODULE_EXPERIENCE.ordinal()]);
            outFile.set("modules.elevator", booleans[Booleans.MODULE_ELEVATOR.ordinal()]);
            outFile.set("modules.rewards", booleans[Booleans.MODULE_REWARDS.ordinal()]);
            outFile.set("modules.glow", booleans[Booleans.MODULE_GLOW.ordinal()]);
            outFile.set("modules.papi", booleans[Booleans.MODULE_PAPI.ordinal()]);
            outFile.set("modules.cash", booleans[Booleans.MODULE_CASH.ordinal()]);
            outFile.set("modules.entity", booleans[Booleans.MODULE_ENTITY.ordinal()]);
            outFile.set("modules.economy", booleans[Booleans.MODULE_ECONOMY.ordinal()]);
            outFile.set("modules.bed", booleans[Booleans.MODULE_BED.ordinal()]);
            outFile.set("modules.item", booleans[Booleans.MODULE_ITEM.ordinal()]);
            outFile.set("modules.kit", booleans[Booleans.MODULE_KIT.ordinal()]);
            outFile.set("modules.teleport", booleans[Booleans.MODULE_TELEPORT.ordinal()]);
            outFile.set("modules.chat", booleans[Booleans.MODULE_CHAT.ordinal()]);
            outFile.set("afk.kick-if-no-perm", booleans[Booleans.AFK_KICK.ordinal()]);
            outFile.set("critical-configs.has-economy-plugin", booleans[Booleans.HAS_ECONOMY_PLUGIN.ordinal()]);
            outFile.set("server.custom-commands", booleans[Booleans.CUSTOM_COMMANDS.ordinal()]);
            // Integers
            outFile.set("critical-configs.plugin-ticks", integers[Integers.PLUGIN_TICKS.ordinal()]);
            outFile.set("afk.limit-time", integers[Integers.AFK_TIMER.ordinal()]);
            outFile.set("critical-configs.teleport-ticks", integers[Integers.COOLDOWN.ordinal()]);
            outFile.set("critical-configs.pvp-time", integers[Integers.PVP_TIME.ordinal()]);
            // Strings
            outFile.set("format.data-time", strings[Strings.DATA_FORMAT.ordinal()]);
            outFile.set("mini-messages.motd", strings[Strings.MINI_MESSAGES_SERVER_SERVER_LIST.ordinal()]);
            outFile.set("afk.perm-to-stay-afk", strings[Strings.PERM_AFK.ordinal()]);
            outFile.set("teleport.timing-bypass", strings[Strings.PERM_TIMING_BYPASS.ordinal()]);
            outFile.set("secret.value", strings[Strings.GUI_SECRET.ordinal()]);
            outFile.set("secret.info-pt", "Não exponha esse código hex");
            outFile.set("secret.info-en", "Don't expose this hex code");
            outFile.set("permissions.ec-other", strings[Strings.PERM_EC_OTHER.ordinal()]);
            outFile.set("table-name.revision", strings[Strings.REVISION_TABLE_NAME.ordinal()]);
            outFile.set("table-name.player-profile", strings[Strings.PROFILE_TABLE_NAME.ordinal()]);
            outFile.set("mini-messages.profile.played-time", strings[Strings.PROFILE_PLAYED_TIME.ordinal()]);
            outFile.set("permissions.fly.other", strings[Strings.PERM_FLY_OTHER.ordinal()]);
            outFile.set("permissions.fly.bypass", strings[Strings.PERM_FLY_BYPASS.ordinal()]);
            outFile.set("messages.join-names-display", strings[Strings.JOIN_NAMES.ordinal()]);
            outFile.set("permissions.base-command", strings[Strings.PERM_BASE_COMMAND.ordinal()]);
            outFile.set("permissions.god-mode", strings[Strings.PERM_GOD_MODE.ordinal()]);
            // Lists
            outFile.set("critical-configs.blocked-commands", stringLists.get(Lists.BLACKLISTED_COMMANDS.ordinal()));
            outFile.set("mini-messages.profile.custom-messages", stringLists.get(Lists.PROFILE_CUSTOM_MESSAGES.ordinal()));
            outFile.set("critical-configs.blocked-worlds-fly", stringLists.get(Lists.BLACKLISTED_WORLDS_FLY.ordinal()));
        }

        @Override
        public void executeCritical() { }

    }

}
