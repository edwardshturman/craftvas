package com.edwardshturman.craftvas;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.bukkit.plugin.java.JavaPlugin;

@NullMarked
public class Canvas implements BasicCommand {
    private final JavaPlugin plugin;

    public Canvas(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        if (args.length == 0) {
            source.getSender().sendRichMessage("<red>Need at least one argument");
            return;
        }

        if (args[0].equalsIgnoreCase("url")) {
            if (args[1].isEmpty()) {
                source.getSender().sendRichMessage("<red>Need another argument to set the base URL");
                return;
            }

            plugin.getConfig().set("canvas-base-url", args[1]);
            plugin.saveConfig();
            source.getSender().sendRichMessage("<gold>Set the base URL");
            return;
        }

        if (args[0].equalsIgnoreCase("token")) {
            if (args[1].isEmpty()) {
                source.getSender().sendRichMessage("<red>Need another argument to set the token");
                return;
            }

            plugin.getConfig().set("token", args[1]);
            plugin.saveConfig();
            source.getSender().sendRichMessage("<gold>Set the token");
            return;
        }
    }

    @Override
    public @Nullable String permission() {
        return "craftvas.canvas";
    }
}
