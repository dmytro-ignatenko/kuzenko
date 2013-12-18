package dmytro.kuzenko.iiop.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import javax.rmi.PortableRemoteObject;

import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.core.types.Row;
import dmytro.kuzenko.core.types.Table;

public class RemoteDatabaseHandlerImpl extends PortableRemoteObject implements DatabaseHandler {
    
    private DatabaseHandler delegate;

    protected RemoteDatabaseHandlerImpl() throws RemoteException {
        super();
    }

    @Override
    public void forDatabaseFolder(String databaseFolder) throws RemoteException {
        delegate.forDatabaseFolder(databaseFolder);
    }

    @Override
    public Table loadTable(String tableName) throws IOException {
        return delegate.loadTable(tableName);
    }

    @Override
    public Collection<Table> listTables() throws IOException {
        return delegate.listTables();
    }

    @Override
    public Table createTable(String tableName, List<String> columnTypes) throws IOException {
        return delegate.createTable(tableName, columnTypes);
    }

    @Override
    public void removeTable(String tableName) throws IOException {
        delegate.removeTable(tableName);
    }

    @Override
    public Row addRow(String tableName, List<String> columnData) throws Exception {
        return delegate.addRow(tableName, columnData);
    }

    @Override
    public List<Row> removeRow(String tableName, List<String> columnData) throws Exception {
        return delegate.removeRow(tableName, columnData);
    }

    @Override
    public void dropDatabase() throws IOException {
        delegate.dropDatabase();
    }

    @Override
    public String getDatabaseName() throws RemoteException {
        return delegate.getDatabaseName();
    }

    @Override
    public List<Row> loadTableData(String tableName) throws IOException {
        return delegate.loadTableData(tableName);
    }

    public void setDelegate(DatabaseHandler delegate) {
        this.delegate = delegate;
    }

	@Override
	public Table descartTable(String tableName1, String tableName2) throws Exception {
		return delegate.descartTable(tableName1, tableName2);
	}
}
