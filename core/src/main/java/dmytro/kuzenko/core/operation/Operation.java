package dmytro.kuzenko.core.operation;

import java.util.Map;

import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.core.OperationHandler;

public interface Operation {
    void execute(OperationHandler commandManager) throws Exception;
    void setState(Map<String,String> parameters, DatabaseHandler databaseManager);
}
