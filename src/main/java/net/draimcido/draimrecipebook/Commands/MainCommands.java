package net.draimcido.draimrecipebook.Commands;

import net.draimcido.draimrecipebook.Config.MessageConfig;
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
            final Player p = (Player)sender;
            if (args.length == 0) {
                for (final String s : MessageConfig.getMessage().getConfig().getStringList("Messages.Help")) {
                    p.sendMessage(ColorUtils.color(s));
                }
                return false;
            } final String lowerCase = args[0].toLowerCase();
            switch (lowerCase) {
                case "reload": {
                    if (p.hasPermission("draimrecipebook.reload")) {
                        MessageUtils.sendMessage(Main.getConfigString("Messages.Another.Plugin-Reload"), sender);
                        SoundUtils.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                        break;
                    }
                    MessageUtils.sendMessage(Main.getConfigString("Messages.Another.No-Perm"), sender);
                    SoundUtils.playSound(p, Sound.BLOCK_ANVIL_PLACE);
                    break;
                }
                default: {
                    p.sendMessage(MessageUtils.config("config", "Messages.Another.NoArg", p, 0));
                    for (final String s2 : MessageConfig.getMessage().getConfig().getStringList("Messages.Help")) {
                        p.sendMessage(ColorUtils.color(s2));
                    }
                    SoundUtils.playSound(p, Sound.BLOCK_ANVIL_PLACE);
                    break;
                }
            }
        }
        return false;
    }

}
