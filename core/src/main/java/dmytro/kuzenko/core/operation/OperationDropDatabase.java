package dmytro.kuzenko.core.operation;

import java.util.Map;

import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.core.OperationHandler;

public class OperationDropDatabase implements Operation {

    private DatabaseHandler databaseHandler;
    
    public void setState(Map<String,String> parameters, DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
    }
    
    @Override
    public void execute(OperationHandler ioManager ) throws Exception {
        databaseHandler.dropDatabase();
        ioManager.outputDatabaseDropped(databaseHandler.getDatabaseName());
    }

}
