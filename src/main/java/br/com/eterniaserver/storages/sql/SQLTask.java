package br.com.eterniaserver.storages.sql;

import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.SQLException;

class SQLTask extends BukkitRunnable {

    private final Connections connections;
    private final SQLCallback sqlCallBack;

    SQLTask(Connections Connection, SQLCallback sqlCallBack) {
        this.connections = Connection;
        this.sqlCallBack = sqlCallBack;
    }

    @Override
    public void run() {
        if(connections.isClosed()) {
            return;
        }

        try (Connection connection = connections.getConnection()){
            sqlCallBack.call(connection);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    void executeAsync() {
        runTaskAsynchronously(connections.getPlugin());
    }
}
