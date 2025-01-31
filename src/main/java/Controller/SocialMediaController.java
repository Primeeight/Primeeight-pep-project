package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService = new AccountService();
    MessageService messageService = new MessageService();
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::postUserHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.post("messages", this::createNewMessageHandler);
        app.post("login", this::loginAccountHandler);
        app.get("accounts/{account_id}/messages", this::getAllMessagesIdHandler);
        app.delete("messages/{message_id}", this::deleteMessageHandler);
        app.get("messages/{message_id}", this::getMessageHandler);
        app.patch("messages/{message_id}", this::updateMessageHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    private void postUserHandler (Context context) throws JsonProcessingException { 
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account resultAccount = accountService.registerUser(account);
        if (resultAccount != null) {
            context.json(mapper.writeValueAsString(resultAccount));            
        }
        else{
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context){
        List<Message>messages = messageService.getAllMessages();
        context.status(200);
        context.json(messages);        
    }
    private void createNewMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message resultMessage = messageService.createMessage(message);
        if (resultMessage != null) {
            context.status(200);
            context.json(mapper.writeValueAsString(resultMessage));
        }
        else{
            context.status(400);
        }
    }
    private void loginAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account resultAccount = accountService.loginAccount(account);
        if (resultAccount != null) {
            context.status(200);
            context.json(mapper.writeValueAsString(resultAccount));

        }
        else{
            context.status(401);
        }
    }
    private void getAllMessagesIdHandler(Context context) throws JsonProcessingException{
        
        String id = (context.pathParam("account_id"));
        int newId = Integer.valueOf(id);
        List <Message> messages = messageService.getAllMessages(newId);
        context.status(200);
        context.json(messages);
    }
    private void deleteMessageHandler(Context context) throws JsonProcessingException{
        String id = (context.pathParam("message_id"));
        int newId = Integer.valueOf(id);
        Message message = messageService.deleteMessage(newId);
        context.status(200);
        if (message != null) {
            context.json(message);
        }else{
            context.result("");
        }    
    }
    private void getMessageHandler(Context context)throws JsonProcessingException{
        int id = Integer.valueOf(context.pathParam("message_id"));
        Message message = messageService.getMessage(id);
        if (message != null) {
            context.json(message);    
        }
        else{
            context.result("");
        }
        context.status(200);
        
    }
    private void updateMessageHandler(Context context)throws JsonProcessingException{
        int id = Integer.valueOf(context.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(),Message.class);
        Message newMessage = messageService.updateMessage(message, id);
        if (newMessage != null) {
            context.json(newMessage);
            context.status(200);    
        }
        else{
            context.status(400);
        }
        
        
    }
}