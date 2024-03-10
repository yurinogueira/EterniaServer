package br.com.eterniaserver.eterniaserver.modules.item;

import br.com.eterniaserver.eternialib.EterniaLib;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.enums.ItemsKeys;
import br.com.eterniaserver.eterniaserver.modules.Constants;

import lombok.AllArgsConstructor;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

final class Utils {

    private Utils() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    public static boolean executeLogic(EterniaServer plugin, Player player, String logicBefore) {
        if (logicBefore == null || logicBefore.isEmpty()) {
            return true;
        }

        logicBefore = plugin.setPlaceholders(player, logicBefore);
        String[] logics = logicBefore.split(";");
        if (logics.length > 1) {
            for (String logic : logics) {
                if (!runLogic(logic)) {
                    return false;
                }
            }
            return true;
        }

        return runLogic(logicBefore);
    }

    private static boolean runLogic(String logicLine) {
        String[] logic = logicLine.split(" ");
        return switch (logic[0]) {
            case "RAND" -> executeRand(logic[1]);
            case "IF" -> executeIf(logic[1], logic[2], logic[3]);
            default -> true;
        };
    }

    private static boolean executeRand(String percent) {
        double chance = Double.parseDouble(percent);
        return chance > Math.random();
    }

    private static boolean executeIf(String firstVariable, String operator, String secondVariable) {
        return switch (operator) {
            case "=" -> firstVariable.equals(secondVariable);
            case "!" -> !firstVariable.equals(secondVariable);
            case ">" -> Double.parseDouble(firstVariable) > Double.parseDouble(secondVariable);
            case "<" -> Double.parseDouble(firstVariable) < Double.parseDouble(secondVariable);
            default -> true;
        };
    }

    @AllArgsConstructor
    public static class JsonItem {

        /*
         * Json Example:
         * {
         *  "name": "&aItem Name",
         *  "type": "DIAMOND_SWORD",
         *  "lore": ["#aaff99Lore 1", "#cacacaLore 2"],
         *  "enchantments": ["FROST_WALKER,2", "DURABILITY,3"],
         *  "unbreakable": true,
         *  "usages": 5,
         *  "consoleRun": true,
         *  "logic": "RAND 0.25;IF %player_name% ! Cafecoina;IF %vault_eco_balance% > 1000;",
         *  "success": "broadcast Cafecoina joga urf;give %player_name% diamond 1",
         *  "fail": "broadcast Cafecoina joga lol;give %player_name% charcoal 1",
         * }
         */

        private final String name;
        private final String type;
        private final List<String> lore;
        private final List<String> enchantments;
        private final int usages;
        private final boolean consoleRun;
        private final Boolean unbreakable;
        private final String success;
        private final String fail;

        /*
         * Examples:
         *   RAND 0.25 -> 25% chance to execute the commands
         *   IF %player_name% = Cafecoina -> Execute the commands if the player name is Cafecoina
         *   IF %player_name% ! Cafecoina -> Execute the commands if the player name is not Cafecoina
         *   IF %vault_eco_balance% > 1000 -> Execute the commands if the player has more than 1000 in the economy
         *   IF %vault_eco_balance% < 1000 -> Execute the commands if the player has less than 1000 in the economy
         */
        private final String logic;

        public ItemStack getItemStack(EterniaServer plugin) {
            ItemStack itemStack = new ItemStack(Material.valueOf(type));

            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.displayName(EterniaLib.getChatCommons().parseColor(name));
            itemMeta.lore(lore.stream().map(EterniaLib.getChatCommons()::parseColor).toList());

            itemMeta.getPersistentDataContainer().set(plugin.getKey(ItemsKeys.TAG_FUNCTION), PersistentDataType.INTEGER, Constants.FUNCTION_CUSTOM_ITEM);
            itemMeta.getPersistentDataContainer().set(plugin.getKey(ItemsKeys.TAG_RUN_IN_CONSOLE), PersistentDataType.INTEGER, consoleRun ? 1 : 0);
            itemMeta.getPersistentDataContainer().set(plugin.getKey(ItemsKeys.TAG_USAGES), PersistentDataType.INTEGER, usages);
            itemMeta.getPersistentDataContainer().set(plugin.getKey(ItemsKeys.TAG_LOGIC_BEFORE), PersistentDataType.STRING, logic);
            itemMeta.getPersistentDataContainer().set(plugin.getKey(ItemsKeys.TAG_RUN_COMMAND), PersistentDataType.STRING, success);
            itemMeta.getPersistentDataContainer().set(plugin.getKey(ItemsKeys.TAG_FAIL_COMMAND), PersistentDataType.STRING, fail);

            if (enchantments != null && !enchantments.isEmpty()) {
                for (String enchant : enchantments) {
                    String[] enchantKeyValue = enchant.split(",");
                    NamespacedKey enchantKey = NamespacedKey.fromString(enchantKeyValue[0]);
                    if (enchantKey != null) {
                        Enchantment enchantment = Registry.ENCHANTMENT.get(enchantKey);
                        if (enchantment != null) {
                            itemMeta.addEnchant(enchantment, Integer.parseInt(enchantKeyValue[1]), true);
                        }
                    }
                }
            }

            if (Boolean.TRUE.equals(unbreakable)) {
                itemMeta.setUnbreakable(true);
            }

            itemStack.setItemMeta(itemMeta);

            return itemStack;
        }

    }

}
