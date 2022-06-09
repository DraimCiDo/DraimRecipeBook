package net.draimcido.draimrecipebook.Utils;

import net.draimcido.draimrecipebook.Config.MessageConfig;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static net.draimcido.draimrecipebook.Utils.ColorUtils.color;


public class MessageUtils {

    public static void sendMessage(String message, CommandSender p) {
        if (!(p instanceof Player player)) {
            p.sendMessage(ColorUtils.colorMessage(message
                    .replace("chat!", "")
                    .replace("title!", "")
                    .replace("actionbar!", "")
            ));
        } else {
            if (message.startsWith("chat!")) {
                sendChatMessage(message.replace("chat!", ""), player);
            } else if (message.startsWith("title!")) {
                sendTitleMessage(message.replace("title!", ""), player);
            } else if (message.startsWith("actionbar!")) {
                sendActionBarMessage(message.replace("actionbar!", ""), player);
            } else {
                sendChatMessage(message.replace("chat!", ""), player);
            }
        }
    }

    public static void sendUsefulMessage(Player player, String path) {
        String messages = MessageConfig.getMessage().getConfig().getString(path);
        player.sendMessage(color(messages));
    }

    public static String ConfigOperator(String message, Player player, int amount) {
        String message1 = placeholder(message, player, amount);
        String message2 = color(message1);
        return message2;
    }

    public static String placeholder(String message, Player player, int amount) {
        String message1 = message.replace("%player%", player.getName());
        String message2 = message1.replace("%amount%", String.valueOf(amount));
        return message2;
    }

    public static String config(String db, String path, Player player, int amount) {
        if (db.equals("config")) {
            String message = MessageConfig.getMessage().getConfig().getString(path);
            String message1 = ConfigOperator(message, player, amount);
            return message1;
        }
        else return "<no message found, please contact the administration>";
    }

    public static void sendChatMessage(String message, Player player) {
        player.sendMessage(ColorUtils.colorMessage(message));
    }

    public static void sendTitleMessage(String message, Player player) {
        player.sendTitle(ColorUtils.colorMessage(message), "", 20, 80, 20);
    }

    public static void sendActionBarMessage(String message, Player player) {
        BaseComponent component = ComponentSerializer.parse(ColorUtils.colorBungee(message))[0];
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
    }

    public static String toCheckMessage(String message) {
        return ChatColor.stripColor(message).toLowerCase();
    }


}
