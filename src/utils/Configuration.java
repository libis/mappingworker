
package utils;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author NaeemM
 */
public class Configuration {
    public Properties getConfigurations(String configuratinFor){
        Properties prop = new Properties();
        String configFile = "";
        switch(configuratinFor){
            case "queuingserver":
                configFile = "queue_server_conf";
                break;
            case "mappingworker":
                configFile = "mapping_worker_conf";
                break;                
        }
        
        try {	                         
            prop.load(this.getClass().getResourceAsStream("/resources/" + configFile + ".properties"));                       						
        } catch (IOException ex) {            
            System.out.println(ex.getMessage());            
        } 		  
        return prop; 		  
    }      
}
