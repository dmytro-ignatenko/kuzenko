package dmytro.kuzenko.core.operation;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmytro.kuzenko.core.DatabaseHandler;

public class OperationBuilder {
    
    private final String commandName;
    
    private DatabaseHandler databaseManager;
    private Map<String, String> parameters;
    private Map<String, Class<?>> commands;

    public static OperationBuilder command(String commandName) {
        return new OperationBuilder(commandName);
    }

    private OperationBuilder(String commandName) {
        this.commandName = commandName;
        commands = new HashMap<String, Class<?>>();
        commands.put("lstbl", OperationListTables.class);
        commands.put("mktbl", OperationMakeTable.class);
        commands.put("rmtbl", OperationRemoveTable.class);
        commands.put("addrw", OperationAddRow.class);
        commands.put("rmvrw", OperationRemoveRow.class);
        commands.put("drpdb", OperationDropDatabase.class);
        commands.put("swtbl", OperationShowTable.class);
        commands.put("untbl", OperationUnionTables.class);
        commands.put("dftbl", OperationDifferenceTables.class);
        commands.put("uqtbl", OperationUniqueTable.class);
        commands.put("dctbl", OperationDescartTables.class);
    }
    
    public OperationBuilder usingDatabaseManager(DatabaseHandler databaseManager) {
        this.databaseManager = databaseManager;
        return this;
    }

    public OperationBuilder forDatabase(String databaseFolder) throws RemoteException {
        this.databaseManager.forDatabaseFolder(databaseFolder);
        return this;
    }

    public OperationBuilder withParameters(Map<String, String> parameters) {
        this.parameters = new HashMap<String, String>(parameters);
        return this;
    }

    public Operation build() throws Exception {
        if (databaseManager.getDatabaseName() == null) {
            throw new RuntimeException("Database folder is not specified");
        }
        
        if(commands.containsKey(commandName)){
            Class<?> commandClass = commands.get(commandName);
            Operation command = (Operation) commandClass.newInstance();
            command.setState(parameters, databaseManager);
            return command;
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
