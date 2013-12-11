package cyberwaste.kuzoff.iiop.client;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.springframework.util.Assert;

import cyberwaste.kuzoff.core.DatabaseHandler;

public class RemoteDatabaseManagerFactory {
    
    public DatabaseHandler locateRemoteDatabaseManager() throws Exception {
        Context ic = new InitialContext();
        
        Object objref = ic.lookup("DatabaseService");
        Assert.notNull(objref);
        
        DatabaseHandler databaseManager = (DatabaseHandler) PortableRemoteObject.narrow(objref, DatabaseHandler.class);
        Assert.notNull(databaseManager);
        
        return databaseManager;
    }
}
