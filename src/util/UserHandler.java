package util;

import core.Coordinates;
import core.Difficulty;
import core.Person;
import exceptions.ScriptRecursionException;
import exceptions.ScriptWrongInputException;
import exceptions.WrongUsageException;
import messages.CommandMessage;
import messages.MessageLabWork;
import messages.ResponseCodeEnum;
import messages.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class UserHandler {
    private Scanner scanner;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();

    public UserHandler(Scanner scanner){
        this.scanner = scanner;
    }

    private MessageLabWork generateLabWork() throws ScriptWrongInputException{
        LabWorkAsker asker = new LabWorkAsker(scanner);
        if (fileMode()){
            asker.setFileInput(true);
        }
        MessageLabWork answer;

        String name = asker.askName();
        Coordinates coordinates = asker.askCoordinates();
        double minimalPoint = asker.askMinimalPoint();
        long maximumQualities = asker.askPersonalQualitiesMaximum();
        Difficulty difficulty = asker.askDifficulty();
        Person author = asker.askAuthor();
        answer = new MessageLabWork(name, coordinates, minimalPoint, maximumQualities, difficulty, author);
        return answer;
    }

    public CommandMessage handle(ResponseCodeEnum serverCode, User user) throws IOException {
        String input;
        String[] command = {"", ""};
        ProcessCodeEnum code = null;
        int attempts = 0;
        try {
            while (code == ProcessCodeEnum.ERROR && fileMode() || command[0].isEmpty()) {
                try {
                    if (fileMode() && (serverCode == ResponseCodeEnum.SERVER_OFF || serverCode == ResponseCodeEnum.ERROR)) {
                        throw new ScriptWrongInputException();
                    }
                    while (fileMode() && !(scanner.hasNextLine())) {
                        scanner.close();
                        scanner = scannerStack.pop();
                        Console.println("?????????? ??????????????.");
                        Console.println("?????????????? ?? ???????????????? ??????????????: " + scriptStack.pop().getName());
                    }
                    if (fileMode()) {
                        input = scanner.nextLine();
                        Console.println(input);
                    } else {
                        input = scanner.nextLine();
                    }
                    command = (input.trim() + " ").split(" ", 2);
                    command[1] = command[1].trim();
                    command[0] = command[0].toLowerCase(Locale.ROOT);
                } catch (IllegalStateException | NoSuchElementException e) {
                    Console.printerr("???????????? ???? ?????????? ??????????!");
                    command[0] = "";
                    command[1] = "";
                }
                code = checkCommand(command[0], command[1]);
            }
                try {
                    if (fileMode() && (serverCode == ResponseCodeEnum.ERROR || code == ProcessCodeEnum.ERROR)) {
                        throw new ScriptWrongInputException();
                    }
                    switch (code) {
                        case OBJECT:
                            MessageLabWork lw = generateLabWork();
                            return new CommandMessage(command[0], command[1], lw, user);
                        case UPDATE_OBJECT:
                            MessageLabWork lw1 = updateLabWork();
                            return new CommandMessage(command[0], command[1], lw1, user);
                        case SCRIPT:
                            File script = new File(command[1]);
                            if (script.exists() == false) {
                                throw new FileNotFoundException();
                            }
                            if (!scriptStack.isEmpty() && scriptStack.search(script) != -1) {
                                throw new ScriptRecursionException();
                            }
                            scannerStack.push(scanner);
                            scriptStack.push(script);
                            scanner = new Scanner(script);
                            Console.println("?????????????? ???? ????????????: " + script.getName());
                    }
                } catch (ScriptRecursionException e) {
                    Console.printerr("???????????????????? ????????????????!");
                    throw new ScriptRecursionException();
                } catch (FileNotFoundException e) {
                    Console.printerr("???? ?????????????? ?????????? ???????? ???? ????????????????.");
                }
        }
        catch (ScriptWrongInputException e){
            while (scannerStack.isEmpty() == false){
                scanner.close();
                scanner = scannerStack.pop();
            }
            scriptStack.clear();
            Console.printerr("???????????????? ???????????????????? ????????????.");
            CommandMessage empty = new CommandMessage(user);
            return empty;
        }
            CommandMessage answer = new CommandMessage(command[0], command[1], null, user);
            return answer;
        }

    private ProcessCodeEnum checkCommand(String command, String commandArgument){
        try {
            switch (command) {
                case "":
                    return ProcessCodeEnum.ERROR;
                case "clear":
                    if (!commandArgument.isEmpty()) {
                        throw new WrongUsageException();
                    }
                    break;
                case "execute_script":
                    if (commandArgument.isEmpty()) {
                        throw new WrongUsageException("?????????? ?????????????? <?????? ??????????????>", "path");
                    }
                    return ProcessCodeEnum.SCRIPT;
                case "exit":
                    if (!commandArgument.isEmpty()) {
                        throw new WrongUsageException();
                    }
                    Console.printerr("???????????? ?????????????????????????? ?????????????????? ???????? ????????????.");
                    System.exit(0);
                    break;
                case "group_counting_by_personal_qualities_maximum":
                    if (!commandArgument.isEmpty()) {
                        throw new WrongUsageException();
                    }
                    break;
                case "help":
                    if (!commandArgument.isEmpty()) {
                        throw new WrongUsageException();
                    }
                    break;
                case "info":
                    if (!commandArgument.isEmpty()) {
                        throw new WrongUsageException();
                    }
                    break;
                case "insert":
                    if (!commandArgument.isEmpty()) {
                        throw new WrongUsageException();
                    }
                    return ProcessCodeEnum.OBJECT;
                case "print_descending":
                    if (!commandArgument.isEmpty()) {
                        throw new WrongUsageException();
                    }
                    break;
                case "remove_any_by_personal_qualities_maximum":
                    if (commandArgument.isEmpty()) {
                        throw new WrongUsageException("?????????? ?????????????? <PERSONAL_QUALITIES_MAXIMUM>", "personal_qualities_maximum");
                    }
                    break;
                case "remove_greater_key":
                    if (commandArgument.isEmpty()) {
                        throw new WrongUsageException("?????????? ?????????????? <ID>", "id");
                    }
                    break;
                case "remove_key":
                    if (commandArgument.isEmpty()) {
                        throw new WrongUsageException("?????????? ?????????????? <ID>", "id");
                    }
                    break;
                case "replace_if_greater":
                    if (commandArgument.isEmpty()) {
                        throw new WrongUsageException("?????????? ?????????????? <ID>", "id");
                    }
                    return ProcessCodeEnum.OBJECT;
                case "replace_if_lower":
                    if (commandArgument.isEmpty()) {
                        throw new WrongUsageException("?????????? ?????????????? <ID>", "id");
                    }
                    return ProcessCodeEnum.OBJECT;
                case "exit_server":
                    if (!commandArgument.isEmpty()) {
                        throw new WrongUsageException();
                    }
                    break;
                case "save":
                    Console.printerr("?????????????? ???????????????????? ???????????????????? ???? ??????????????!");
                    return ProcessCodeEnum.ERROR;
                case "show":
                    if (!commandArgument.isEmpty()) {
                        throw new WrongUsageException();
                    }
                    break;
                case "update":
                    if (commandArgument.isEmpty()) {
                        throw new WrongUsageException("?????????? ?????????????? <ID>", "id");
                    }
                    return ProcessCodeEnum.OBJECT;
                default:
                    Console.printerr("?????????????? " + command + " ???? ????????????????????!");
                    Console.println("??????????????????: help");
                    return ProcessCodeEnum.ERROR;
            }
        }
            catch (WrongUsageException e){
                Console.printerr(e.getMessage());
                Console.println("??????????????????????????: " + command + " " + e.getNeededArgument());
                return ProcessCodeEnum.ERROR;
            }
            return ProcessCodeEnum.OK;
        }

    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }

    private MessageLabWork updateLabWork() throws ScriptWrongInputException{
        LabWorkAsker asker = new LabWorkAsker(scanner);
        if (fileMode()){
            asker.setFileInput(true);
        }
        String name = null;
        if (asker.askQuestion("???????????? ???????????????? ???????")) {
            name = asker.askName();
        }
        Coordinates coordinates = null;
        if (asker.askQuestion("???????????? ???????????????? ?????????????????????")) {
            coordinates = asker.askCoordinates();
        }
        double minimalPoint = 0.0;
        if (asker.askQuestion("???????????? ???????????????? ?????????????????????? ?????????????")) {
            minimalPoint = asker.askMinimalPoint();
        }
        long personalMaximum = 0;
        if (asker.askQuestion("???????????? ???????????????? ???????????????????????? ?????????????????")) {
            personalMaximum = asker.askPersonalQualitiesMaximum();
        }
        Difficulty difficulty = null;
        if (asker.askQuestion("???????????? ???????????????? ???????????????????")) {
            difficulty = asker.askDifficulty();
        }
        Person author = null;
        if (asker.askQuestion("???????????? ???????????????? ?????????????")) {
            author = asker.askAuthor();
        }
        return new MessageLabWork(name, coordinates, minimalPoint, personalMaximum, difficulty, author);
    }
}
