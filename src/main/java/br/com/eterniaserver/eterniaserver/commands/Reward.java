package br.com.eterniaserver.eterniaserver.commands;

import br.com.eterniaserver.acf.annotation.CommandAlias;
import br.com.eterniaserver.acf.annotation.CommandPermission;
import br.com.eterniaserver.acf.annotation.Description;
import br.com.eterniaserver.acf.annotation.Syntax;
import br.com.eterniaserver.eternialib.EQueries;
import br.com.eterniaserver.acf.BaseCommand;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.enums.Messages;
import br.com.eterniaserver.eterniaserver.core.APIServer;
import br.com.eterniaserver.eterniaserver.Constants;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.security.SecureRandom;

public class Reward extends BaseCommand {

    private final SecureRandom random = new SecureRandom();
    private final byte[] bytes = new byte[20];

    public Reward() {
        APIServer.updateRewardMap(EQueries.getMapString(Constants.getQuerySelectAll(EterniaServer.configs.tableRewards), "code", "group_name"));
        Bukkit.getConsoleSender().sendMessage(EterniaServer.msg.getMessage(Messages.SERVER_DATA_LOADED, true, "Keys", String.valueOf(APIServer.getRewardMapSize())));
    }

    @CommandAlias("%usekey")
    @Syntax("%usekey_syntax")
    @Description("%usekey_description")
    @CommandPermission("%usekey_perm")
    public void onUseKey(Player player, String key) {
        if (APIServer.hasReward(key)) {
            giveReward(APIServer.getReward(key), player);
            deleteKey(key);
            APIServer.removeReward(key);
        } else {
            EterniaServer.msg.sendMessage(player, Messages.REWARD_INVALID_KEY, key);
        }
    }

    @CommandAlias("%genkey")
    @Syntax("%genkey_syntax")
    @Description("%genkey_description")
    @CommandPermission("%genkey_perm")
    public void onGenKey(CommandSender sender, String reward) {
        if (EterniaServer.rewards.rewardsMap.containsKey(reward)) {
            random.nextBytes(bytes);
            final String key = Long.toHexString(random.nextLong());
            createKey(reward, key);
            APIServer.addReward(key, reward);
            EterniaServer.msg.sendMessage(sender, Messages.REWARD_CREATED, key);
        } else {
            EterniaServer.msg.sendMessage(sender, Messages.REWARD_NOT_FOUND, reward);
        }
    }

    private void createKey(final String grupo, String key) {
        EQueries.executeQuery(Constants.getQueryInsert(EterniaServer.configs.tableRewards, "code", key, "group_name", grupo));
    }

    private void deleteKey(final String key) {
        EQueries.executeQuery(Constants.getQueryDelete(EterniaServer.configs.tableRewards, "code", key));
    }

    private void giveReward(String group, Player player) {
        EterniaServer.rewards.rewardsMap.get(group).forEach((chance, lista) -> {
            if (Math.random() <= chance) {
                for (String command : lista) {
                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), APIServer.setPlaceholders(player, command));
                }
            }
        });
    }

}
