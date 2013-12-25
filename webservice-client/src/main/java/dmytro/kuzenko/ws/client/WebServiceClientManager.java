package dmytro.kuzenko.ws.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.martiansoftware.jsap.JSAPException;

import dmytro.kuzenko.shell.ShellManager;

public class WebServiceClientManager extends ShellManager {
    
    private WebServiceClientManager() throws JSAPException {
        super();
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("wsClientContext.xml");
        applicationContext.getBean(WebServiceClientManager.class).start();
    }
}
