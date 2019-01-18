package support.kajstech.kajbot.command.commands;


import net.dv8tion.jda.api.EmbedBuilder;
import support.kajstech.kajbot.Language;
import support.kajstech.kajbot.command.Command;
import support.kajstech.kajbot.command.CommandEvent;
import support.kajstech.kajbot.handlers.ConfigHandler;
import support.kajstech.kajbot.handlers.KeywordHandler;

import java.awt.*;
import java.time.ZonedDateTime;


public class CustomKeywords extends Command {

    public CustomKeywords() {
        this.name = "keyword";
        this.guildOnly = false;
        this.requiredRole = ConfigHandler.getProperty("Bot admin role");
    }

    @Override
    public void execute(CommandEvent e) {

        switch (e.getArgsSplit().get(0)) {
            case "list":
                try {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(new Color(0xA6C055));
                    eb.setTimestamp(ZonedDateTime.now());
                    KeywordHandler.getKeywords().forEach((k, v) -> eb.addField(String.valueOf(k), String.valueOf(v), true));
                    e.reply(eb.build());
                } catch (Exception ignored) {
                    return;
                }

                break;

            case "del":
            case "remove":
                try {
                    if (KeywordHandler.kws.containsKey(e.getArgsSplit().get(1))) {
                        KeywordHandler.removeKeyword(e.getArgsSplit().get(1));
                        e.reply((Language.getMessage("Keyword.UNREGISTERED")).replace("%KW%", e.getArgsSplit().get(1).toUpperCase()));
                    }
                } catch (Exception ignored) {
                    return;
                }
                break;
            case "add":
                try {
                    String kwName = e.getArgsSplit().get(1);
                    String[] kwContext = e.getArgs().substring(kwName.length() + "add ".length() + 1).split("\\s+");
                    KeywordHandler.addKeyword(kwName, String.join(" ", kwContext));
                    e.reply((Language.getMessage("Keyword.REGISTERED")).replace("%KW%", kwName.toUpperCase()));
                } catch (Exception ignored) {
                    return;
                }
                break;
        }

    }


}
