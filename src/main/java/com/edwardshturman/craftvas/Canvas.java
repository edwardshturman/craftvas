package com.edwardshturman.craftvas;

import edu.ksu.canvas.CanvasApiFactory;
import edu.ksu.canvas.interfaces.CourseReader;
import edu.ksu.canvas.model.Course;
import edu.ksu.canvas.oauth.NonRefreshableOauthToken;
import edu.ksu.canvas.oauth.OauthToken;
import edu.ksu.canvas.requestOptions.ListCurrentUserCoursesOptions;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.configuration.file.FileConfiguration;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;

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

        if (args[0].equalsIgnoreCase("info")) {
            FileConfiguration config = plugin.getConfig();
            String canvasBaseUrl = config.getString("canvas-base-url");
            String tokenString = config.getString("token");
            plugin.getLogger().info("Using Canvas API URL: " + canvasBaseUrl);
            OauthToken token = new NonRefreshableOauthToken(tokenString);
            plugin.getLogger().info("Using token: " + token.getAccessToken());
            CanvasApiFactory apiFactory = new CanvasApiFactory(canvasBaseUrl);

            CourseReader courseReader = apiFactory.getReader(CourseReader.class, token);
            try {
                List<Course> courses = courseReader.listCurrentUserCourses(new ListCurrentUserCoursesOptions());
                source.getSender().sendRichMessage("<gold>Craftvas sees courses:");
                for (Course course : courses) {
                    source.getSender().sendRichMessage("<gold>- " + course.getName());
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Failed to load account: " + e.getMessage());
                source.getSender().sendRichMessage("<red>Failed to load account");
            }
        }
    }

    @Override
    public @Nullable String permission() {
        return "craftvas.canvas";
    }
}
