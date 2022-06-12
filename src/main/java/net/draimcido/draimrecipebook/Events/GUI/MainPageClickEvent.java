package net.draimcido.draimrecipebook.Events.GUI;

import net.draimcido.draimrecipebook.GUIs.MainPage;
import net.draimcido.draimrecipebook.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;

public class MainPageClickEvent implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }

        Player p = (Player) event.getWhoClicked();
        if (!p.getPersistentDataContainer().has(Main.mainPage, PersistentDataType.STRING)) {
            return;
        } else if (!p.getPersistentDataContainer().get(Main.mainPage, PersistentDataType.STRING).equalsIgnoreCase(MainPage.name)) {
            return;
        }

        event.setCancelled(true);

        if (event.getClickedInventory().getHolder() != null) {
            if (event.getClickedInventory().getHolder().equals(event.getWhoClicked())) return;
        }

        switch (event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Main.buttonKey, PersistentDataType.STRING)) {
            case ("EXIT"):
                p.closeInventory();
                break;
            default:
                break;
        }
    }
}
