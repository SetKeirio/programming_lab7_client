package util;

import messages.CommandMessage;
import messages.User;

import java.util.Scanner;

public class AuthorizationManager {

    private Scanner scanner;
    AuthorizationAsker asker;
    public AuthorizationManager(Scanner scanner){
        this.scanner = scanner;
    }

    public CommandMessage authorize(){
        asker = new AuthorizationAsker(scanner);
        String commandName;
        if(asker.askQuestion("Вы уже зарегистрированы?")){
            commandName = "login";
        }
        else{
            commandName = "register";
        }
        User user = new User(asker.askLogin(), asker.askPassword());
        return new CommandMessage(commandName, "", user);
    }
}
