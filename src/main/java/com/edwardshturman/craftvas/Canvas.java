package com.edwardshturman.craftvas;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class Canvas implements BasicCommand {

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        if (args.length == 0) {
            source.getSender().sendRichMessage("<red>Need at least one argument");
            return;
        }
    }

    @Override
    public @Nullable String permission() {
        return "craftvas.canvas";
    }
}
