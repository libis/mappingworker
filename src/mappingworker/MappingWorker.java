
package mappingworker;

import com.rabbitmq.client.QueueingConsumer;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import queuing.QueuingServer;
import utils.Configuration;
import utils.LibisUtil;

/**
 *
 * @author NaeemM
 */
public class MappingWorker {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException, ParseException {
        QueuingServer qServer = new QueuingServer();
        QueueingConsumer consumer = qServer.createQueue();
        
        LibisUtil util = new LibisUtil();
        Configuration mappingConf = new Configuration();
        JSONParser parser = new JSONParser();
        Mapping mapp = new Mapping();

        Properties mappingProp = mappingConf.getConfigurations("mappingworker");
        String requestDir = System.getProperty("user.dir") + "/" 
                            + mappingProp.getProperty("request_directory");

        /* Create base request directory if it does not exists, 
           this directory will contain a subdirectory for each request
        */
        if (!new File(requestDir).exists())
            util.createFile(requestDir); 
 
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        while (true) {            
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody()); 
            System.out.println("Message received");
            System.out.println(util.prettyPrintJson(message));
            
            JSONObject messageBodyobj   = (JSONObject) parser.parse(message);             
            String content              = messageBodyobj.get("content").toString();
            String mappingRules         = messageBodyobj.get("mappingRules").toString();
            String sourceFormat         = messageBodyobj.get("sourceFormat").toString();
            String targetFormat         = messageBodyobj.get("targetFormat").toString();
            String requestId            = messageBodyobj.get("requestId").toString();
            
            String requestPath          = requestDir + "/" + requestId;
            util.createFile(requestPath);                   /* create a directory for request*/
            String contentFile          = requestPath + "/" + "content.xml";
            String mappingRulesFile     = requestPath + "/" + "mappingrules.csv";
            util.writeFile(contentFile, content);
            util.writeFile(mappingRulesFile, mappingRules);
            
            mapp.mappRecords(requestPath, sourceFormat, targetFormat);
            
            //System.out.println("request id : " + requestId);            
            
            
        }        
    }
    
}
