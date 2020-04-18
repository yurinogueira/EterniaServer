package br.com.eterniaserver.storages.sql;

import br.com.eterniaserver.EterniaServer;

import java.sql.PreparedStatement;

public class Table {

    public Table() {
        final String xp = "CREATE TABLE IF NOT EXISTS " + EterniaServer.configs.getString("sql.table-xp") + " (player_name varchar(32), xp int(11));";
        EterniaServer.connection.executeSQLQuery(connection -> {
            PreparedStatement xpsql = connection.prepareStatement(xp);
            xpsql.execute();
            xpsql.close();
        }, true);
        if (EterniaServer.configs.getBoolean("sql.mysql")) {
            final String money = "CREATE TABLE IF NOT EXISTS " + EterniaServer.configs.getString("sql.table-money") + " (player_name varchar(32), balance double(22,4));";
            EterniaServer.connection.executeSQLQuery(connection -> {
                PreparedStatement moneysql = connection.prepareStatement(money);
                moneysql.execute();
                moneysql.close();
            }, true);
        } else {
            final String money = "CREATE TABLE IF NOT EXISTS " + EterniaServer.configs.getString("sql.table-money") + " (player_name varchar(32), balance double(22));";
            EterniaServer.connection.executeSQLQuery(connection -> {
                PreparedStatement moneysql = connection.prepareStatement(money);
                moneysql.execute();
                moneysql.close();
            }, true);
        }
        final String warp = "CREATE TABLE IF NOT EXISTS " + EterniaServer.configs.getString("sql.table-warp") + " (name varchar(16), location varchar(128));";
        EterniaServer.connection.executeSQLQuery(connection -> {
            PreparedStatement warpsql = connection.prepareStatement(warp);
            warpsql.execute();
            warpsql.close();
        }, true);
        final String shop = "CREATE TABLE IF NOT EXISTS " + EterniaServer.configs.getString("sql.table-shop") + " (name varchar(16), location varchar(128));";
        EterniaServer.connection.executeSQLQuery(connection -> {
            PreparedStatement shopsql = connection.prepareStatement(shop);
            shopsql.execute();
            shopsql.close();
        }, true);
        final String home = "CREATE TABLE IF NOT EXISTS " + EterniaServer.configs.getString("sql.table-home") + " (player_name varchar(16), homes varchar(255));";
        EterniaServer.connection.executeSQLQuery(connection -> {
            PreparedStatement homesql = connection.prepareStatement(home);
            homesql.execute();
            homesql.close();
        }, true);
        final String homes = "CREATE TABLE IF NOT EXISTS " + EterniaServer.configs.getString("sql.table-homes") + " (name varchar(32), location varchar(128));";
        EterniaServer.connection.executeSQLQuery(connection -> {
            PreparedStatement homessql = connection.prepareStatement(homes);
            homessql.execute();
            homessql.close();
        }, true);
        final String kits = "CREATE TABLE IF NOT EXISTS " + EterniaServer.configs.getString("sql.table-kits") + " (name varchar(32), cooldown varchar(32));";
        EterniaServer.connection.executeSQLQuery(connection -> {
            PreparedStatement kitssql = connection.prepareStatement(kits);
            kitssql.execute();
            kitssql.close();
        }, true);
    }

}
