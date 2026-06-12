package lk.kaushalya.jms;

import jakarta.jms.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class QueueApp {
    public static void main(String[] args) {
        try {
            InitialContext ic = new InitialContext();

            QueueConnectionFactory factory = (QueueConnectionFactory) ic.lookup("jms/myQueueConnectionFactory");
            QueueConnection connection = factory.createQueueConnection();
            connection.start();

            QueueSession session = connection.createQueueSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            Queue queue = (Queue) ic.lookup("jms/myQueue");
            QueueSender sender = session.createSender(queue);

            Scanner sc = new Scanner(System.in);
            System.out.println("====== QueueConnectionFactory ======");
            System.out.println("Enter message or type 'exit' to quit:");

            while(true){
                String line = sc.nextLine();
                if(line.equalsIgnoreCase("exit")){
                    break;
                }

                TextMessage txtMessage = session.createTextMessage();
                txtMessage.setText(line);

                sender.send(txtMessage);
            }


        } catch (NamingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
