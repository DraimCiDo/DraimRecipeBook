package net.draimcido.draimrecipebook.Commands;

import net.draimcido.draimrecipebook.Config.MessageConfig;
import net.draimcido.draimrecipebook.GUIs.MainPage;
import net.draimcido.draimrecipebook.Main;
import net.draimcido.draimrecipebook.Utils.ColorUtils;
import net.draimcido.draimrecipebook.Utils.MessageUtils;
import net.draimcido.draimrecipebook.Utils.SoundUtils;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommands implements CommandExecutor {
    private Main pl;

    public MainCommands(Main pl) {
        super();
        this.pl = pl;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (command.getName().equals("draimrecipebook")) {
            if (args.length == 0) {
                if (sender instanceof Player p) {
                    p.openInventory(new MainPage().getMenu());
                    return true;
                }
                return false;
            } final String lowerCase = args[0].toLowerCase();
            switch (lowerCase) {
                case "reload": {
                    if (sender.hasPermission("draimrecipebook.reload")) {
                        MessageUtils.sendMessage(Main.getConfigString("Messages.Another.Plugin-Reload"), sender);
                        SoundUtils.playSound((Player) sender, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                        break;
                    }
                    MessageUtils.sendMessage(Main.getConfigString("Messages.Another.No-Perm"), sender);
                    SoundUtils.playSound((Player) sender, Sound.BLOCK_ANVIL_PLACE);
                    break;
                }
                default: {
                    sender.sendMessage(MessageUtils.config("config", "Messages.Another.NoArg", (Player) sender, 0));
                    for (final String s2 : MessageConfig.getMessage().getConfig().getStringList("Messages.Help")) {
                        sender.sendMessage(ColorUtils.color(s2));
                    }
                    SoundUtils.playSound((Player) sender, Sound.BLOCK_ANVIL_PLACE);
                    break;
                }
            }
        }
        return false;
    }

}
