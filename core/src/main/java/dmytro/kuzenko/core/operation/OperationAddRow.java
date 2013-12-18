package dmytro.kuzenko.core.operation;

import java.util.List;
import java.util.Map;

import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.core.OperationHandler;
import dmytro.kuzenko.core.types.Row;

public class OperationAddRow implements Operation {
    
    private Map<String,String> parameters;
    private DatabaseHandler databaseHandler;
    
    public void setState(Map<String,String> parameters, DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
        this.parameters = parameters;
    }
    
    public void execute(OperationHandler ioManager) throws Exception {
        final String tableName = OperationBuilder.getStringParameter(parameters, "name");
        final List<String> columnData = OperationBuilder.getListParameter(parameters,"column");
        Row new_row = databaseHandler.addRow(tableName,columnData);
        ioManager.outputRowAdded(new_row);
    }

}
