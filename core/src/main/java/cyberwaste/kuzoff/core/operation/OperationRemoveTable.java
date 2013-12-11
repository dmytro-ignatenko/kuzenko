package cyberwaste.kuzoff.core.operation;

import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseHandler;
import cyberwaste.kuzoff.core.OperationHandler;

public class OperationRemoveTable implements Operation {

    private Map<String,String> parameters;
    private DatabaseHandler databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseHandler databaseManager){
        this.databaseManager = databaseManager;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(OperationHandler ioManager) throws Exception {
        final String tableName = OperationBuilder.getStringParameter(parameters, "name");
        databaseManager.removeTable(tableName);
        ioManager.outputTableRemoved(tableName);
    }

}
