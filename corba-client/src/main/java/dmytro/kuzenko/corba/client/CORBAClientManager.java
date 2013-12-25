package dmytro.kuzenko.corba.client;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.martiansoftware.jsap.JSAPException;

import dmytro.kuzenko.corba.info.databaseHandlerHelper;
import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.shell.ShellManager;

public class CORBAClientManager extends ShellManager {
    
    private CORBAClientManager() throws JSAPException {
        super();
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("corbaClientContext.xml");
        applicationContext.getBean(CORBAClientManager.class).start();
        
        try {
        	ORB orb = ORB.init(args, null);
        	org.omg.CORBA.Object objRef = 
        		orb.resolve_initial_references("NameService");
        	NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
        	String name = "DatabaseService";
        	DatabaseHandler databaseHandlerImpl = (DatabaseHandler) databaseHandlerHelper.narrow(ncRef.resolve_str(name), DatabaseHandler.class);
        	setDatabaseHandlerImpl(databaseHandlerImpl);
        } catch (Exception e) {
            System.out.println("Error: " + e) ;
            e.printStackTrace(System.out);
       }
    }
}
