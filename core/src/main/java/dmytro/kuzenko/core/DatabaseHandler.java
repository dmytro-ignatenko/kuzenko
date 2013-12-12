package dmytro.kuzenko.core;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import dmytro.kuzenko.core.types.Row;
import dmytro.kuzenko.core.types.Table;

public interface DatabaseHandler extends Remote {

    void forDatabaseFolder(String databaseFolder) throws RemoteException;

    Table loadTable(String tableName) throws IOException;

    Collection<Table> listTables() throws IOException;

    Table createTable(String tableName, List<String> columnTypes) throws IOException;

    void removeTable(String tableName) throws IOException;

    Row addRow(String tableName, List<String> columnData) throws Exception;

    List<Row> removeRow(String tableName, List<String> columnData) throws Exception;

    void dropDatabase() throws IOException;

    String getDatabaseName() throws RemoteException;

    List<Row> loadTableData(String tableName) throws IOException;
    
    Table descartTable(String tableName1, String tableName2) throws Exception;
}
