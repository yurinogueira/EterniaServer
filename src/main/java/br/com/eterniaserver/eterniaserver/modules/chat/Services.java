package br.com.eterniaserver.eterniaserver.modules.chat;

import br.com.eterniaserver.eternialib.EterniaLib;
import br.com.eterniaserver.eternialib.chat.MessageOptions;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.api.interfaces.ChatAPI;
import br.com.eterniaserver.eterniaserver.enums.Messages;
import br.com.eterniaserver.eterniaserver.enums.Strings;
import br.com.eterniaserver.eterniaserver.modules.Constants;
import br.com.eterniaserver.eterniaserver.modules.core.Entities.PlayerProfile;
import br.com.eterniaserver.eterniaserver.modules.chat.Entities.ChatInfo;
import br.com.eterniaserver.eterniaserver.modules.chat.Utils.ChannelObject;
import br.com.eterniaserver.eterniaserver.modules.chat.Utils.CustomPlaceholder;

import io.papermc.paper.event.player.AsyncChatEvent;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.Title.Times;

import org.bukkit.Instrument;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

final class Services {

    private Services() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    static class CraftChat implements ChatAPI {

        protected static final HoverEvent<Component> DISCORD_SRV_HOVER_EVENT = HoverEvent.showText(Component.text("discordsrv"));

        protected final Map<String, CustomPlaceholder> customPlaceholdersObjectsMap = new HashMap<>();
        protected final Map<Integer, ChannelObject> channelObjectsMap = new HashMap<>();
        protected final List<String> channels = new ArrayList<>();

        protected static final String TELL_CHANNEL_STRING = "tellchannel";

        private final static String NICKNAME_WORD_REGEX = "\\W";
        private final static String CLEAR_COLOR_REGEX = "<#[a-fA-F\\d]{6}>";

        private final Map<UUID, UUID> tellMap = new ConcurrentHashMap<>();
        private final Map<Integer, UUID> playerHashToUUID = new HashMap<>();
        private final Map<String, String> staticTags = new HashMap<>();
        private final Times mentionTimes = Times.times(Duration.ofMillis(100), Duration.ofSeconds(1), Duration.ofMillis(100));
        private final Set<UUID> spySet = new HashSet<>();

        private final EterniaServer plugin;
        private final int tellChannelHashCode;

        private int showItemHashCode;
        private int defaultChannel;
        private int discordSRVChannel;
        private TextReplacementConfig replaceText;
        private boolean muteAllChannels = false;
        private int muteChannelTaskId = 0;

        public CraftChat(EterniaServer plugin) {
            this.plugin = plugin;
            this.tellChannelHashCode = TELL_CHANNEL_STRING.hashCode();
        }

        protected String getPlayerDefaultColor(ChatInfo chatInfo, ChannelObject channelObject) {
            String color = chatInfo.getChatColor();
            if (color == null) {
                return channelObject.channelColor();
            }

            return color;
        }

        protected void addHashToUUID(UUID uuid, String name) {
            playerHashToUUID.put(name.toLowerCase().hashCode(), uuid);
        }

        protected void removeHashToUUID(String name) {
            playerHashToUUID.remove(name.toLowerCase().hashCode());
        }

        private UUID getUUIDFromHash(int hash) {
            return playerHashToUUID.get(hash);
        }

        private int defaultChannel() {
            if (this.defaultChannel != 0) {
                return this.defaultChannel;
            }
            this.defaultChannel = plugin.getString(Strings.DEFAULT_CHANNEL).toLowerCase().hashCode();
            return this.defaultChannel;
        }

        private int getShowItemHashCode() {
            if (showItemHashCode != 0) {
                return showItemHashCode;
            }
            this.showItemHashCode = plugin.getString(Strings.SHOW_ITEM_PLACEHOLDER).toLowerCase().hashCode();
            return showItemHashCode;
        }

        private int discordSRVChannel() {
            if (this.discordSRVChannel != 0) {
                return this.discordSRVChannel;
            }
            this.discordSRVChannel = plugin.getString(Strings.CHAT_DISCORD_SRV_CHANNEL).toLowerCase().hashCode();
            return this.discordSRVChannel;
        }

        private int tellChannel() {
            return tellChannelHashCode;
        }

        protected boolean handleChannel(AsyncChatEvent event) {
            Player player = event.getPlayer();
            Component component = event.message();

            if (!player.hasPermission(plugin.getString(Strings.PERM_CHAT_BYPASS_PROTECTION))) {
                component = component.replaceText(getFilter());
            }

            ChatInfo chatInfo = EterniaLib.getDatabase().get(ChatInfo.class, player.getUniqueId());
            Integer channel = chatInfo.getDefaultChannel();
            if (channel == null) {
                channel = defaultChannel();
            }

            boolean isTellChannel = channel == tellChannel();
            boolean isDiscordSRVChannel = channel == discordSRVChannel();

            if (muteAllChannels && !isTellChannel) {
                EterniaLib.getChatCommons().sendMessage(player, Messages.CHAT_CHANNELS_MUTED);
                return true;
            }

            UUID uuid = player.getUniqueId();

            if (isMuted(chatInfo)) {
                MessageOptions options = new MessageOptions(String.valueOf(secondsMutedLeft(uuid)));
                EterniaLib.getChatCommons().sendMessage(player, Messages.CHAT_ARE_MUTED, options);
                return true;
            }

            PlayerProfile playerProfile = EterniaLib.getDatabase().get(PlayerProfile.class, uuid);

            if (isDiscordSRVChannel) {
                event.message(component.hoverEvent(DISCORD_SRV_HOVER_EVENT));
            }

            if (isTellChannel) {
                UUID targetUUID = getTellLink(uuid);
                if (targetUUID == null) {
                    removeTellLink(uuid);
                    EterniaLib.getChatCommons().sendMessage(player, Messages.CHAT_TELL_NO_PLAYER);
                    return true;
                }

                Player target = plugin.getServer().getPlayer(targetUUID);
                if (target == null || !target.isOnline()) {
                    removeTellLink(uuid);
                    EterniaLib.getChatCommons().sendMessage(player, Messages.CHAT_TELL_NO_PLAYER);
                    return true;
                }

                sendPrivateMessage(event, component, playerProfile, target);
                return false;
            }

            filter(event, playerProfile, chatInfo, component);
            return false;
        }

        protected void sendPrivateMessage(AsyncChatEvent event,
                                          Component component,
                                          PlayerProfile playerProfile,
                                          Player target) {

            Player player = event.getPlayer();
            Set<Audience> viewers = event.viewers();

            UUID playerUUID = player.getUniqueId();
            UUID targetUUID = target.getUniqueId();

            EterniaServer.getChatAPI().setTellLink(playerUUID, targetUUID);

            PlayerProfile targetProfile = EterniaLib.getDatabase().get(PlayerProfile.class, targetUUID);

            viewers.clear();
            viewers.add(player);
            viewers.add(target);

            String msg = EterniaLib.getChatCommons().plain(component);
            msg = msg.replaceAll(CLEAR_COLOR_REGEX, "");

            MessageOptions options = new MessageOptions(
                    msg,
                    playerProfile.getPlayerName(),
                    playerProfile.getPlayerDisplay(),
                    targetProfile.getPlayerName(),
                    targetProfile.getPlayerDisplay()
            );
            Component spyMsgComponent = EterniaLib.getChatCommons().parseMessage(Messages.CHAT_SPY_TELL, options);

            String spyPerm = plugin.getString(Strings.PERM_SPY);
            for (Player other : plugin.getServer().getOnlinePlayers()) {
                boolean inTell = other.getUniqueId().equals(playerUUID) || other.getUniqueId().equals(targetUUID);
                if (!inTell && (other.hasPermission(spyPerm) || isSpying(other.getUniqueId()))) {
                    other.sendMessage(spyMsgComponent);
                }
            }

            Component msgComponent = EterniaLib.getChatCommons().parseMessage(Messages.CHAT_TELL, options);

            event.renderer((source, sourceDisplayName, message, viewer) -> {
                Optional<UUID> viewerUUID = viewer.get(Identity.UUID);
                if (viewerUUID.isEmpty()) {
                    return message;
                }

                return msgComponent;
            });
        }

        private void filter(AsyncChatEvent event, PlayerProfile playerProfile, ChatInfo chatInfo, Component component) {
            Player player = event.getPlayer();

            ChannelObject channelObject = channelObjectsMap.get(chatInfo.getDefaultChannel());
            if (channelObject == null) {
                channelObject = channelObjectsMap.get(defaultChannel());
            }

            if (!player.hasPermission(channelObject.perm())) {
                EterniaLib.getChatCommons().sendMessage(player, Messages.SERVER_NO_PERM);
                return;
            }

            StringBuilder messageBuilder = new StringBuilder();
            getChatFormat(messageBuilder, player, channelObject.format());

            String messageStr = EterniaLib.getChatCommons().plain(component);
            if (player.hasPermission(plugin.getString(Strings.PERM_CHAT_COLOR))) {
                messageStr = EterniaLib.getChatCommons().getColor(messageStr);
            }

            messageBuilder.append(EterniaLib.getChatCommons().getColor(getPlayerDefaultColor(chatInfo, channelObject)));
            for (String section : messageStr.split(" ")) {
                messageBuilder.append(" ");
                messageBuilder.append(getSection(section, player, playerProfile));
            }

            Component messageComponent = EterniaLib.getChatCommons().deserialize(messageBuilder.toString());

            Set<Audience> viewers = event.viewers();

            World world = player.getWorld();
            Location location = player.getLocation();
            String spyPerm = plugin.getString(Strings.PERM_SPY);

            for (Player receiver : plugin.getServer().getOnlinePlayers()) {
                boolean hasPermission = receiver.hasPermission(channelObject.perm());
                if (!channelObject.hasRange() && !hasPermission) {
                    viewers.remove(receiver);
                }
                else if (hasPermission) {
                    int range = channelObject.range();
                    boolean isInRange = range <= 0 || (
                            world.equals(receiver.getWorld())
                                    &&
                            receiver.getLocation().distanceSquared(location) <= Math.pow(range, 2)
                    );

                    if (!isInRange) {
                        viewers.remove(receiver);
                    }
                    if (!isInRange && receiver.hasPermission(spyPerm) && isSpying(receiver.getUniqueId())) {
                        MessageOptions options = new MessageOptions(
                                messageStr,
                                playerProfile.getPlayerName(),
                                playerProfile.getPlayerDisplay()
                        );
                        EterniaLib.getChatCommons().sendMessage(receiver, Messages.CHAT_SPY_LOCAL, options);
                    }
                }
            }

            Component sourceMessage = messageComponent.compact();

            event.renderer((source, sourceDisplayName, message, viewer) -> {
                Optional<UUID> viewerUUID = viewer.get(Identity.UUID);
                if (viewerUUID.isEmpty()) {
                    return message;
                }

                return sourceMessage;
            });

            if (viewers.isEmpty()) {
                EterniaLib.getChatCommons().sendMessage(player, Messages.CHAT_NO_ONE_NEAR);
            }
        }

        private String getSection(String section, Player player, PlayerProfile playerProfile) {
            int sectionHashCode = section.toLowerCase().hashCode();
            UUID mentionPlayerUUID = getUUIDFromHash(sectionHashCode);

            if (mentionPlayerUUID != null && player.hasPermission(plugin.getString(Strings.PERM_CHAT_MENTION))) {
                Player mentionPlayer = plugin.getServer().getPlayer(mentionPlayerUUID);
                if (mentionPlayer != null) {
                    Title title = Title.title(
                            EterniaLib.getChatCommons().parseColor(plugin.getString(Strings.CONS_MENTION_TITLE).replace("{0}", playerProfile.getPlayerName()).replace("{1}", playerProfile.getPlayerDisplay())),
                            EterniaLib.getChatCommons().parseColor(plugin.getString(Strings.CONS_MENTION_SUBTITLE).replace("{0}", playerProfile.getPlayerName()).replace("{1}", playerProfile.getPlayerDisplay())),
                            mentionTimes
                    );
                    mentionPlayer.playNote(mentionPlayer.getLocation(), Instrument.PIANO, Note.natural(1, Note.Tone.F));
                    mentionPlayer.showTitle(title);
                }
                return "<color:%s>%s</color>".formatted(plugin.getString(Strings.CHAT_DEFAULT_TAG_COLOR), section);
            }

            if (player.hasPermission(plugin.getString(Strings.PERM_CHAT_ITEM)) && sectionHashCode == getShowItemHashCode()) {
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                if (!itemStack.getType().equals(Material.AIR)) {
                    Component itemComponent = Component.text(section.replace(
                            plugin.getString(Strings.SHOW_ITEM_PLACEHOLDER),
                            plugin.getString(Strings.CONS_SHOW_ITEM)
                                    .replace("{0}", String.valueOf(itemStack.getAmount()))
                                    .replace("{1}", itemStack.getType().toString())
                    ));
                    return "<color:%s>%s</color>".formatted(
                            plugin.getString(Strings.CHAT_DEFAULT_TAG_COLOR),
                            EterniaLib.getChatCommons().serializer(itemComponent.hoverEvent(
                                    plugin.getServer().getItemFactory().asHoverEvent(itemStack, UnaryOperator.identity())
                            ))
                    );
                }
            }

            return section;
        }

        private void getChatFormat(StringBuilder message, Player player, String format) {
            Map<Integer, String> sections = new TreeMap<>();
            for (Map.Entry<String, CustomPlaceholder> entry : customPlaceholdersObjectsMap.entrySet()) {
                if (format.contains("{" + entry.getKey() + "}") && player.hasPermission(entry.getValue().permission())) {
                    sections.put(entry.getValue().priority(), getStringTag(player, entry.getValue()));
                }
            }

            for (String section : sections.values()) {
                message.append(section);
            }
        }

        private String getStringTag(Player player, CustomPlaceholder object) {
            if (!object.isStatic()) {
                return loadTag(player, object);
            }

            String value = object.value();
            if (!staticTags.containsKey(value)) {
                staticTags.put(value, loadTag(player, object));
            }

            return staticTags.get(value);
        }

        private String loadTag(Player player, CustomPlaceholder object) {
            String tag = object.value().equals("%player_displayname%") ?
                    EterniaLib.getChatCommons().serializer(player.displayName()) :
                    EterniaLib.getChatCommons().getColor(plugin.setPlaceholders(player, object.value()));

            if (!object.hoverText().isEmpty()) {
                tag = "<hover:show_text:'%s'>%s</hover>".formatted(EterniaLib.getChatCommons().getColor(plugin.setPlaceholders(player, object.hoverText())), tag);
            }
            if (!object.suggestCmd().isEmpty()) {
                tag = "<click:suggest_command:'%s'>%s</click>".formatted(plugin.setPlaceholders(player, object.suggestCmd()), tag);
            }

            return tag;
        }

        protected void clearPlayerName(Player player) {
            PlayerProfile playerProfile = EterniaLib.getDatabase().get(PlayerProfile.class, player.getUniqueId());

            playerProfile.setPlayerDisplay(player.getName());

            player.displayName(Component.text(player.getName()));

            EterniaLib.getDatabase().update(PlayerProfile.class, playerProfile);
        }

        protected String setPlayerDisplay(Player player, String nickname) {
            PlayerProfile playerProfile = EterniaLib.getDatabase().get(PlayerProfile.class, player.getUniqueId());

            nickname = nickname.replaceAll(NICKNAME_WORD_REGEX, "");

            Component nicknameComponent = EterniaLib.getChatCommons().parseColor(nickname);

            playerProfile.setPlayerDisplay(nickname);

            player.displayName(nicknameComponent);

            EterniaLib.getDatabase().update(PlayerProfile.class, playerProfile);

            return nickname;
        }

        @Override
        public TextReplacementConfig getFilter() {
            return replaceText;
        }

        @Override
        public void setFilter(Pattern filter) {
            this.replaceText = TextReplacementConfig.builder().match(filter).replacement("").build();
        }

        @Override
        public void setTellLink(UUID sender, UUID target) {
            tellMap.put(target, sender);
        }

        @Override
        public void removeTellLink(UUID uuid) {
            tellMap.remove(uuid);
        }

        @Override
        public UUID getTellLink(UUID uuid) {
            return tellMap.get(uuid);
        }

        @Override
        public void tempMuteAllChannels(long time) {
            this.muteChannelTaskId = plugin.getServer().getScheduler().scheduleSyncDelayedTask(
                    plugin, () -> this.muteAllChannels = false, time
            );
            this.muteAllChannels = true;
        }

        @Override
        public void muteAllChannels() {
            this.muteAllChannels = true;
        }

        @Override
        public void unMuteAllChannels() {
            this.muteAllChannels = false;
            if (muteChannelTaskId != 0) {
                plugin.getServer().getScheduler().cancelTask(muteChannelTaskId);
                muteChannelTaskId = 0;
            }
        }

        @Override
        public boolean isChannelsMute() {
            return muteAllChannels;
        }

        @Override
        public boolean isMuted(UUID uuid) {
            ChatInfo chatInfo = EterniaLib.getDatabase().get(ChatInfo.class, uuid);
            return isMuted(chatInfo);
        }

        private boolean isMuted(ChatInfo chatInfo) {
            Timestamp mutedUntil = chatInfo.getMutedUntil();
            if (mutedUntil == null) {
                return false;
            }

            return mutedUntil.after(new Timestamp(System.currentTimeMillis()));
        }

        @Override
        public int secondsMutedLeft(UUID uuid) {
            ChatInfo chatInfo = EterniaLib.getDatabase().get(ChatInfo.class, uuid);
            return secondsMutedLeft(chatInfo);
        }

        private int secondsMutedLeft(ChatInfo chatInfo) {
            Timestamp mutedUntil = chatInfo.getMutedUntil();
            if (mutedUntil == null) {
                return 0;
            }

            return (int) TimeUnit.MILLISECONDS.toSeconds(mutedUntil.getTime() - System.currentTimeMillis());
        }

        @Override
        public void mute(UUID uuid, long time) {
            ChatInfo chatInfo = EterniaLib.getDatabase().get(ChatInfo.class, uuid);

            chatInfo.setMutedUntil(new Timestamp(time));

            EterniaLib.getDatabase().update(ChatInfo.class, chatInfo);
        }

        @Override
        public boolean isSpying(UUID uuid) {
            return spySet.contains(uuid);
        }

        @Override
        public void setSpying(UUID uuid) {
            spySet.add(uuid);
        }

        @Override
        public void removeSpying(UUID uuid) {
            spySet.remove(uuid);
        }
    }

}
