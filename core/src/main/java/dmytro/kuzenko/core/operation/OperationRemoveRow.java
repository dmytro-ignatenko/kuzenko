package dmytro.kuzenko.core.operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.List;

import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.core.OperationHandler;
import dmytro.kuzenko.core.types.Row;

public class OperationRemoveRow implements Operation {

    private Map<String,String> parameters;
    private DatabaseHandler databaseHandler;
    
    public void setState(Map<String,String> parameters, DatabaseHandler databaseHandler){
        this.databaseHandler = databaseHandler;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(OperationHandler ioManager) throws Exception {
        final String tableName = OperationBuilder.getStringParameter(parameters, "name");
        int numColumns = databaseHandler.loadTable(tableName).columnTypes().size();
        final Map<Integer,String> columnDataMap = OperationBuilder.getMapParameter(parameters, "column", numColumns);
        ArrayList<String> columnData = new ArrayList<String>(numColumns);
        columnData.addAll(Arrays.asList(new String[numColumns+1]));
        for (Integer key : columnDataMap.keySet()) {
            columnData.set(key, columnDataMap.get(key));
        }
        List<Row> rowList = databaseHandler.removeRow(tableName,columnData);
        ioManager.outputRowDeleted(rowList);
    }

}
