package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        ArrayList<Message>messages = new ArrayList<>();
        String sql = "SELECT * FROM message;";
        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            Message newMessage = new Message();
            while (rs.next()) {
                newMessage.setMessage_id(rs.getInt("message_id"));
                newMessage.setPosted_by(rs.getInt("posted_by"));
                newMessage.setMessage_text(rs.getString("message_text"));
                newMessage.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                messages.add(newMessage);
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return messages;
    }
    public List<Message> getAllMessages(int id){
        Connection connection = ConnectionUtil.getConnection();
        ArrayList<Message>messages = new ArrayList<>();
        String sql = "SELECT * FROM message WHERE posted_by = ?;";
        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            Message newMessage = new Message();
            while (rs.next()) {
                newMessage.setMessage_id(rs.getInt("message_id"));
                newMessage.setPosted_by(rs.getInt("posted_by"));
                newMessage.setMessage_text(rs.getString("message_text"));
                newMessage.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
                messages.add(newMessage);
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return messages;
    }
    public Message getMessage(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        Message message = new Message();
        String sql = "SELECT * FROM message WHERE message_id = ?;";
        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                message.setMessage_id(rs.getInt("message_id"));
                message.setPosted_by(rs.getInt("posted_by"));
                message.setMessage_text(rs.getString("message_text"));
                message.setTime_posted_epoch(rs.getLong("time_posted_epoch"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (message.getMessage_text()!= null) {
            return message;
        }
        return null;
    }
    public Message createMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet rs= preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                message.setMessage_id((int) rs.getLong(1));
                return message;
            }
        }
            catch(SQLException e){
                System.out.println(e.getMessage());
            }
        return null;
    }
    public Message deleteMessage(int id){
        Message resultMessage = getMessage(id);
        if (resultMessage != null){
            Connection connection = ConnectionUtil.getConnection();
            String sql = "DELETE FROM message WHERE message_id = ?;";
            try{            
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                return resultMessage;
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return null;
    }
    public Message updateMessage(Message message, int message_id){
        Connection connection = ConnectionUtil.getConnection();
        String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, message.getMessage_text());
            preparedStatement.setInt(2, message_id);
            preparedStatement.executeUpdate();
            return getMessage(message_id);
        }
            catch(SQLException e){
                System.out.println(e.getMessage());
            }
        return null;
    }
}
