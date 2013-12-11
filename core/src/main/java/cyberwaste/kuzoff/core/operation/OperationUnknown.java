package cyberwaste.kuzoff.core.operation;

import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseHandler;
import cyberwaste.kuzoff.core.OperationHandler;

public class OperationUnknown implements Operation {

    public void setState(Map<String,String> parameters, DatabaseHandler databaseManager) { }
    
    @Override
    public void execute(OperationHandler ioManager) throws Exception {
        throw new Exception("Unknown command");
    }
}
