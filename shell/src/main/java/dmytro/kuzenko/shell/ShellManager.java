package dmytro.kuzenko.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.martiansoftware.jsap.FlaggedOption;
import com.martiansoftware.jsap.JSAP;
import com.martiansoftware.jsap.JSAPException;
import com.martiansoftware.jsap.JSAPResult;
import com.martiansoftware.jsap.Parameter;
import com.martiansoftware.jsap.SimpleJSAP;

import dmytro.kuzenko.core.DatabaseHandler;
import dmytro.kuzenko.core.OperationHandler;
import dmytro.kuzenko.core.operation.Operation;
import dmytro.kuzenko.core.operation.OperationBuilder;

public class ShellManager extends OperationHandler {
    
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("shellClientContext.xml");
        applicationContext.getBean(ShellManager.class).start();
    }
    
    private static final String DATABASE_FOLDER_OPTION = "database";
    private static final String COMMAND_NAME_OPTION = "command";
    private static final String PARAMETERS_OPTION = "parameters";
    
    private static final Object EXIT_COMMAND = "exit";
    
    private final JSAP jsap;
    private final BufferedReader reader;
    
    private DatabaseHandler databaseHandler;
    
    private boolean hasMoreCommands;
    
    protected ShellManager() throws JSAPException {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.jsap = new SimpleJSAP(
            "kuzenko",
            "",
            new Parameter[] {
                new FlaggedOption(DATABASE_FOLDER_OPTION)
                    .setShortFlag('d').setLongFlag("database").setUsageName("database root folder").setRequired(true),
                new FlaggedOption(COMMAND_NAME_OPTION)
                    .setShortFlag('c').setLongFlag("command").setUsageName("command to execute").setRequired(true),
                new FlaggedOption(PARAMETERS_OPTION)
                    .setShortFlag('p')
                    .setLongFlag("parameters")
                    .setUsageName("command parameters (name_1=value_1;...name_k=value_k)")
                    .setRequired(false)
            }
        );
        
        this.hasMoreCommands = true;
    }
    
    @Override
    protected Operation getNextOperation() throws Exception {
        String nextCommandAsString = reader.readLine();
        
        if (EXIT_COMMAND.equals(nextCommandAsString)) {
            hasMoreCommands = false;
            return null;
        }
        
        JSAPResult result = jsap.parse(nextCommandAsString);
        
        if (result.success()) {
            String databaseFolder = result.getString(DATABASE_FOLDER_OPTION);
            String commandName = result.getString(COMMAND_NAME_OPTION);
            Map<String, String> parameters = parseParameters(result.getString(PARAMETERS_OPTION, ""));
            
            return OperationBuilder
                    .operation(commandName)
                    .usingDatabaseManager(databaseHandler)
                    .forDatabase(databaseFolder)
                    .withParameters(parameters)
                    .build();
        } else {
            throw new Exception("Wrong command syntax: " + nextCommandAsString);
        }
    }

    @Override
    protected boolean hasMoreOperations() {
        return hasMoreCommands;
    }
    
    @Override
    protected void outputResult(String message) {
        System.out.println(message);
    }

    @Override
    protected void outputMessage(String message) {
        System.err.println(message);
    }
    
    public static void setDatabaseHandlerImpl(DatabaseHandler databaseHandler) {
    	if (databaseHandler != null) {
    		System.out.println("Cool!");
    	}
    }

    private static Map<String, String> parseParameters(String parametersAsString) throws IOException {
        Properties properties = new Properties();
        properties.load(new StringReader(parametersAsString.replace(';', '\n')));
        
        Map<String, String> result = new HashMap<String, String>();
        for (String propertyName : properties.stringPropertyNames()) {
            result.put(propertyName, properties.getProperty(propertyName));
        }
        
        return result;
    }
    
    public void setDatabaseHandler(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }
}
