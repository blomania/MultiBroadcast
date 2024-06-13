package me.korolkotov.multibroadcast;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.korolkotov.multibroadcast.command.MultiBroadcastCMD;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Plugin(
        id = "multibroadcast",
        name = "MultiBroadcast",
        version = "1.0",
        description = "Plugin for multi broadcast by https://vk.com/blomania",
        authors = {"BLOCKMANIA", "KoroLKotov"}
)
public class Main {
    private final ProxyServer proxy;
    public static YamlDocument config;

    @Inject
    public Main(ProxyServer proxy, Logger logger, @DataDirectory Path dataDirectory) {
        this.proxy = proxy;

        try {
            config = YamlDocument.create(new File(dataDirectory.toFile(), "messages.yml"),
                    Objects.requireNonNull(getClass().getResourceAsStream("/messages.yml")),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().build());

            config.update();
            config.save();
        } catch (IOException e) {
            logger.severe("Error with loading config!");
            Optional<PluginContainer> container = proxy.getPluginManager().getPlugin("multibroadcast");
            container.ifPresent(pluginContainer -> pluginContainer.getExecutorService().shutdown());
        }

        System.out.println("[MultiBroadcast] Plugin by BLOCKMANIA.");
        System.out.println("[MultiBroadcast] VK: https://vk.com/blomania");
        System.out.println("[MultiBroadcast] MultiBroadcast initialized!");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        var commandManager = proxy.getCommandManager();

        var commandMeta = commandManager.metaBuilder("multibroadcast")
                .plugin(this)
                .build();

        var command = new MultiBroadcastCMD(proxy);

        commandManager.register(commandMeta, command);
    }
}