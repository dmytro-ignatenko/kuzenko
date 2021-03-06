package dmytro.kuzenko.iiop.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.martiansoftware.jsap.JSAPException;

import dmytro.kuzenko.shell.ShellManager;

public class IIOPClientManager extends ShellManager {
    
    private IIOPClientManager() throws JSAPException {
        super();
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("iiopClientContext.xml");
        applicationContext.getBean(IIOPClientManager.class).start();
    }
}
