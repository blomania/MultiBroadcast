package me.korolkotov.multibroadcast.util;

import com.velocitypowered.api.command.CommandSource;
import dev.dejvokep.boostedyaml.route.Route;
import me.korolkotov.multibroadcast.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyFormat;
import org.jetbrains.annotations.NotNull;

public class ChatUtil {
    public static void sendMessage(CommandSource source, String text) {
        Component component = LegacyComponentSerializer.legacy('&').deserialize(getTag() + text);
        source.sendMessage(component);
    }

    public static void sendMessage(CommandSource source, String[] text) {
        if (text.length == 0) return;

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : text) {
            String toAdd = s;
            if (!stringBuilder.isEmpty()) {
                toAdd = " " + toAdd;
            }
            stringBuilder.append(toAdd);
        }
        Component component = LegacyComponentSerializer.legacy('&').deserialize(getTag() + stringBuilder);
        source.sendMessage(component);
    }

    public static String getTag() {
        return Main.config.getString(Route.from("tag"));
    }
}

