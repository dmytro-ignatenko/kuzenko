package dmytro.kuzenko.core.operation;

import java.util.Map;

import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.core.OperationHandler;

public class OperationDropDatabase implements Operation {

    private DatabaseHandler databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseHandler databaseManager){
        this.databaseManager = databaseManager;
    }
    
    @Override
    public void execute(OperationHandler ioManager ) throws Exception {
        databaseManager.dropDatabase();
        ioManager.outputDatabaseDropped(databaseManager.getDatabaseName());
    }

}
