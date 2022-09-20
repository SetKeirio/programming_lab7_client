package messages;

import java.io.Serializable;

public class CommandMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String commandName;
    private String commandArgument;
    private Serializable objectArgument;

    public CommandMessage(String commandName, String commandArgument, Serializable objectArgument){
        this.commandName = commandName;
        this.commandArgument = commandArgument;
        this.objectArgument = objectArgument;
    }

    public CommandMessage(String commandName, String commandArgument){
        this(commandName, commandArgument, null);
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
        return "Request (command)[Название команды = " + commandName + ", аргумент = " + commandArgument + ", объект = " + objectArgument + "]";
    }

    public boolean isEmpty(){
        return ((commandName.isEmpty()) && (commandArgument.isEmpty()) && (objectArgument == null));
    }
}
