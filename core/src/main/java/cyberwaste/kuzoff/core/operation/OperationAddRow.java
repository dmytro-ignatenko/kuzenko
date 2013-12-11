package cyberwaste.kuzoff.core.operation;

import java.util.List;
import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseHandler;
import cyberwaste.kuzoff.core.OperationHandler;
import cyberwaste.kuzoff.core.domain.Row;

public class OperationAddRow implements Operation {
    
    private Map<String,String> parameters;
    private DatabaseHandler databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseHandler databaseManager){
        this.databaseManager = databaseManager;
        this.parameters = parameters;
    }
    
    public void execute(OperationHandler ioManager) throws Exception {
        final String tableName = OperationBuilder.getStringParameter(parameters, "name");
        final List<String> columnData = OperationBuilder.getListParameter(parameters,"column");
        Row new_row = databaseManager.addRow(tableName,columnData);
        ioManager.outputRowAdded(new_row);
    }

}
