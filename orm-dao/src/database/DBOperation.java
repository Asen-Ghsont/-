package database;

import row.RowInfo;
import table.Table;
import table.TableInfo;

import java.sql.*;
import java.util.Map;

public class DBOperation {

    public boolean creatTable(Table<?> table) {
        try {
            Connection connection = DBConnection.connect();
            Statement statement = connection.createStatement();
            statement.execute(table.getTableInfo().toString());
            DBConnection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public <T> boolean delete(Table<T> table, String primeKeyName, String primeKeyValue) throws SQLException {
        TableInfo<T> tableInfo = table.getTableInfo();
        String tableName = tableInfo.getTableName();
        String sql = "delete from " + tableName + " where " + primeKeyName + "=" + primeKeyValue;
        return update(sql);
    }

    public <T> boolean insert(Table<T> table, Object object) throws SQLException {
        TableInfo<T> tableInfo = table.getTableInfo();
        String tableName = tableInfo.getTableName();
        return update("insert into " + tableName + RowInfo.parse(object));
    }

    public <T> boolean change(Table<T> table, String primeKeyName, String primeKeyValue, Map<String, String> values) throws SQLException {
        StringBuilder sql = new StringBuilder("update " + table.getTableInfo().getTableName() + " set ");
        for (Map.Entry<String, String> entry : values.entrySet()) {
            sql.append(entry.getKey());
            sql.append("=");
            sql.append(entry.getValue());
            sql.append(",");
        }
        sql.deleteCharAt(sql.toString().length() - 1);
        sql.append(" where ").append(primeKeyName).append("=").append(primeKeyValue);
        return update(sql.toString());
    }

    private static boolean update(String sql) throws SQLException {
        int ans;
        Connection connection = DBConnection.connect();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ans = preparedStatement.executeUpdate();

        //close
        DBConnection.close();
        return ans > 0;
    }

    public boolean isExist(Table<?> table, String primeKeyName, String primeKeyValue) {
        String sql = "select * from " + table.getTableInfo().getTableName() + " where " + primeKeyName + "=" + primeKeyValue;
        try {
            ResultSet resultSet = query(sql);
            boolean ans = resultSet.next();
            DBConnection.close();
            return ans;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet getAllInfo(Table<?> table) {
        String sql = "select * from " + table.getTableInfo().getTableName();
        return query(sql);
    }

    public ResultSet getTargetInfo(Table<?> table, String primeKey, String value) {
        String sql = "select * from " + table.getTableInfo().getTableName() + " where " + primeKey + "=" + value;
        return query(sql);
    }

    public ResultSet getAllColumnInfo(Table<?> table, String columnName) {
        String sql = "select " + columnName + " from " + table.getTableInfo().getTableName();
        return query(sql);
    }

    public ResultSet getTargetColumnInfo(Table<?> table, String key, String value, String columnName) {
        String sql = "select " + columnName + " from " + table.getTableInfo().getTableName() + " where " + key + "=" + value;
        return query(sql);
    }

    private static ResultSet query(String sql) {
        Connection connection = DBConnection.connect();
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
