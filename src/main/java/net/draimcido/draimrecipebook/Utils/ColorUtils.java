package net.draimcido.draimrecipebook.Utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {

    private static final String RAW_GRADIENT_HEX_REGEX = "<\\\\$#[A-Fa-f0-9]{6}>";

    public static final String HEX_REGEX = "#[A-Fa-f0-9]{6}";

    public static final String TAG_REGEX = "^[A-Za-z0-9_]{1,16}$";

    public static final String RAW_HEX_REGEX = "<#[A-Fa-f0-9]{6}>";

    public static String legacyToJson(String legacyString) {
        if (legacyString == null) return "";
        return ComponentSerializer.toString(TextComponent.fromLegacyText(legacyString));
    }

    public static String jsonToLegacy(String json) {
        if (json == null) return "";
        return BaseComponent.toLegacyText(ComponentSerializer.parse(json));
    }

    public static String colorBungee(String legacyMessage) {
        return legacyToJson(colorMessage(legacyMessage));
    }

    public static String colorMessage(String legacyMessage) {
        try {
            if (legacyMessage.isEmpty()) {
                return legacyMessage;
            }
            if (ChatColor.translateAlternateColorCodes('&', legacyMessage).isEmpty()) {
                return ChatColor.translateAlternateColorCodes('&', legacyMessage);
            }
        } catch (NullPointerException e) {
            return legacyMessage;
        }

        legacyMessage = gradient(legacyMessage);

        // FORMAT CODES
        List<String> formatCodes = Arrays.asList("&k", "&l", "&m", "&n", "&o", "&r");
        for (String code : formatCodes) {
            legacyMessage = legacyMessage.replaceAll(code, ChatColor.getByChar(code.charAt(1)) + "");
        }

        // HEX VALUES
        legacyMessage = hex(legacyMessage);

        return ChatColor.translateAlternateColorCodes('&', legacyMessage);
    }

    private static String hex(String legacyMessage) {
        Matcher matcher = Pattern.compile(RAW_HEX_REGEX).matcher(legacyMessage);
        int hexAmount = 0;
        while (matcher.find()) {
            matcher.region(matcher.end() - 1, legacyMessage.length());
            hexAmount++;
        }
        int startIndex = 0;
        for (int hexIndex = 0; hexIndex < hexAmount; hexIndex++) {
            int messageIndex = legacyMessage.indexOf("<#", startIndex);
            String hex = legacyMessage.substring(messageIndex + 1, messageIndex + 8);
            startIndex = messageIndex + 2;
            legacyMessage = legacyMessage.replace("<" + hex + ">", ChatColor.of(hex) + "");
        }
        return legacyMessage;
    }

    private static String gradient(String legacyMessage) {

        List<String> hexes = new ArrayList<>();
        Matcher matcher = Pattern.compile(RAW_GRADIENT_HEX_REGEX).matcher(legacyMessage);
        while (matcher.find()) {
            hexes.add(matcher.group().replace("<$", "").replace(">", ""));
        }
        int hexIndex = 0;
        List<String> texts = new LinkedList<>(Arrays.asList(legacyMessage.split(RAW_GRADIENT_HEX_REGEX)));
        StringBuilder finalMessage = new StringBuilder();
        for (String text : texts) {
            if (texts.get(0).equalsIgnoreCase(text)) {
                finalMessage.append(text);
                continue;
            }
            if (text.length() == 0) continue;
            if (hexIndex + 1 >= hexes.size()) {
                if (!finalMessage.toString().contains(text)) finalMessage.append(text);
                continue;
            }
            String fromHex = hexes.get(hexIndex);
            String toHex = hexes.get(hexIndex + 1);
            finalMessage.append(insertFades(text, fromHex, toHex, texts.contains("&l"), texts.contains("&o"), texts.contains("&n"), texts.contains("&m"), texts.contains("&k")));
            hexIndex++;
        }
        return finalMessage.toString();
    }

    private static String insertFades(String message, String fromHex, String toHex, boolean bold, boolean italic, boolean underlined, boolean strikethrogh, boolean magic) {
        message = message.replaceAll("&k", "");
        message = message.replaceAll("&l", "");
        message = message.replaceAll("&m", "");
        message = message.replaceAll("&n", "");
        message = message.replaceAll("&o", "");
        message = message.replaceAll("&r", "");
        int length = message.length();
        Color fromRGB = Color.decode(fromHex);
        Color toRGB = Color.decode(toHex);
        double rStep = Math.abs((double) (fromRGB.getRed() - toRGB.getRed()) / length);
        double gStep = Math.abs((double) (fromRGB.getGreen() - toRGB.getGreen()) / length);
        double bStep = Math.abs((double) (fromRGB.getBlue() - toRGB.getBlue()) / length);
        if (fromRGB.getRed() > toRGB.getRed()) rStep = -rStep;
        if (fromRGB.getGreen() > toRGB.getGreen()) gStep = -gStep;
        if (fromRGB.getBlue() > toRGB.getBlue()) bStep = -bStep;
        Color finalColor = new Color(fromRGB.getRGB());
        message = message.replaceAll(RAW_GRADIENT_HEX_REGEX, "");
        message = message.replace("", "<$>");
        for (int index = 0; index <= length; index++) {
            int red = (int) Math.round(finalColor.getRed() + rStep);
            int green = (int) Math.round(finalColor.getGreen() + gStep);
            int blue = (int) Math.round(finalColor.getBlue() + bStep);

            if (red > 255) red = 255; if (red < 0) red = 0;
            if (green > 255) green = 255; if (green < 0) green = 0;
            if (blue > 255) blue = 255; if (blue < 0) blue = 0;

            finalColor = new Color(red, green, blue);
            String hex = "#" + Integer.toHexString(finalColor.getRGB()).substring(2);
            String format = "";

            if (bold) format += ChatColor.BOLD;
            if (italic) format += ChatColor.ITALIC;
            if (underlined) format += ChatColor.UNDERLINE;
            if (strikethrogh) format += ChatColor.STRIKETHROUGH;
            if (magic) format += ChatColor.MAGIC;

            message = message.replaceFirst("<\\$>", "" + ChatColor.of(hex) + format);
        }
        return message;
    }

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    // Реплейс цветов в консоли
    public static String color(String message) {
        return message.replace("&", "§");
    }
}
