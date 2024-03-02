package br.com.eterniaserver.eterniaserver.modules.economy;

import br.com.eterniaserver.eternialib.chat.MessageMap;
import br.com.eterniaserver.eternialib.configuration.CommandLocale;
import br.com.eterniaserver.eternialib.configuration.enums.ConfigurationCategory;
import br.com.eterniaserver.eternialib.configuration.interfaces.CmdConfiguration;
import br.com.eterniaserver.eternialib.configuration.interfaces.MsgConfiguration;
import br.com.eterniaserver.eternialib.configuration.interfaces.ReloadableConfiguration;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.enums.Booleans;
import br.com.eterniaserver.eterniaserver.enums.Doubles;
import br.com.eterniaserver.eterniaserver.enums.Integers;
import br.com.eterniaserver.eterniaserver.enums.Messages;
import br.com.eterniaserver.eterniaserver.enums.Strings;
import br.com.eterniaserver.eterniaserver.modules.Constants;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

final class Configurations {

    private Configurations() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }
    static class CommandsConfiguration implements CmdConfiguration<Enums.Commands> {

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
            return Constants.ECONOMY_MODULE_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.ECONOMY_COMMANDS_FILE_PATH;
        }

        @Override
        public ConfigurationCategory category() {
            return ConfigurationCategory.BLOCKED;
        }

        @Override
        public void executeConfig() { }

        @Override
        public void executeCritical() {
            addCommandLocale(Enums.Commands.ECONOMY, new CommandLocale(
                    "money",
                    " <página>",
                    " Ajuda para o sistema monetário",
                    "eternia.economy.user",
                    null
            ));
            addCommandLocale(Enums.Commands.ECONOMY_BALTOP, new CommandLocale(
                    "baltop|balancetop",
                    " <página>",
                    " Mostre a lista de jogadores mais ricos do servidor",
                    "eternia.economy.user",
                    null
            ));
            addCommandLocale(Enums.Commands.ECONOMY_BALANCE, new CommandLocale(
                    "saldo|balance|bal",
                    " <jogador>",
                    " Verifique o seu saldo ou o saldo de outro jogador",
                    "eternia.economy.user",
                    null
            ));
            addCommandLocale(Enums.Commands.ECONOMY_PAY, new CommandLocale(
                    "pagar|pay",
                    " <jogador> <quantia>",
                    " Pague um jogador",
                    "eternia.economy.user",
                    null
            ));
            addCommandLocale(Enums.Commands.ECONOMY_TAKE, new CommandLocale(
                    "take|retirar",
                    " <jogador> <quantia>",
                    " Retire uma quantia do saldo de um jogador",
                    "eternia.economy.admin",
                    null
            ));
            addCommandLocale(Enums.Commands.ECONOMY_GIVE, new CommandLocale(
                    "give|dar",
                    " <jogador> <quantia>",
                    " Dê uma quantia para um jogador",
                    "eternia.economy.admin",
                    null
            ));
            addCommandLocale(Enums.Commands.ECONOMY_BANK, new CommandLocale(
                    "banco|bank",
                    " <página>",
                    " Ajuda para o sistema bancário",
                    "eternia.economy.user",
                    null
            ));
            addCommandLocale(Enums.Commands.ECONOMY_BANK_LIST, new CommandLocale(
                    "list|listar",
                    " <página>",
                    " Lista os bancos do servidor",
                    "eternia.economy.user",
                    null
            ));
            addCommandLocale(Enums.Commands.ECONOMY_BANK_INFO, new CommandLocale(
                    "info",
                    " <banco>",
                    " Verifique as informações de um banco",
                    "eternia.economy.user",
                    null
            ));
            addCommandLocale(Enums.Commands.ECONOMY_BANK_CREATE, new CommandLocale(
                    "criar|create",
                    " <banco>",
                    " Crie um banco",
                    "eternia.economy.user",
                    null
            ));
            addCommandLocale(Enums.Commands.ECONOMY_BANK_DELETE, new CommandLocale(
                    "deletar|delete",
                    " <banco>",
                    " Deleta um banco que você é dono",
                    "eternia.economy.user",
                    null
            ));
            addCommandLocale(Enums.Commands.ECONOMY_BANK_MY_BANKS, new CommandLocale(
                    "meus|my",
                    " <página>",
                    " Lista os bancos que você é membro",
                    "eternia.economy.user",
                    null
            ));
            addCommandLocale(Enums.Commands.ECONOMY_BANK_DEPOSIT, new CommandLocale(
                    "depositar|deposit",
                    " <quantia>",
                    " Deposite uma quantia no banco",
                    "eternia.economy.user",
                    null
            ));
            addCommandLocale(Enums.Commands.ECONOMY_BANK_WITHDRAW, new CommandLocale(
                    "sacar|withdraw",
                    " <quantia>",
                    " Saque uma quantia do banco",
                    "eternia.economy.user",
                    null
            ));
            addCommandLocale(Enums.Commands.ECONOMY_BANK_CHANGE_ROLE, new CommandLocale(
                    "cargo|role",
                    " <jogador> <cargo>",
                    " Mude o cargo de um membro do banco",
                    "eternia.economy.user",
                    null
            ));
            addCommandLocale(Enums.Commands.ECONOMY_BANK_AFFILIATE, new CommandLocale(
                    "afiliar|affiliate",
                    " <banco> <jogador>",
                    " Afiliar-se a um banco",
                    "eternia.economy.user",
                    null
            ));
        }

    }

    static class MessagesConfiguration implements MsgConfiguration<Messages> {

        private final FileConfiguration inFile = YamlConfiguration.loadConfiguration(new File(getFilePath()));
        private final FileConfiguration outFile = new YamlConfiguration();

        private final MessageMap<Messages, String> messageMap;

        public MessagesConfiguration(MessageMap<Messages, String> messageMap) {
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
            return Constants.ECONOMY_MODULE_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.ECONOMY_MESSAGES_FILE_PATH;
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
            addMessage(Messages.ECO_PAGE_LIMIT,
                    "Escolha uma página entre 1 e #00aaaa{0}#555555.",
                    "quantidade de páginas"
            );
            addMessage(Messages.ECO_BALTOP_TITLE,
                    "Mais Ricos#555555,#aaaaaa página #00aaaa{0}#555555.",
                    "número da página atual"
            );
            addMessage(Messages.ECO_BALTOP_LIST,
                    "#00aaaa{1} #555555- #aaaaaaSaldo#555555: #00aaaa{2}",
                    "nome do jogador",
                    "apelido do jogador",
                    "saldo do jogador"
            );
            addMessage(Messages.ECO_PAGE,
                    "#555555---",
                    "Left: Página anterior",
                    "Right: Próxima página"
            );
            addMessage(Messages.ECO_BALANCE_IN_CONSOLE,
                    "Você não pode verificar o seu saldo no console#555555, #aaaaaainforme um jogador#555555."
            );
            addMessage(Messages.ECO_BALANCE,
                    "O saldo de #00aaaa{1}#aaaaaa é #00aaaa{2}#555555.",
                    "nome do jogador",
                    "apelido do jogador",
                    "saldo do jogador"
            );
            addMessage(Messages.ECO_CANT_PAY_YOURSELF,
                    "Você não pode pagar a si mesmo#555555."
            );
            addMessage(Messages.ECO_INVALID_VALUE,
                    "Você deve informar uma quantia válida#555555."
            );
            addMessage(Messages.ECO_INSUFFICIENT_BALANCE,
                    "Saldo suficiente#555555."
            );
            addMessage(Messages.ECO_PAYED,
                    "Você pagou #00aaaa{2}#aaaaaa para #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador",
                    "saldo do jogador"
            );
            addMessage(Messages.ECO_RECEIVED,
                    "Você recebeu #00aaaa{2}#aaaaaa de #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador",
                    "saldo do jogador"
            );
            addMessage(Messages.ECO_GIVED,
                    "Você deu #00aaaa{2}#aaaaaa para #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador",
                    "saldo do jogador"
            );
            addMessage(Messages.ECO_RETIRED,
                    "{1} retirou #00aaaa{2}#aaaaaa do seu saldo#555555.",
                    "nome do jogador",
                    "apelido do jogador",
                    "saldo do jogador"
            );
            addMessage(Messages.ECO_TAKED,
                    "Você retirou #00aaaa{2}#aaaaaa do saldo de #00aaaa{1}#555555.",
                    "nome do jogador",
                    "apelido do jogador",
                    "saldo do jogador"
            );
            addMessage(Messages.ECO_BANK_LIST_TITLE,
                    "Bancos#555555,#aaaaaa página #00aaaa{0}#555555.",
                    "número da página atual"
            );
            addMessage(Messages.ECO_BANK_LIST,
                    "#00aaaa{0} #555555- #aaaaaaSaldo#555555: #00aaaa{1}",
                    "nome do banco",
                    "saldo do banco"
            );
            addMessage(Messages.ECO_BANK_NAME_LIMIT,
                    "O nome do banco deve ter no máximo #00aaaa{0}#aaaaaa caracteres#555555.",
                    "número máximo de caracteres"
            );
            addMessage(Messages.ECO_BANK_NAME_INVALID,
                    "O nome do banco deve conter apenas letras#555555."
            );
            addMessage(Messages.ECO_BANK_NOT_HAS_AMOUNT,
                    "O banco não possui essa quantia#555555."
            );
            addMessage(Messages.ECO_BANK_ALREADY_EXISTS,
                    "O banco #00aaaa{0}#aaaaaa já existe#555555.",
                    "nome do banco"
            );
            addMessage(Messages.ECO_BANK_CREATED,
                    "Você criou o banco #00aaaa{0}#555555.",
                    "nome do banco"
            );
            addMessage(Messages.ECO_BANK_CREATED_BROADCAST,
                    "#00aaaa{1}#aaaaaa criou o banco #00aaaa{2}#555555.",
                    "nome do jogador",
                    "apelido do jogador",
                    "nome do banco"
            );
            addMessage(Messages.ECO_BANK_NOT_EXIST,
                    "O banco #00aaaa{0}#aaaaaa não existe#555555.",
                    "nome do banco"
            );
            addMessage(Messages.ECO_BANK_NOT_MEMBER,
                    "Você não é membro do banco #00aaaa{0}#555555.",
                    "nome do banco"
            );
            addMessage(Messages.ECO_BANK_NOT_OWNER,
                    "Você não é dono do banco #00aaaa{0}#555555.",
                    "nome do banco"
            );
            addMessage(Messages.ECO_BANK_DELETED,
                    "Você deletou o banco #00aaaa{0}#555555.",
                    "nome do banco"
            );
            addMessage(Messages.ECO_BANK_DELETED_BROADCAST,
                    "#00aaaa{1}#aaaaaa deletou o banco #00aaaa{2}#555555.",
                    "nome do jogador",
                    "apelido do jogador",
                    "nome do banco"
            );
            addMessage(Messages.ECO_BANK_ALREADY_HAS_BANK,
                    "Você já possui um banco#555555.",
                    "nome do banco"
            );
            addMessage(Messages.ECO_BANK_NO_BANKS,
                    "Você não possui bancos#555555."
            );
            addMessage(Messages.ECO_BANK_MY_BANKS_TITLE,
                    "Meus Bancos#555555#555555."
            );
            addMessage(Messages.ECO_BANK_MY_BANKS_LIST,
                    "#00aaaa{0} #555555- #aaaaaaCargo#555555: #00aaaa{1}",
                    "nome do banco",
                    "cargo do banco"
            );
            addMessage(Messages.ECO_BANK_DEPOSITED,
                    "Você depositou #00aaaa{1}#aaaaaa no banco #00aaaa{0}#555555.",
                    "nome do banco",
                    "saldo do banco"
            );
            addMessage(Messages.ECO_BANK_NO_WITHDRAW_PERMISSION,
                    "Você não possui permissão para sacar do banco #00aaaa{1}#555555.",
                    "nome do banco"
            );
            addMessage(Messages.ECO_BANK_WITHDRAWN,
                    "Você sacou #00aaaa{1}#aaaaaa do banco #00aaaa{0}#555555.",
                    "nome do banco",
                    "saldo do banco"
            );
            addMessage(Messages.ECO_BANK_TARGET_NOT_MEMBER,
                    "O jogador #00aaaa{2}#aaaaaa não é membro do banco #00aaaa{0}#555555.",
                    "nome do banco",
                    "nome do jogador",
                    "apeliido do jogador"
            );
            addMessage(Messages.ECO_BANK_INVALID_ROLE,
                    "O cargo #00aaaa{1}#aaaaaa não existe no banco #00aaaa{0}#555555.",
                    "nome do banco",
                    "nome do cargo"
            );
            addMessage(Messages.ECO_BANK_CHANGE_ROLE_TO,
                    "Você mudou o cargo de #00aaaa{1}#aaaaaa para #00aaaa{2}#555555.",
                    "nome do jogador",
                    "apelido do jogador",
                    "nome do cargo",
                    "banco"
            );
            addMessage(Messages.ECO_BANK_CHANGE_ROLE,
                    "{1} mudou o seu cargo para #00aaaa{2}#555555.",
                    "nome do jogador",
                    "apelido do jogador",
                    "nome do cargo",
                    "banco"
            );
            addMessage(Messages.ECO_BANK_ALREADY_MEMBER,
                    "Você já é membro do banco #00aaaa{0}#555555.",
                    "nome do banco"
            );
            addMessage(Messages.ECO_BANK_TARGET_NOT_OWNER,
                    "O jogador #00aaaa{2}#aaaaaa não é dono do banco #00aaaa{0}#555555.",
                    "nome do banco",
                    "nome do jogador",
                    "apelido do jogador"
            );
            addMessage(Messages.ECO_BANK_AFILIATE_SUCCESS,
                    "Você afiliou-se ao banco #00aaaa{0}#555555.",
                    "nome do banco"
            );
            addMessage(Messages.ECO_BANK_AFFILIATE_REQUEST,
                    "Você enviou um pedido de afiliação para o banco #00aaaa{2}#555555.",
                    "nome do jogador",
                    "apelido do jogador",
                    "nome do banco"
            );
            addMessage(Messages.ECO_BANK_AFFILIATE_REQUESTED,
                    "O jogador #00aaaa{1}#aaaaaa enviou um pedido de afiliação para o banco #00aaaa{2}#555555.",
                    "nome do jogador",
                    "apelido do jogador",
                    "nome do banco"
            );
            addMessage(Messages.ECO_BANK_INFO_TITLE,
                    "Informações do Banco #00aaaa{0}#555555.",
                    "nome do banco"
            );
            addMessage(Messages.ECO_BANK_INFO_BALANCE,
                    "#aaaaaaSaldo: #00aaaa{0}#555555.",
                    "saldo do banco"
            );
            addMessage(Messages.ECO_BANK_INFO_TAX,
                    "#aaaaaaTaxa: #00aaaa{0}#555555.",
                    "taxa do banco"
            );
            addMessage(Messages.ECO_BANK_INFO_MEMBERS,
                    "#aaaaaaMembro#555555: #00aaaa{1} #aaaaaaCargo#555555: #00aaaa{2}#555555.",
                    "membros do banco"
            );
        }

        @Override
        public void executeCritical() { }

    }

    static class EconomyConfiguration implements ReloadableConfiguration {

        private final EterniaServer plugin;

        private final FileConfiguration inFile = YamlConfiguration.loadConfiguration(new File(getFilePath()));
        private final FileConfiguration outFile = new YamlConfiguration();

        public EconomyConfiguration(EterniaServer plugin) {
            this.plugin = plugin;
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
            return Constants.ECONOMY_MODULE_FOLDER_PATH;
        }

        @Override
        public String getFilePath() {
            return Constants.ECONOMY_CONFIG_FILE_PATH;
        }

        @Override
        public ConfigurationCategory category() {
            return ConfigurationCategory.GENERIC;
        }

        @Override
        public void executeConfig() {
            String[] strings = plugin.strings();
            int[] integers = plugin.integers();
            boolean[] booleans = plugin.booleans();
            double[] doubles = plugin.doubles();

            integers[Integers.ECONOMY_COIN_DIGITS.ordinal()] = inFile.getInt("eco.digits", 2);
            integers[Integers.ECONOMY_BALANCE_TOP_SIZE.ordinal()] = inFile.getInt("eco.balance-top.size", 30);
            integers[Integers.ECONOMY_BALANCE_TOP_REFRESH_TIME.ordinal()] = inFile.getInt("eco.balance-top.refresh-time", 300);
            integers[Integers.ECONOMY_BANK_TAX_REFRESH_TIME.ordinal()] = inFile.getInt("critical-config.bank-tax.refresh-time", 3600);
            integers[Integers.ECONOMY_BANK_NAME_SIZE_LIMIT.ordinal()] = inFile.getInt("critical-config.bank-name-size-limit", 20);

            booleans[Booleans.ECONOMY_HAS_BANK.ordinal()] = inFile.getBoolean("bank.enable", true);

            doubles[Doubles.ECO_START_MONEY.ordinal()] = inFile.getDouble("eco.start-money", 100D);
            doubles[Doubles.ECO_BANK_TAX_VALUE.ordinal()] = inFile.getDouble("critical-config.bank-tax.value", 0.025);
            doubles[Doubles.ECO_BANK_CREATE_COST.ordinal()] = inFile.getDouble("critical-config.bank-create-cost", 10000D);

            strings[Strings.ECO_NAME.ordinal()] = inFile.getString("label.name", "EterniaEco");
            strings[Strings.ECO_LANGUAGE.ordinal()] = inFile.getString("eco.language", "pt");
            strings[Strings.ECO_COUNTRY.ordinal()] = inFile.getString("eco.country", "BR");
            strings[Strings.ECO_COIN_NAME.ordinal()] = inFile.getString("label.coin.s-name", "EterniaCoin");
            strings[Strings.ECO_COIN_PLURAL_NAME.ordinal()] = inFile.getString("label.coin.p-name", "EterniaCoins");
            strings[Strings.ECO_TABLE_NAME_BALANCE.ordinal()] = inFile.getString("critical-config.table-name.balance", "e_eco_balance");
            strings[Strings.ECO_TABLE_NAME_BANK.ordinal()] = inFile.getString("critical-config.table-name.bank", "e_eco_bank");
            strings[Strings.ECO_TABLE_NAME_BANK_MEMBER.ordinal()] = inFile.getString("critical-config.table-name.bank_member", "e_eco_bank_member");
            strings[Strings.ECO_BALTOP_COMMAND_NAME.ordinal()] = inFile.getString("critical-config.baltop-command-name", "baltop");
            strings[Strings.ECO_BANK_LIST_COMMAND_NAME.ordinal()] = inFile.getString("critical-config.bank-list-command-name", "bank list");

            outFile.set("eco.digits", integers[Integers.ECONOMY_COIN_DIGITS.ordinal()]);
            outFile.set("eco.balance-top.size", integers[Integers.ECONOMY_BALANCE_TOP_SIZE.ordinal()]);
            outFile.set("eco.balance-top.refresh-time", integers[Integers.ECONOMY_BALANCE_TOP_REFRESH_TIME.ordinal()]);
            outFile.set("critical-config.bank-tax.refresh-time", integers[Integers.ECONOMY_BANK_TAX_REFRESH_TIME.ordinal()]);
            outFile.set("critical-config.bank-name-size-limit", integers[Integers.ECONOMY_BANK_NAME_SIZE_LIMIT.ordinal()]);

            outFile.set("bank.enable", booleans[Booleans.ECONOMY_HAS_BANK.ordinal()]);

            outFile.set("eco.start-money", doubles[Doubles.ECO_START_MONEY.ordinal()]);
            outFile.set("critical-config.bank-tax.value", doubles[Doubles.ECO_BANK_TAX_VALUE.ordinal()]);
            outFile.set("critical-config.bank-create-cost", doubles[Doubles.ECO_BANK_CREATE_COST.ordinal()]);

            outFile.set("label.name", strings[Strings.ECO_NAME.ordinal()]);
            outFile.set("eco.language", strings[Strings.ECO_LANGUAGE.ordinal()]);
            outFile.set("eco.country", strings[Strings.ECO_COUNTRY.ordinal()]);
            outFile.set("label.coin.s-name", strings[Strings.ECO_COIN_NAME.ordinal()]);
            outFile.set("label.coin.p-name", strings[Strings.ECO_COIN_PLURAL_NAME.ordinal()]);
            outFile.set("critical-config.table-name.balance", strings[Strings.ECO_TABLE_NAME_BALANCE.ordinal()]);
            outFile.set("critical-config.table-name.bank", strings[Strings.ECO_TABLE_NAME_BANK.ordinal()]);
            outFile.set("critical-config.table-name.bank_member", strings[Strings.ECO_TABLE_NAME_BANK_MEMBER.ordinal()]);
            outFile.set("critical-config.baltop-command-name", strings[Strings.ECO_BALTOP_COMMAND_NAME.ordinal()]);
            outFile.set("critical-config.bank-list-command-name", strings[Strings.ECO_BANK_LIST_COMMAND_NAME.ordinal()]);
        }

        @Override
        public void executeCritical() { }
    }

}
