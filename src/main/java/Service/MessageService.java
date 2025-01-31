package Service;
import Model.Message;

import java.util.List;

import DAO.MessageDAO;
import DAO.AccountDAO;

public class MessageService {
    MessageDAO messageDAO;
    AccountDAO accountDAO;

    public MessageService(){
        this.messageDAO = new MessageDAO();
        this.accountDAO = new AccountDAO();
    }
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
        this.accountDAO = new AccountDAO();
    }
    public List<Message> getAllMessages(){
        return this.messageDAO.getAllMessages();
    }
    public List<Message> getAllMessages(int id){
        return this.messageDAO.getAllMessages(id);
    }

    public Message createMessage(Message message){
        if (message.getMessage_text() != "" && message.getMessage_text().length() < 255) {
            int id = message.getPosted_by();
            if (accountDAO.getAccount(id) != null) {
                return this.messageDAO.createMessage(message);
            }
        }
        return null;
    }
    public Message deleteMessage(int message_id){
        return this.messageDAO.deleteMessage(message_id);
    }
    public Message getMessage(int message_id){
        return this.messageDAO.getMessage(message_id);
    }
    public Message updateMessage(Message message, int message_id){
        if (message.getMessage_text() != "" && message.getMessage_text().length() < 255){
            if (messageDAO.getMessage(message_id) != null) {
                return this.messageDAO.updateMessage(message, message_id);
            }
        }
        return null;
    }
}