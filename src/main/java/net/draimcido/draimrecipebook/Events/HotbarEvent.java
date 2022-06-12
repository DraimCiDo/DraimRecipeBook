package net.draimcido.draimrecipebook.Events;

import me.filoghost.chestcommands.api.ChestCommandsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class HotbarEvent implements Listener {

    @EventHandler
    public void HotbarEvent (PlayerItemHeldEvent event) {
        Player p = (Player) event.getPlayer(); // Проверка на игрока
        if (event.getNewSlot() == 8) { // Проверка слота
            ChestCommandsAPI.openInternalMenu(p, "mir.yml"); // Открытие меню "mir.yml" через CC-API
            event.isCancelled(); // Отмена последующих приминений(Чтобы меню открывалось онли на 9 слот хотбара)
        }
    }



    @EventHandler
    public void InvClick (InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getView().getBottomInventory().getType() == InventoryType.PLAYER) {
            if (event.getSlot() == 8) {
                event.setCancelled(true);
            }
        }
    }
}
