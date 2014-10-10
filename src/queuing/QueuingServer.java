
package queuing;


import java.util.Properties;
import utils.Configuration;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import java.io.IOException;


/**
 *
 * @author NaeemM
 */
public class QueuingServer {
    private final Configuration conf;

    public QueuingServer() {
        this.conf = new Configuration();
        
    }
    
    public QueueingConsumer createQueue() throws IOException{
        Properties prop = this.conf.getConfigurations("queuingserver");
        System.out.println(prop.get("rmq_server"));
        Connection connection = this.connectQueuingServer(prop);
        Channel channel = connection.createChannel();
        channel.queueDeclare(prop.getProperty("rmq_mapping_queue"), false, false, false, null);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(prop.getProperty("rmq_mapping_queue"), true, consumer); 
        
        return consumer;
    }
    
    public Connection connectQueuingServer(Properties queuingServerConfig) throws IOException{        
        ConnectionFactory factory = new ConnectionFactory();        
        factory.setHost(queuingServerConfig.getProperty("rmq_server"));
        factory.setPort(Integer.parseInt(queuingServerConfig.getProperty("rmq_port")));
        factory.setUsername(queuingServerConfig.getProperty("rmq_id"));
        factory.setPassword(queuingServerConfig.getProperty("rmq_pwd"));
        factory.setVirtualHost(queuingServerConfig.getProperty("rmq_vhost"));
        //factory.setVirtualHost("/");                    //temporary, remove it for production
        
        Connection connection = factory.newConnection();
        return connection;
    }    


}
