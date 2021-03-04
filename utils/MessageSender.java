package me.illtamer.infinitebot.utils;

import me.illtamer.infinitebot.InfiniteBot;
import me.illtamer.infinitebot.event.MessageReceiveEvent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * 用于输出后台内返回信息
 * @author IllTamer
 */
public class MessageSender implements ConsoleCommandSender
{
    private final Server server;
    private final MessageReceiveEvent event;
    private final List<String> messages = new ArrayList<>();
    private static int id;


    public MessageSender(Server server, MessageReceiveEvent event)
    {
        this.server = server;
        this.event = event;
    }


    private void sendLog()
    {
        Bukkit.getScheduler().cancelTask(id);
        id = Bukkit.getScheduler().runTaskLaterAsynchronously(InfiniteBot.getInstance(), () ->
        {
            StringBuilder output = new StringBuilder();
            for (String s : this.messages)
            {
                output.append(s.replaceAll("§\\S", "")).append("\n");
            }
            event.sendMessage(output.toString());
            this.messages.clear();
        }, 4).getTaskId();
    }


    @Override
    public void sendMessage(@NotNull String string)
    {
        this.messages.add(string);
        sendLog();
    }

    @Override
    public void sendMessage(@NotNull String[] strings)
    {
        for (String message : strings)
        {
            sendMessage(message);
            break;
        }
    }

    @Override
    public void sendMessage(@Nullable UUID uuid, @NotNull String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendMessage(@Nullable UUID uuid, @NotNull String[] strings) {
        throw new UnsupportedOperationException();
    }

    private Optional<ConsoleCommandSender> get() {
        return Optional.of(this.server.getConsoleSender());
    }

    @Override
    public boolean isOp() {
        return true;
    }

    @Override
    public void setOp(boolean b) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public Spigot spigot() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public Server getServer() {
        return server;
    }

    @NotNull
    @Override
    public String getName() {
        return "CONSOLE";
    }

    @Override
    public void acceptConversationInput(@NotNull String s) {

    }

    @Override
    public boolean beginConversation(@NotNull Conversation conversation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendRawMessage(@NotNull String s) {

    }

    @Override
    public void sendRawMessage(@Nullable UUID uuid, @NotNull String s) {

    }

    @Override
    public void recalculatePermissions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isConversing() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAttachment(@NotNull PermissionAttachment permissionAttachment) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void abandonConversation(@NotNull Conversation conversation, @NotNull ConversationAbandonedEvent conversationAbandonedEvent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isPermissionSet(@NotNull String s) {
        return get().map(c -> c.isPermissionSet(s)).orElse(true);
    }

    @Override
    public boolean isPermissionSet(@NotNull Permission permission) {
        return get().map(c -> c.isPermissionSet(permission)).orElse(true);
    }

    @Override
    public boolean hasPermission(@NotNull String s) {
        return get().map(c -> c.hasPermission(s)).orElse(true);
    }

    @Override
    public boolean hasPermission(@NotNull Permission permission) {
        return get().map(c -> c.hasPermission(permission)).orElse(true);
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String s, boolean b) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String s, boolean b, int i) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull Plugin plugin, int i) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        throw new UnsupportedOperationException();
    }
}
