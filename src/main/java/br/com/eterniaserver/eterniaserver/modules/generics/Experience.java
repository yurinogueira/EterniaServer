package br.com.eterniaserver.eterniaserver.modules.generics;

import br.com.eterniaserver.eternialib.EFiles;
import br.com.eterniaserver.eternialib.EQueries;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.objects.Checks;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Syntax;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Experience extends BaseCommand {

    private final Checks checks;
    private final EFiles messages;
    private final ExperienceManager exp;

    public Experience(EterniaServer plugin) {
        this.checks = plugin.getChecks();
        this.messages = plugin.getEFiles();
        this.exp = plugin.getExp();

        final String query = "SELECT * FROM " + plugin.serverConfig.getString("sql.table-xp") + ";";
        final HashMap<String, String> temp = EQueries.getMapString(query, "player_name", "xp");

        AtomicInteger x = new AtomicInteger(0);
        temp.forEach((k, v) -> {
            Vars.xp.put(k, Integer.parseInt(v));
            x.getAndIncrement();
            messages.sendConsole("server.load-data",  "%module%", "Experience", "%amount%", x.get());
        });
    }

    @CommandAlias("checklevel|verlevel")
    @CommandPermission("eternia.checklvl")
    public void onCheckLevel(Player player) {
        int lvl = player.getLevel();
        float xp = player.getExp();
        player.setLevel(0);
        player.setExp(0);
        player.giveExp(exp.getExp(player.getName()));
        messages.sendMessage("experience.check", "%amount%", player.getLevel(), player);
        player.setLevel(lvl);
        player.setExp(xp);
    }

    @CommandAlias("bottlelvl|bottleexp|gaffinhas")
    @Syntax("<level>")
    @CommandPermission("eternia.bottlexp")
    public void onBottleLevel(Player player, Integer xp_want) {
        int xp_real = checks.getXPForLevel(player.getLevel());
        if (xp_want > 0 && xp_real > xp_want) {
            ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8[&eGarrafa de EXP&8]"));
                item.setItemMeta(meta);
                item.setLore(Collections.singletonList(String.valueOf(xp_want)));
            }
            PlayerInventory inventory = player.getInventory();
            inventory.addItem(item);
            messages.sendMessage("experience.bottleexp", player);
            player.setLevel(0);
            player.setExp(0);
            player.giveExp(xp_real - xp_want);
        } else {
            messages.sendMessage("experience.insufficient", player);
        }
    }

    @CommandAlias("withdrawlvl|pegarlvl|takelvl")
    @Syntax("<level>")
    @CommandPermission("eternia.withdrawlvl")
    public void onWithdrawLevel(Player player, Integer level) {
        final String playerName = player.getName();

        int xpla = checks.getXPForLevel(level);
        if (exp.getExp(playerName) >= xpla) {
            exp.removeExp(playerName, xpla);
            player.giveExp(xpla);
            messages.sendMessage("experience.withdraw", "%level%", player.getLevel(), player);
        } else {
            messages.sendMessage("experience.insufficient", player);
        }
    }

    @CommandAlias("depositlvl|depositarlvl")
    @Syntax("<level>")
    @CommandPermission("eternia.depositlvl")
    public void onDepositLevel(Player player, Integer xpla) {
        int xp_atual = player.getLevel();
        if (xp_atual >= xpla) {
            int xp = checks.getXPForLevel(xpla);
            int xpto = checks.getXPForLevel(xp_atual);
            exp.addExp(player.getName(), xp);
            messages.sendMessage("experience.deposit", "%amount%", xpla, player);
            player.setLevel(0);
            player.setExp(0);
            player.giveExp(xpto - xp);
        } else {
            messages.sendMessage("experience.insufficient", player);
        }
    }

}
