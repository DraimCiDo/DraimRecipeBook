package net.draimcido.draimrecipebook.GUIs;

import net.draimcido.draimrecipebook.Main;
import net.draimcido.draimrecipebook.Utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class MainPage {

    public static final String name = "Main";

    public Inventory getMenu() {
        String guiName = ColorUtils.colorMessage(Main.getConfigString("Menus.Main.Title"));
        int guiSize = Main.getConfigInt("Menus.Main.Size");
        Inventory mainMenu = Bukkit.createInventory(null, guiSize, guiName);

        for (String button : Main.getConfigKeys("Menus.Main.Buttons", false)) {
            List<Integer> intSlots = Main.getConfigIntList("Menus.Main.Buttons."+button+".slots");
            for (int i : intSlots) {
                mainMenu.setItem(i, getItem(button));
            }
        }
        return  mainMenu;
    }

    public ItemStack getItem(String name) {
        ItemStack button;
        String materialString = Main.getConfigString("Menus.Main.Buttons."+name+".material");
        String itemName = ColorUtils.colorMessage(Main.getConfigString("Menus.Main.Buttons."+name+".name"));
        List<String> itemLore = new ArrayList<>();
        String type = Main.getConfigString("Menus.Main.Buttons."+name+".type").toUpperCase();
        for (String line : Main.getConfigStringList("Menus.Main.Buttons."+name+".lore")) {
            itemLore.add(ColorUtils.colorMessage(line));
        }
        Material itemMaterial = Material.getMaterial(materialString);
        button = new ItemStack(itemMaterial, 1);

        ItemMeta meta = button.getItemMeta();
        meta.setDisplayName(itemName);
        meta.setLore(itemLore);
        meta.getPersistentDataContainer().set(Main.buttonKey, PersistentDataType.STRING, type);
        button.setItemMeta(meta);

        return button;
    }
}
