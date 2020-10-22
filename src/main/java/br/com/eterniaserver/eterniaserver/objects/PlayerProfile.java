package br.com.eterniaserver.eterniaserver.objects;

import br.com.eterniaserver.eterniaserver.EterniaServer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayerProfile {

    private String playerName;
    private String playerDisplayName;

    private List<String> homes;

    private final long firstLogin;
    private long lastLogin;
    private long hours;

    private double balance = 0.0;
    private int cash = 0;
    private int xp = 0;

    private long onPvP;

    private int chatChannel = 0;
    private boolean nickRequest = false;
    private String tempNick;
    private long muted = System.currentTimeMillis();

    public PlayerProfile(String playerName, long firstLogin, long lastLogin, long hours) {
        this.playerName = playerName;
        this.firstLogin = firstLogin;
        this.lastLogin = lastLogin;
        this.hours = hours;
    }

    public long updateTimePlayed() {
        this.hours = hours + (System.currentTimeMillis() - lastLogin);
        this.lastLogin = System.currentTimeMillis();
        return hours;
    }

    public boolean isOnPvP() {
        if (onPvP == 0) return false;
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - onPvP) < EterniaServer.configs.pvpTime;
    }

    public void setIsOnPvP() {
        this.onPvP = System.currentTimeMillis();
    }

    public List<String> getHomes() {
        if (homes == null) {
            homes = new ArrayList<>();
        }
        return homes;
    }

    public int getOnPvP() {
        if (onPvP == 0) return 0;
        return (int) TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - onPvP);
    }

    public String getPlayerDisplayName() {
        return playerDisplayName != null ? playerDisplayName : playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isNickRequest() {
        return nickRequest;
    }

    public double getBalance() {
        return balance;
    }

    public int getCash() {
        return cash;
    }

    public int getChatChannel() {
        return chatChannel;
    }

    public int getXp() {
        return xp;
    }

    public long getFirstLogin() {
        return firstLogin;
    }

    public long getHours() {
        return hours;
    }

    public long getMuted() {
        return muted;
    }

    public String getTempNick() {
        return tempNick;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public void setChatChannel(int chatChannel) {
        this.chatChannel = chatChannel;
    }

    public void setHours(long hours) {
        this.hours = hours;
    }

    public void setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setMuted(long muted) {
        this.muted = muted;
    }

    public void setNickRequest(boolean nickRequest) {
        this.nickRequest = nickRequest;
    }

    public void setPlayerDisplayName(String playerDisplayName) {
        this.playerDisplayName = playerDisplayName;
    }

    public void setTempNick(String tempNick) {
        this.tempNick = tempNick;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

}
