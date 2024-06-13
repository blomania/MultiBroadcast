package me.korolkotov.multibroadcast.command;

import com.google.inject.Inject;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import me.korolkotov.multibroadcast.util.ChatUtil;

public class MultiBroadcastCMD implements SimpleCommand {
    private final ProxyServer proxy;

    public MultiBroadcastCMD(ProxyServer proxy) {
        this.proxy = proxy;
    }

    @Override
    public void execute(SimpleCommand.Invocation invocation) {
        String[] args = invocation.arguments();

        if (args.length == 0) {
            ChatUtil.sendMessage(invocation.source(), "&cУкажите текст!");
            return;
        }

        for (Player player : proxy.getAllPlayers()) {
            ChatUtil.sendMessage(player, args);
        }
    }
}