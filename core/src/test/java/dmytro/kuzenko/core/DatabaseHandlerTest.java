package dmytro.kuzenko.core;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import dmytro.kuzenko.core.implementation.DatabaseHandlerImpl;
import dmytro.kuzenko.core.types.Row;
import dmytro.kuzenko.core.types.Table;
import dmytro.kuzenko.core.types.Type;

//@Ignore
public class DatabaseHandlerTest {
    
    private DatabaseHandlerImpl handler;
    
    @Before
    public void setUp() throws Exception {
        handler = new DatabaseHandlerImpl();
        handler.forDatabaseFolder("/home/dmytro/Documents/db_test");
        List<String> types = new ArrayList<String>();
        types.add("int"); types.add("char");
        handler.createTable("table1",types);
        List<String> row = new ArrayList<String>();
        row.add("10"); row.add("a");
        handler.addRow("table1",row);
        row = new ArrayList<String>();
        row.add("20"); row.add("b");
        handler.addRow("table1", row);
        
        handler.createTable("table2", types);
        row.clear();
        row.add("20"); row.add("b");
        handler.addRow("table2", row);
        row.clear();
        row.add("30"); row.add("c");
        handler.addRow("table2", row);
        row.clear();
        row.add("30"); row.add("c");
        handler.addRow("table2", row);
        
    }

    @After
    public void tearDown() throws Exception {
        handler.dropDatabase();
    }

    @Test
    public void listTablesTest() throws IOException {
        String[] typeNames = {"int", "char"}; 
        Collection<Table> tables = handler.listTables();
        assertEquals(2, tables.size());
        Iterator<Table> it = tables.iterator();
        Table table = it.next();
        assertEquals(table.getName(), "table1");
        List<Type> types = table.columnTypes();
        for(int i=0;i<types.size();i++){
            assertEquals(types.get(i).getName(),typeNames[i]);
        }
        table = it.next();
        assertEquals(table.getName(), "table2");
        for(int i=0;i<types.size();i++){
            assertEquals(types.get(i).getName(),typeNames[i]);
        }
    }
    
    @Test
    public void loadTableDataTest() throws IOException{
        List<Row> rows = handler.loadTableData("table1");
        assertEquals(2,rows.size());
        for(Row row : rows) assertEquals(2,row.length());
        String[][] rowDataActual = {{"10","a"},{"20","b"}};
        for(int i=0; i<rows.size();i++){
            Row row = rows.get(i);
            for(int j=0;j<row.length();j++){
                assertEquals(row.getElement(j).getValue(), rowDataActual[i][j]);
            }
        }
    }
    
    @Test
    public void removeRowTest() throws Exception {
        List<String> columnData = Arrays.asList(new String[]{null, "10", "a"});
        handler.removeRow("table1", columnData);
        List<Row> rows = handler.loadTableData("table1");
        assertEquals(1,rows.size());
        Row row = rows.get(0);
        String[] rowDataActual = {"20","b"};
        for(int i=0;i<row.length();i++){
            assertEquals(row.getElement(i).getValue(), rowDataActual[i]);
        }
    }
    
    @Test
    public void unionTablesTest() throws Exception{
        Table unionTable = handler.unionTable("table1", "table2");
        List<Row> rows = handler.loadTableData(unionTable.getName());
        assertEquals(3, rows.size());
        String[][] rowDataActual = {{"10","a"},{"20","b"},{"30","c"}};
        for(int i=0;i<rows.size();i++){
            for(int j=0;j<unionTable.columnTypes().size();j++){
                assertEquals(rowDataActual[i][j], rows.get(i).getElement(j).getValue());
            }
        }
    }

    @Test
    public void differenceTableTest() throws Exception{
        Table differenceTable = handler.differenceTable("table1", "table2");
        List<Row> rows = handler.loadTableData(differenceTable.getName());
        assertEquals(1, rows.size());
        Row row = rows.get(0);
        String[] rowDataActual = {"10","a"};
        for(int i=0;i<differenceTable.columnTypes().size();i++){
            assertEquals(rowDataActual[i], row.getElement(i).getValue());
        }
    }
    
    @Test
    public void uniqueTableTest() throws Exception{
        Table uniqueTable = handler.uniqueTable("table2");
        List<Row> rows = handler.loadTableData(uniqueTable.getName());
        assertEquals(2, rows.size());
        String[][] rowDataActual = {{"20","b"},{"30","c"}};
        for(int i=0;i<rows.size();i++){
            for(int j=0;j<uniqueTable.columnTypes().size();j++){
                assertEquals(rowDataActual[i][j], rows.get(i).getElement(j).getValue());
            }
        }
    }
    
    @Test
    public void descartTableTest() throws Exception {
    	Table descartTable = handler.descartTable("table1", "table2");
        List<Row> rows = handler.loadTableData(descartTable.getName());
        assertEquals(4, rows.size()); 
        String[][] rowDataActual = {{"10","a","20","b"},{"10","a","30","c"},{"20","b","20","b"},{"20","b","30","c"}};
        for(int i=0;i<rows.size();i++){
            for(int j=0;j<descartTable.columnTypes().size();j++){
                assertEquals(rowDataActual[i][j], rows.get(i).getElement(j).getValue());
            }
        }
    }
    
}
