package dmytro.kuzenko.core.operation;

import java.util.Map;
import java.util.List;

import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.core.OperationHandler;
import dmytro.kuzenko.core.types.Row;
import dmytro.kuzenko.core.types.Table;

public class OperationShowTable implements Operation {

    private Map<String,String> parameters;
    private DatabaseHandler databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseHandler databaseManager){
        this.databaseManager = databaseManager;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(OperationHandler ioManager ) throws Exception {
        final String tableName = OperationBuilder.getStringParameter(parameters, "name");
        List<Row> tableData = databaseManager.loadTableData(tableName);
        Table result = databaseManager.loadTable(tableName);
        ioManager.outputTableData(result, tableData);
    }

}
