package messages;

import java.io.Serializable;

public class CommandMessage implements Serializable {
    private User user;
    private static final long serialVersionUID = 1L;
    private String commandName;
    private String commandArgument;
    private Serializable objectArgument;

    public CommandMessage(String commandName, String commandArgument, Serializable objectArgument, User user){
        this.commandName = commandName;
        this.commandArgument = commandArgument;
        this.objectArgument = objectArgument;
        this.user = user;
    }

    public CommandMessage(String commandName, String commandArgument, User user){
        this(commandName, commandArgument, null, user);
    }

    public CommandMessage(User user) { // пустой запрос
        this("", "", user);
    }

    public boolean isEmpty(){
        return ((commandName.equals("")) && (commandArgument.equals("")) && (objectArgument == null) && (user == null));
    }

    public User getUser() {
        return user;
    }

    public CommandMessage(){
        this("", "", null);
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandArgument() {
        return commandArgument;
    }

    public Object getObjectArgument() {
        return objectArgument;
    }

    @Override
    public String toString() {
        return "Request (command)[Название команды = " + commandName + ", аргумент = " + commandArgument + ", объект = " + objectArgument + ", пользователь = " + user.getUsername() + "]";
    }
}
