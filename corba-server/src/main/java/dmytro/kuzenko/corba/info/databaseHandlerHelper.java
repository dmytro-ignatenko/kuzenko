package dmytro.kuzenko.corba.info;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.omg.CORBA.Object;
import org.springframework.util.Assert;

import dmytro.kuzenko.core.DatabaseHandler;

public class databaseHandlerHelper {
    
    public DatabaseHandler locateRemoteDatabaseHandler() throws Exception {
        Context ic = new InitialContext();
        
        Object objref = (Object) ic.lookup("DatabaseService");
        Assert.notNull(objref);
        
        DatabaseHandler databaseHandler = (DatabaseHandler) PortableRemoteObject.narrow(objref, DatabaseHandler.class);
        Assert.notNull(databaseHandler);
        
        return databaseHandler;
    }

	public static DatabaseHandler narrow(Object resolve_str,
			Class<DatabaseHandler> class1) throws Exception {
		return null;
	}
}
