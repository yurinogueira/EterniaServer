package br.com.eterniaserver.events;

import br.com.eterniaserver.EterniaServer;
import br.com.eterniaserver.configs.Messages;
import br.com.eterniaserver.configs.Strings;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class OnBlockBreak implements Listener {

    private final EterniaServer plugin;

    public OnBlockBreak(EterniaServer plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent breakEvent) {
        if (breakEvent.isCancelled()) {
            return;
        }
        if (EterniaServer.configs.getBoolean("modules.spawners")) {
            if (breakEvent.getBlock().getType() == Material.SPAWNER) {
                Block block = breakEvent.getBlock();
                Material material = block.getType();
                Player player = breakEvent.getPlayer();
                if (EterniaServer.configs.getStringList("spawners.blacklisted-worlds").contains(player.getWorld().getName()) && (!player.hasPermission("eternia.spawners.bypass"))) {
                    Messages.PlayerMessage("spawners.block", player);
                    breakEvent.setCancelled(true);
                    return;
                }
                if (player.hasPermission("eternia.spawners.break")) {
                    ItemStack itemInHand = player.getInventory().getItemInMainHand();
                    if (itemInHand.containsEnchantment(Enchantment.SILK_TOUCH) || player.hasPermission("eternia.spawners.nosilk")) {
                        CreatureSpawner spawner = (CreatureSpawner) block.getState();
                        ItemStack item = new ItemStack(material);
                        ItemMeta meta = item.getItemMeta();
                        String mob = spawner.getSpawnedType().toString().replace("_", " ");
                        String mobFormatted = mob.substring(0, 1).toUpperCase() + mob.substring(1).toLowerCase();
                        if (meta != null) {
                            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', ("&8[" + EterniaServer.configs.getString("spawners.mob-name-color") + "%mob% &7Spawner&8]".replace("%mob%", mobFormatted))));
                            List<String> newLore = new ArrayList<>();
                            EterniaServer.configs.getStringList("spawners.lore");
                            if (EterniaServer.configs.getBoolean("spawners.enable-lore")) {
                                for (String line : EterniaServer.configs.getStringList("spawners.lore")) {
                                    newLore.add(ChatColor.translateAlternateColorCodes('&', line.replace("%s", mobFormatted)));
                                }
                                meta.setLore(newLore);
                            }
                        }
                        item.setItemMeta(meta);
                        if (EterniaServer.configs.getDouble("spawners.drop-chance") != 1) {
                            double random = Math.random();
                            if (random >= EterniaServer.configs.getDouble("spawners.drop-chance")) {
                                Messages.PlayerMessage("spawners.no-drop", player);
                                return;
                            }
                        }
                        if (EterniaServer.configs.getBoolean("spawners.drop-in-inventory")) {
                            if (player.getInventory().firstEmpty() == -1) {
                                breakEvent.setCancelled(true);
                                Messages.PlayerMessage("spawners.invfull", player);
                                return;
                            }
                            player.getInventory().addItem(item);
                            block.getDrops().clear();
                            breakEvent.setExpToDrop(0);
                            return;
                        }
                        Location loc = block.getLocation();
                        Objects.requireNonNull(loc.getWorld()).dropItemNaturally(loc, item);
                        breakEvent.setExpToDrop(0);
                    } else {
                        breakEvent.setCancelled(true);
                        Messages.PlayerMessage("spawners.no-silktouch", player);
                    }
                } else {
                    breakEvent.setCancelled(true);
                    Messages.PlayerMessage("server.no-perm", player);
                }
            }
        }
        if (EterniaServer.configs.getBoolean("modules.block-reward")) {
            if (EterniaServer.blocks.contains("Blocks." + breakEvent.getBlock().getType())) {
                ConfigurationSection cs = EterniaServer.blocks.getConfigurationSection("Blocks." + breakEvent.getBlock().getType());
                double randomNumber = new Random().nextDouble();
                if (cs != null) {
                    List<String> mainList = new ArrayList<>(cs.getKeys(true));
                    double lowestNumberAboveRandom = 1.1;
                    for (int i = 1; i < cs.getKeys(true).size(); i++) {
                        double current = Double.parseDouble(mainList.get(i));
                        if (current < lowestNumberAboveRandom && current > randomNumber) {
                            lowestNumberAboveRandom = current;
                        }
                    }
                    if (lowestNumberAboveRandom <= 1) {
                        List<String> stringList = EterniaServer.blocks.getStringList("Blocks." + breakEvent.getBlock().getType() + "." + lowestNumberAboveRandom);
                        for (String command : stringList) {
                            if (Strings.papi) {
                                String modifiedCommand = Messages.putPAPI(breakEvent.getPlayer(), command);
                                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), modifiedCommand);
                            } else {
                                String modifiedCommand = command.replace("%player_name%", breakEvent.getPlayer().getPlayerListName());
                                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), modifiedCommand);
                            }
                        }
                    }
                }
            }
        }
    }

}