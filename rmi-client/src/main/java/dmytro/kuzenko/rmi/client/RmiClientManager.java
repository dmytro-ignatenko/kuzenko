package dmytro.kuzenko.rmi.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.martiansoftware.jsap.JSAPException;

import dmytro.kuzenko.shell.ShellManager;

public class RmiClientManager extends ShellManager {
    
    private RmiClientManager() throws JSAPException {
        super();
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("rmiClientContext.xml");
        applicationContext.getBean(RmiClientManager.class).start();
    }
}
