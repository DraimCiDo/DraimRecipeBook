package net.draimcido.draimrecipebook.Events;

import me.filoghost.chestcommands.api.ChestCommandsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class HotbarEvent implements Listener {

    @EventHandler
    public void InventoryClick (InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getView().getBottomInventory().getType() == InventoryType.PLAYER) {
            if (event.getSlot() == 8) {
                    event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void InventoryOpenGUI (InventoryClickEvent event) {
        if (event.getSlot() == 8) {
            Player p = Bukkit.getPlayer("DanichMan");
            ChestCommandsAPI.openInternalMenu(p, "mir.yml");
        }
    }
}
