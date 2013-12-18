package dmytro.kuzenko.web.jsf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.core.implementation.DatabaseHandlerImpl;
import dmytro.kuzenko.core.types.Row;
import dmytro.kuzenko.core.types.Table;

public class MainView {

    private DatabaseHandler databaseHandler;
    
    private String database;
    private String table;
    private String tableForOperation;
    private List<String> newRow;
    
    public String getDatabase() {
        return database;
    }
    
    public void setDatabase(String database) {
        this.database = database;
    }
    
    public String getTable() {
        return table;
    }
    
    public void setTable(String table) throws IOException {
        this.table = table;
        resetNewRow();
    }
    
    public String getTableForOperation() {
        return tableForOperation;
    }
    
    public void setTableForOperation(String tableForOperation) {
        this.tableForOperation = tableForOperation;
    }

    public List<String> getDatabases() {
        List<String> databases = new ArrayList<String>();
        for (File databaseFolder : DatabaseHandlerImpl.KUZENKO_HOME.listFiles()) {
            databases.add(databaseFolder.getName());
        }
        return databases;
    }
    
    public Collection<String> getTables() throws IOException {
        try {
            databaseHandler.forDatabaseFolder(database);
            Collection<String> tables = new ArrayList<String>();
            for (Table table : databaseHandler.listTables()) {
                tables.add(table.getName());
            }
            return tables;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    
    public List<Row> getData() throws IOException {
        try {
            databaseHandler.forDatabaseFolder(database);
            return databaseHandler.loadTableData(table);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    
    public List<String> getColumnTypeNames() throws IOException {
        try {
            databaseHandler.forDatabaseFolder(database);
            return databaseHandler.loadTable(table).getColumnTypeNames();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    
    public List<List<String>> getNewRow() {
        return Collections.singletonList(newRow);
    }
    
    public void setNewRow(List<List<String>> newRow) {
        this.newRow = newRow.get(0);
    }
  
    public void addRow() {
        try {
            databaseHandler.forDatabaseFolder(database);
            databaseHandler.addRow(table, newRow);
            resetNewRow();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } 
    
    public void descart() {
        try {
            databaseHandler.forDatabaseFolder(database);
            Table result = databaseHandler.descartTable(table, tableForOperation);
            setTable(result.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } 

    private List<String> resetNewRow() throws IOException {
        newRow  = new ArrayList<String>();
        for (int i = 0; i < getColumnTypeNames().size(); i++) {
            newRow.add("");
        }
        return newRow;
    }
    
    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }
}
