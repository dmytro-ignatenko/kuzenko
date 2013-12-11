package cyberwaste.kuzoff.core.operation;

import java.util.Map;
import java.util.List;

import cyberwaste.kuzoff.core.DatabaseHandler;
import cyberwaste.kuzoff.core.OperationHandler;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;

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
