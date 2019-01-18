package support.kajstech.kajbot.command.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.command.CommandManager;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.handlers.CustomCommandsHandler;

import java.awt.*;
import java.time.ZonedDateTime;


public class CustomCommands extends Command {

    public CustomCommands() {
        this.name = "command";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot admin role");
    }

    @Override
    public void execute(CommandEvent e) {
        if (e.getArgs().length() < 1) return;

        switch (e.getArgsSplit().get(0)) {
            default:
            case "list":
                try {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(new Color(0xA6C055));
                    eb.setTimestamp(ZonedDateTime.now());
                    CustomCommandsHandler.getCommands().forEach((k, v) -> eb.addField(String.valueOf(k), String.valueOf(v), true));
                    e.reply(eb.build());
                } catch (Exception ignored) {
                    return;
                }

                break;
            case "del":
            case "remove":
                if (CommandManager.commands.containsKey(e.getArgsSplit().get(1)) || !CustomCommandsHandler.getCommands().contains(e.getArgsSplit().get(1)))
                    return;
                CustomCommandsHandler.removeCommand(e.getArgsSplit().get(1).replace(ConfigHandler.getProperty("Command prefix"), ""));
                e.reply((Language.getMessage("Command.UNREGISTERED")).replace("%CMD%", e.getArgsSplit().get(1).toUpperCase()));
                break;
            case "add":
                String cmdName = e.getArgsSplit().get(1).replace(ConfigHandler.getProperty("Command prefix"), "");
                if (CommandManager.commands.containsKey(cmdName) || !CustomCommandsHandler.getCommands().contains(cmdName))
                    return;
                String[] cmdContext = e.getArgs().substring(cmdName.length() + "add ".length() + 1).split("\\s+");
                CustomCommandsHandler.addCommand(cmdName, String.join(" ", cmdContext));
                e.reply((Language.getMessage("Command.REGISTERED")).replace("%CMD%", cmdName.toUpperCase()));
                break;
        }

    }


}
