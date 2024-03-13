package br.com.eterniaserver.eterniaserver.modules.placeholderapi;

import br.com.eterniaserver.eternialib.EterniaLib;
import br.com.eterniaserver.eterniaserver.EterniaServer;
import br.com.eterniaserver.eterniaserver.enums.Strings;
import br.com.eterniaserver.eterniaserver.modules.Constants;
import br.com.eterniaserver.eterniaserver.modules.core.Entities.PlayerProfile;
import br.com.eterniaserver.eterniaserver.modules.cash.Entities.CashBalance;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.OfflinePlayer;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;


final class Services {

    private Services() {
        throw new IllegalStateException(Constants.UTILITY_CLASS);
    }

    static class Placeholders {

        private final EterniaServer plugin;
        private final String version;

        private final DateFormat dateFormat;

        public Placeholders(EterniaServer plugin) {
            this.plugin = plugin;
            this.version = "1.0.0";
            this.dateFormat = new SimpleDateFormat(plugin.getString(Strings.DATA_FORMAT));
        }

        public void register() {
            if (plugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
                new Placeholders.PlaceHoldersEnabled().register();
            }
            else {
                plugin.getLogger().log(Level.WARNING, "PlaceHolderAPI not found");
            }
        }

        private class PlaceHoldersEnabled extends PlaceholderExpansion {

            @Override
            public boolean persist(){
                return true;
            }

            @Override
            public boolean canRegister() {
                return true;
            }

            @Override
            public @NotNull String getAuthor() {
                return "yurinogueira";
            }

            @Override
            public @NotNull String getIdentifier() {
                return "eterniaserver";
            }

            @Override
            public @NotNull String getVersion() {
                return version;
            }

            @Override
            public String onRequest(OfflinePlayer offlinePlayer, @NotNull String identifier) {
                if (offlinePlayer == null) {
                    return "";
                }

                return switch (identifier.hashCode()) {
                    case -690213213 -> getFirstJoin(offlinePlayer.getUniqueId());       // register
                    case -500526734 -> getBalanceTop(offlinePlayer.getUniqueId());      // balance_top
                    case 96486 -> getAfk(offlinePlayer.getUniqueId());                  // afk
                    case 3046195 -> getCash(offlinePlayer.getUniqueId());               // cash
                    case 3175821 -> getGlow(offlinePlayer.getUniqueId());               // glow
                    case 30148772 -> getBalanceTop(1);                          // balance_top_1
                    case 30148773 -> getBalanceTop(2);                          // balance_top_2
                    case 30148774 -> getBalanceTop(3);                          // balance_top_3
                    case 197143583 -> getGodMode(offlinePlayer.getUniqueId());          // godmode
                    case 308210020 -> getDisplay(offlinePlayer.getUniqueId());          // player_display
                    default -> plugin.getString(Strings.INVALID_PLACEHOLDER);
                };
            }

            private String getFirstJoin(UUID uuid) {
                PlayerProfile playerProfile = EterniaLib.getDatabase().get(PlayerProfile.class, uuid);
                if (playerProfile.getFirstJoin() == null) {
                    return "";
                }

                return dateFormat.format(new Date(playerProfile.getFirstJoin().getTime()));
            }

            private String getBalanceTop(UUID uuid) {
                if (EterniaServer.getExtraEconomyAPI().isBalanceTop(uuid)) {
                    return plugin.getString(Strings.BALANCE_TOP_TAG);
                }

                return "";
            }

            private String getAfk(UUID uuid) {
                if (EterniaLib.getDatabase().get(PlayerProfile.class, uuid).isAfk()) {
                    return plugin.getString(Strings.AFK_PLACEHOLDER);
                }

                return "";
            }

            private String getCash(UUID uuid) {
                CashBalance cashBalance = EterniaLib.getDatabase().get(CashBalance.class, uuid);
                if (cashBalance.getBalance() == null) {
                    return "";
                }

                return String.valueOf(cashBalance.getBalance());
            }

            private String getGlow(UUID uuid) {
                PlayerProfile playerProfile = EterniaLib.getDatabase().get(PlayerProfile.class, uuid);
                if (playerProfile.getColor() == null) {
                    return "";
                }

                return playerProfile.getColor();
            }

            private String getBalanceTop(int position) {
                return EterniaServer.getExtraEconomyAPI().getBalanceTop(position);
            }

            private String getGodMode(UUID uuid) {
                if (EterniaLib.getDatabase().get(PlayerProfile.class, uuid).isGod()) {
                    return plugin.getString(Strings.GOD_PLACEHOLDER);
                }

                return "";
            }

            private String getDisplay(UUID uuid) {
                PlayerProfile playerProfile = EterniaLib.getDatabase().get(PlayerProfile.class, uuid);
                if (playerProfile.getPlayerDisplay() == null) {
                    return "";
                }

                return playerProfile.getPlayerDisplay();
            }
        }
    }
}
