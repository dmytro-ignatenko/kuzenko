package dmytro.kuzenko.core.operation;

import java.util.Map;

import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.core.OperationHandler;

public class OperationRemoveTable implements Operation {

    private Map<String,String> parameters;
    private DatabaseHandler databaseHandler;
    
    public void setState(Map<String,String> parameters, DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(OperationHandler ioManager) throws Exception {
        final String tableName = OperationBuilder.getStringParameter(parameters, "name");
        databaseHandler.removeTable(tableName);
        ioManager.outputTableRemoved(tableName);
    }

}
