package dmytro.kuzenko.core.operation;

import java.util.Map;

import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.core.OperationHandler;

public class OperationUnknown implements Operation {

    public void setState(Map<String,String> parameters, DatabaseHandler databaseHandler) { }
    
    @Override
    public void execute(OperationHandler ioManager) throws Exception {
        throw new Exception("Unknown command");
    }
}
