package br.com.eterniaserver.eterniaserver.handlers;

import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.api.CashRelated;
import br.com.eterniaserver.eterniaserver.objects.EntityControl;
import br.com.eterniaserver.eterniaserver.objects.User;
import br.com.eterniaserver.eterniaserver.enums.Booleans;
import br.com.eterniaserver.eterniaserver.enums.Strings;
import br.com.eterniaserver.eterniaserver.enums.Messages;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class EntityHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            User user = new User((Player) event.getEntity());
            if (user.getGodMode()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player player = (Player) event.getDamager();
            User user = new User(player);
            if (player.isFlying() && !player.hasPermission(EterniaServer.getString(Strings.PERM_FLY_BYPASS))) {
                user.setIsOnPvP();
                user.sendMessage(Messages.FLY_ENTER_PVP);
                player.setAllowFlight(false);
                player.setFlying(false);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityBreed(EntityBreedEvent event) {
        if (!EterniaServer.getBoolean(Booleans.MODULE_ENTITY) || !EterniaServer.getBoolean(Booleans.BREEDING_LIMITER)) {
            return;
        }

        EntityType entityType = event.getEntityType();
        EntityControl entityControl = EterniaServer.getControl(entityType);

        if (entityControl.getBreedingLimit() == -1) {
            return;
        }

        int amount = 0;
        for (Entity entity : event.getEntity().getLocation().getChunk().getEntities()) {
            if (entity.getType().ordinal() == entityType.ordinal()) {
                if (amount > entityControl.getBreedingLimit()) {
                    event.setCancelled(true);
                    break;
                }
                amount++;
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onEntityInventoryClick(InventoryClickEvent e) {
        if ((!EterniaServer.getBoolean(Booleans.MODULE_CASH) && !EterniaServer.getBoolean(Booleans.MODULE_SPAWNERS)) || e.isCancelled()) {
            return;
        }

        final Player player = (Player) e.getWhoClicked();
        final ItemStack itemStack = e.getCurrentItem();
        if (itemStack != null && (EterniaServer.getBoolean(Booleans.PREVENT_ANVIL)
                && EterniaServer.getBoolean(Booleans.MODULE_SPAWNERS)
                && e.getInventory().getType() == InventoryType.ANVIL
                && itemStack.getType() == Material.SPAWNER)) {
            e.setCancelled(true);
            EterniaServer.sendMessage(player, Messages.SPAWNER_CANT_CHANGE_NAME);
        }

        if (EterniaServer.getBoolean(Booleans.MODULE_CASH)) {
            String title = e.getView().getTitle();
            if ("Cash".equals(title)) {
                CashRelated.menuGui(player, e.getSlot());
                e.setCancelled(true);
            } else if (EterniaServer.getGuisInvert().containsKey(title)) {
                CashRelated.permGui(player, title, e.getSlot());
                e.setCancelled(true);
            }
        }

    }

}
