package br.com.eterniaserver.modules.economymanager.commands;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.eterniaserver.EterniaServer;
import br.com.eterniaserver.configs.Messages;
import br.com.eterniaserver.modules.economymanager.sql.Queries;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class Baltop implements CommandExecutor {

    private final EterniaServer plugin;

    public Baltop(EterniaServer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (sender.hasPermission("eternia.baltop")) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    final String query = "SELECT * FROM " + EterniaServer.configs.getString("sql.table-money") + " ORDER BY balance DESC LIMIT " + 10 + ";";
                    List<String> accounts = new ArrayList<>();
                    ResultSet rs = null;
                    try {
                        rs = EterniaServer.connection.Query(query);
                        while (rs.next()) {
                            final String string2 = rs.getString("player_name");
                            accounts.add(string2);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        try {
                            rs.close();
                        } catch (SQLException ee) {
                            ee.printStackTrace();
                        }
                    } finally {
                        try {
                            assert rs != null;
                            rs.close();
                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                    }
                    DecimalFormat df2 = new DecimalFormat(".##");
                    Messages.PlayerMessage("eco.baltop", player);
                    accounts.forEach(name -> Messages.PlayerMessage("eco.ballist", (accounts.indexOf(name) + 1), name, df2.format(Queries.getMoney(name)), player));
                });
            } else {
                Messages.PlayerMessage("server.no-perm", player);
            }
        } else {
            Messages.ConsoleMessage("server.only-player");
        }
        return true;
    }

}
