package br.com.eterniaserver.eterniaserver.core;

import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.enums.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class PluginClear extends BukkitRunnable {

    private int removed;

    @Override
    public void run() {
        removed = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (Chunk chunk : around(player.getChunk())) {
                cleanupChunk(chunk);
            }
        }
        if (removed != 0) {
            Bukkit.broadcastMessage(EterniaServer.msg.getMessage(Messages.SERVER_REMOVED_ENTITIES, true, String.valueOf(removed)));
        }
    }

    private Collection<Chunk> around(Chunk origin) {
        World world = origin.getWorld();

        int length = (EterniaServer.configs.clearRange * 2) + 1;
        Set<Chunk> chunks = new HashSet<>(length * length);

        int cX = origin.getX();
        int cZ = origin.getZ();

        for (int x = -EterniaServer.configs.clearRange; x <= EterniaServer.configs.clearRange; x++) {
            for (int z = -EterniaServer.configs.clearRange; z <= EterniaServer.configs.clearRange; z++) {
                chunks.add(world.getChunkAt(cX + x, cZ + z));
            }
        }
        return chunks;
    }

    private void cleanupChunk(Chunk chunk) {
        Map<EntityType, Integer> entityIntegerMap = new EnumMap<>(EntityType.class);
        for (Entity e : chunk.getEntities()) {
            if (!(e instanceof Creature)) continue;


            int amount = entityIntegerMap.getOrDefault(e.getType(), 0);

            if (amount > 15) {
                e.remove();
                removed++;
            } else {
                entityIntegerMap.put(e.getType(), amount + 1);
            }
        }
    }

}
