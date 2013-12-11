package cyberwaste.kuzoff.core.operation;

import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseHandler;
import cyberwaste.kuzoff.core.OperationHandler;

public interface Operation {
    void execute(OperationHandler commandManager) throws Exception;
    void setState(Map<String,String> parameters, DatabaseHandler databaseManager);
}
