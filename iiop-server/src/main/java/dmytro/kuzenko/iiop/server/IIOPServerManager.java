package dmytro.kuzenko.iiop.server;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IIOPServerManager {
    
    private RemoteDatabaseHandlerImpl remoteDatabaseHandler;
    
    @SuppressWarnings("resource")
    public static void main(String[] args) throws BeansException, Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("iiopServerContext.xml");
        applicationContext.getBean(IIOPServerManager.class).start(args);
    }
    
    public void start(String[] args) throws Exception {        
        Context initialNamingContext = new InitialContext();
        initialNamingContext.rebind("DatabaseService", remoteDatabaseHandler);
        System.out.println("Database Server: Ready...");
    }
    
    public void setRemoteDatabaseHandler(RemoteDatabaseHandlerImpl remoteDatabaseHandler) {
        this.remoteDatabaseHandler = remoteDatabaseHandler;
    }
}
