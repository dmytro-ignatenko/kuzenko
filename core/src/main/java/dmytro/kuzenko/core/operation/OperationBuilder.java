package dmytro.kuzenko.core.operation;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmytro.kuzenko.core.DatabaseHandler;

public class OperationBuilder {
    
    private final String operationName;
    
    private DatabaseHandler databaseHandler;
    private Map<String, String> parameters;
    private Map<String, Class<?>> operations;

    public static OperationBuilder operation(String operationName) {
        return new OperationBuilder(operationName);
    }

    private OperationBuilder(String operationName) {
        this.operationName = operationName;
        operations = new HashMap<String, Class<?>>();
        operations.put("lstbl", OperationListTables.class);
        operations.put("mktbl", OperationMakeTable.class);
        operations.put("rmtbl", OperationRemoveTable.class);
        operations.put("addrw", OperationAddRow.class);
        operations.put("rmvrw", OperationRemoveRow.class);
        operations.put("drpdb", OperationDropDatabase.class);
        operations.put("swtbl", OperationShowTable.class);
        operations.put("dctbl", OperationDescartTables.class);
    }
    
    public OperationBuilder usingDatabaseManager(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
        return this;
    }

    public OperationBuilder forDatabase(String databaseFolder) throws RemoteException {
        this.databaseHandler.forDatabaseFolder(databaseFolder);
        return this;
    }

    public OperationBuilder withParameters(Map<String, String> parameters) {
        this.parameters = new HashMap<String, String>(parameters);
        return this;
    }

    public Operation build() throws Exception {
        if (databaseHandler.getDatabaseName() == null) {
            throw new RuntimeException("Database folder is not specified");
        }
        
        if(operations.containsKey(operationName)){
            Class<?> operationClass = operations.get(operationName);
            Operation operation = (Operation) operationClass.newInstance();
            operation.setState(parameters, databaseHandler);
            return operation;
        }
        else{
            return new OperationUnknown();
        }
    }

    public static String getStringParameter(Map<String, String> parameters, String key) {
        return parameters.get(key);
    }
    
    public static List<String> getListParameter(Map<String, String> parameters, String key) {
        List<String> result = new ArrayList<String>();
        
        int index = 1;
        String columnType;
        while ((columnType = getStringParameter(parameters, key + "-" + index)) != null) {
            result.add(columnType);
            index++;
        }
        
        return result;
    }

    public static Map<Integer, String> getMapParameter(Map<String,String> parameters, String key, int numColumns){
        Map<Integer, String> result = new HashMap<Integer, String>();
        
        String columnData;
        for (int index = 1;index<=numColumns;index++) {
            columnData = getStringParameter(parameters, key + "-" + index);
            if(columnData != null) result.put(index, columnData);
        }
        
        return result;
    }
}
