package cyberwaste.kuzoff.core.operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.List;

import cyberwaste.kuzoff.core.DatabaseHandler;
import cyberwaste.kuzoff.core.OperationHandler;
import cyberwaste.kuzoff.core.domain.Row;

public class OperationRemoveRow implements Operation {

    private Map<String,String> parameters;
    private DatabaseHandler databaseManager;
    
    public void setState(Map<String,String> parameters, DatabaseHandler databaseManager){
        this.databaseManager = databaseManager;
        this.parameters = parameters;
    }
    
    @Override
    public void execute(OperationHandler ioManager) throws Exception {
        final String tableName = OperationBuilder.getStringParameter(parameters, "name");
        int numColumns = databaseManager.loadTable(tableName).columnTypes().size();
        final Map<Integer,String> columnDataMap = OperationBuilder.getMapParameter(parameters, "column", numColumns);
        ArrayList<String> columnData = new ArrayList<String>(numColumns);
        columnData.addAll(Arrays.asList(new String[numColumns+1]));
        for (Integer key : columnDataMap.keySet()) {
            columnData.set(key, columnDataMap.get(key));
        }
        List<Row> rowList = databaseManager.removeRow(tableName,columnData);
        ioManager.outputRowDeleted(rowList);
    }

}
