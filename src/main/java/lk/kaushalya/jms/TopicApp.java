package lk.kaushalya.jms;

import jakarta.jms.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class TopicApp {
    public static void main(String[] args) {
        try {
            InitialContext ic = new InitialContext();

            TopicConnectionFactory factory = (TopicConnectionFactory) ic.lookup("jms/myTopicConnectionFactory");
            TopicConnection connection = factory.createTopicConnection();
            connection.start();

            TopicSession session = connection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            Topic topic = (Topic) ic.lookup("myTopic");
            TopicPublisher publisher = session.createPublisher(topic);

            Scanner sc = new Scanner(System.in);
            System.out.println("====== TopicConnectionFactory ======");
            System.out.println("Enter message or type 'exit' to quit:");

            while(true){
                String line = sc.nextLine();
                if(line.equalsIgnoreCase("exit")){
                    break;
                }

                TextMessage txtMessage = session.createTextMessage();
                txtMessage.setText(line);

                publisher.publish(txtMessage);
            }


        } catch (NamingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
