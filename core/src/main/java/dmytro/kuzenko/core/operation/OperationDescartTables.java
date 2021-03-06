package dmytro.kuzenko.core.operation;

import java.util.List;
import java.util.Map;

import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.core.OperationHandler;
import dmytro.kuzenko.core.types.Row;
import dmytro.kuzenko.core.types.Table;

public class OperationDescartTables implements Operation {

	private Map<String,String> parameters;
    private DatabaseHandler databaseHandler;
    
    public void setState(Map<String,String> parameters, DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(OperationHandler ioManager) throws Exception {
        final String tableName1 = OperationBuilder.getStringParameter(parameters, "name-1");
        final String tableName2 = OperationBuilder.getStringParameter(parameters, "name-2"); 
        Table newTable = databaseHandler.descartTable(tableName1, tableName2);
        List<Row> tableData = databaseHandler.loadTableData(newTable.getName());
        ioManager.outputTableData(newTable, tableData);
    }

}
