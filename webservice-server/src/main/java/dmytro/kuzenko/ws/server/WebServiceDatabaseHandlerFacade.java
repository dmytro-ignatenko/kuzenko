package dmytro.kuzenko.ws.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.core.types.Row;
import dmytro.kuzenko.core.types.Table;

@Controller
@RequestMapping("/api")
public class WebServiceDatabaseHandlerFacade implements DatabaseHandler {
    
    @Autowired
    private DatabaseHandler delegate;
    
    @Override
    @RequestMapping(value="/database/{databaseFolder}", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.FOUND)
    public void forDatabaseFolder(@PathVariable("databaseFolder") String databaseFolder) throws RemoteException {
        delegate.forDatabaseFolder(databaseFolder);
    }

    @Override
    @RequestMapping(value="/table/{tableName}", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody Table loadTable(@PathVariable("tableName") String tableName) throws IOException {
        return delegate.loadTable(tableName);
    }

    @Override
    @RequestMapping(value="/table", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody Collection<Table> listTables() throws IOException {
        return delegate.listTables();
    }

    @Override
    @RequestMapping(value="/table/{tableName}", method=RequestMethod.POST, produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Table createTable(@PathVariable("tableName") String tableName, @RequestParam("columnTypes") List<String> columnTypes) throws IOException {
        return delegate.createTable(tableName, columnTypes);
    }

    @Override
    @RequestMapping(value="/table/{tableName}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTable(@PathVariable("tableName") String tableName) throws IOException {
        delegate.removeTable(tableName);
    }

    @Override
    @RequestMapping(value="/table/{tableName}/data", method=RequestMethod.POST, produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Row addRow(@PathVariable("tableName") String tableName, @RequestParam("columnData") List<String> columnData) throws Exception {
        return delegate.addRow(tableName, columnData);
    }

    @Override
    @RequestMapping(value="/table/{tableName}/data", method=RequestMethod.DELETE, produces="application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody List<Row> removeRow(@PathVariable("tableName") String tableName, @RequestParam("columnData") List<String> columnData) throws Exception {
        return delegate.removeRow(tableName, columnData);
    }

    @Override
    @RequestMapping(value="/database", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dropDatabase() throws IOException {
        delegate.dropDatabase();
    }

    @Override
    @RequestMapping(value="/database", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody String getDatabaseName() throws RemoteException {
        return delegate.getDatabaseName();
    }

    @Override
    @RequestMapping(value="/table/{tableName}/data", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody List<Row> loadTableData(@PathVariable("tableName") String tableName) throws IOException {
        return delegate.loadTableData(tableName);
    }

    public void setDelegate(DatabaseHandler delegate) {
        this.delegate = delegate;
    }

	@Override
	@RequestMapping(value="/table/{tableName1}/descart/{tableName2}", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody Table descartTable(@PathVariable("tableName1") String tableName1, @PathVariable("tableName2") String tableName2) throws Exception {
		
		return delegate.descartTable(tableName1, tableName2);
	}
}
