package br.com.eterniaserver.eterniaserver.configurations.configs;

import br.com.eterniaserver.eterniaserver.Constants;
import br.com.eterniaserver.eterniaserver.api.ServerRelated;
import br.com.eterniaserver.eterniaserver.enums.ChanceMaps;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlocksCfg {

    public BlocksCfg(List<Map<String, Map<Double, List<String>>>> chanceMaps) {
        FileConfiguration blocks = YamlConfiguration.loadConfiguration(new File(Constants.BLOCKS_FILE_PATH));
        FileConfiguration outBlocks = new YamlConfiguration();

        Map<Double, List<String>> tempBlockzinMap = new HashMap<>();
        tempBlockzinMap.put(0.001, List.of("crates givekey %player_name% minerios"));
        tempBlockzinMap.put(0.00005, List.of("crates givekey %player_name% mineriosraros"));

        Map<String, Map<Double, List<String>>> blocksMap = new HashMap<>();

        blocksMap.put("STONE", tempBlockzinMap);

        Map<String, Map<Double, List<String>>> tempBlock = new HashMap<>();

        loadBlocks(blocks, tempBlock, "blocks");

        if (tempBlock.isEmpty()) {
            tempBlock = new HashMap<>(blocksMap);
        }

        blocksMap.clear();
        tempBlock.forEach(blocksMap::put);

        blocksMap.forEach((k, v) -> v.forEach((l, b) -> outBlocks.set("blocks." + k + '.' + String.format("%.10f", l).replace('.', ','), b)));

        chanceMaps.set(ChanceMaps.BLOCK_DROPS.ordinal(), blocksMap);

        Map<Double, List<String>> tempFarmMap = new HashMap<>();
        tempFarmMap.put(0.001, List.of("crates givekey %player_name% farm"));
        tempFarmMap.put(0.00005, List.of("crates givekey %player_name% farmraros"));

        Map<String, Map<Double, List<String>>> farmsMap = new HashMap<>();

        farmsMap.put("CARROT", tempFarmMap);

        Map<String, Map<Double, List<String>>> tempFarm = new HashMap<>();

        loadBlocks(blocks, tempFarm, "farm");

        if (tempFarm.isEmpty()) {
            tempFarm = new HashMap<>(farmsMap);
        }

        farmsMap.clear();
        tempFarm.forEach(farmsMap::put);

        farmsMap.forEach((k, v) -> v.forEach((l, b) -> outBlocks.set("farm." + k + "." + String.format("%.10f", l).replace('.', ','), b)));

        chanceMaps.set(ChanceMaps.FARM_DROPS.ordinal(), farmsMap);

        outBlocks.options().header("Caso precise de ajuda acesse https://github.com/EterniaServer/EterniaServer/wiki");

        try {
            outBlocks.save(Constants.BLOCKS_FILE_PATH);
        } catch (IOException exception) {
            ServerRelated.logError("Impossível de criar arquivos em " + Constants.DATA_LAYER_FOLDER_PATH, 3);
        }

    }

    private void loadBlocks(FileConfiguration blocks, Map<String, Map<Double, List<String>>> tempBlock, String name) {
        ConfigurationSection configurationSection = blocks.getConfigurationSection(name);
        if (configurationSection != null) {
            for (String key : configurationSection.getKeys(false)) {
                Map<Double, List<String>> tempChanceMap = new HashMap<>();
                ConfigurationSection section = blocks.getConfigurationSection(name + "." + key);
                if (section != null) {
                    for (String chance : section.getKeys(false)){
                        tempChanceMap.put(Double.parseDouble(chance.replace(',', '.')), blocks.getStringList(name + "." + key + "." + chance));
                    }
                }
                tempBlock.put(key, tempChanceMap);
            }
        }
    }

}
