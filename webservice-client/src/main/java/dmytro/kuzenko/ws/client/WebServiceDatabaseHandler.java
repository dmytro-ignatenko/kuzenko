package dmytro.kuzenko.ws.client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.RestTemplate;

import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.core.types.Row;
import dmytro.kuzenko.core.types.Table;

public class WebServiceDatabaseHandler implements DatabaseHandler {
    
    private final static String SERVICE_URL = "http://localhost:8080/kuzenko-ws/api";
    
    private RestTemplate restTemplate;

    @Override
    public void forDatabaseFolder(String databaseFolder) throws RemoteException {
        restTemplate.postForObject(SERVICE_URL + "/database/" + databaseFolder, null, Void.class, Collections.emptyMap());
    }

    @Override
    public Table loadTable(String tableName) throws IOException {
        return restTemplate.getForObject(SERVICE_URL + "/table/" + tableName, Table.class, Collections.emptyMap());
    }

    @Override
    public Collection<Table> listTables() throws IOException {
        Table[] result = restTemplate.getForObject(SERVICE_URL + "/table", Table[].class, Collections.emptyMap());
        return Arrays.asList(result);
    }

    @Override
    public Table createTable(String tableName, List<String> columnTypes) throws IOException {
        return restTemplate.postForObject(
            SERVICE_URL + "/table/" + tableName + "?columnTypes={columnTypes}", null, Table.class, 
            Collections.singletonMap("columnTypes", StringUtils.join(columnTypes, ','))
        );
    }

    @Override
    public void removeTable(String tableName) throws IOException {
        restTemplate.delete(SERVICE_URL + "/table/" + tableName);
    }

    @Override
    public Row addRow(String tableName, List<String> columnData) throws Exception {
        return restTemplate.postForObject(
            SERVICE_URL + "/table/" + tableName + "/data?columnData={columnData}", null, Row.class, 
            Collections.singletonMap("columnData", StringUtils.join(columnData, ','))
        );
    }

    @Override
    public List<Row> removeRow(String tableName, List<String> columnData) throws Exception {
        restTemplate.delete(
           SERVICE_URL + "/table/" + tableName + "/data?columnData={columnData}", 
           Collections.singletonMap("columnData", StringUtils.join(columnData, ','))
        );
        return Collections.emptyList();
    }

    @Override
    public void dropDatabase() throws IOException {
        restTemplate.delete(SERVICE_URL + "/database");
    }

    @Override
    public String getDatabaseName() throws RemoteException {
        return restTemplate.getForObject(SERVICE_URL + "/database", String.class, Collections.emptyMap());
    }

    @Override
    public List<Row> loadTableData(String tableName) throws IOException {
        Row[] result = restTemplate.getForObject(
            SERVICE_URL + "/table/" + tableName + "/data", Row[].class, Collections.emptyMap()
        );
        return Arrays.asList(result);
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

	@Override
	public Table descartTable(String tableName1, String tableName2) throws Exception {
		return restTemplate.getForObject(
			SERVICE_URL + "/table/" + tableName1 + "/descart/" + tableName2, Table.class, Collections.emptyMap()
	    );
	}
}
