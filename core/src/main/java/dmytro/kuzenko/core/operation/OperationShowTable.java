package dmytro.kuzenko.core.operation;

import java.util.Map;
import java.util.List;

import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.core.OperationHandler;
import dmytro.kuzenko.core.types.Row;
import dmytro.kuzenko.core.types.Table;

public class OperationShowTable implements Operation {

    private Map<String,String> parameters;
    private DatabaseHandler databaseHandler;
    
    public void setState(Map<String,String> parameters, DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(OperationHandler ioManager ) throws Exception {
        final String tableName = OperationBuilder.getStringParameter(parameters, "name");
        List<Row> tableData = databaseHandler.loadTableData(tableName);
        Table result = databaseHandler.loadTable(tableName);
        ioManager.outputTableData(result, tableData);
    }

}
