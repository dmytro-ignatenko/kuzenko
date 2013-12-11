package cyberwaste.kuzoff.core.operation;

import java.util.Collection;
import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseHandler;
import cyberwaste.kuzoff.core.OperationHandler;
import cyberwaste.kuzoff.core.domain.Table;

public class OperationListTables implements Operation {
    
    private Map<String,String> parameters;
    private DatabaseHandler databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseHandler databaseManager){
        this.databaseManager = databaseManager;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(OperationHandler ioManager) throws Exception {
        final String tableName = OperationBuilder.getStringParameter(parameters, "name");
        
        if (tableName != null) {
            Table result = databaseManager.loadTable(tableName);
            ioManager.outputTableInfo(result);
        } else {
            Collection<Table> result = databaseManager.listTables();
            ioManager.outputListTables(result);
        }

    }

}
