package util;

import exceptions.LimitIgnoreException;
import exceptions.ScriptWrongInputException;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class AuthorizationAsker {
    private Scanner scanner;

    public AuthorizationAsker(Scanner scanner){
        this.scanner = scanner;
    }

    public String askLogin(){
        String answer = "";
        while (true){
            try{
                Console.println("Ваш логин:");
                answer = scanner.nextLine().trim();
                if (answer.isEmpty()){
                    throw new LimitIgnoreException();
                }
                return answer;
            }
            catch (LimitIgnoreException e){
                Console.printerr("Логин не может быть пустым!");
            }
        }
    }

    public String askPassword(){
        String answer = "";
        while (true){
            try{
                Console.println("Ваш пароль:");
                answer = scanner.nextLine().trim();
                if (answer.isEmpty()){
                    throw new LimitIgnoreException();
                }
                return answer;
            }
            catch (LimitIgnoreException e){
                Console.printerr("Пароль не может быть пустым!");
            }
        }
    }

    public boolean askQuestion(String question) throws ScriptWrongInputException {
        String format = question + " (д/н)?";
        String answer;
        while (1==1){
            try{
                Console.println(format);
                answer = scanner.nextLine().trim().toLowerCase();
                if ((!answer.equals("д")) && !(answer.equals("н"))){
                    throw new LimitIgnoreException();
                }
                break;
            }
            catch (NoSuchElementException e){
                Console.printerr("Ответ введен неверно.");
            }
            catch (LimitIgnoreException e){
                Console.printerr("Ответ должен быть буквой д или н.");
            }
            catch (IllegalStateException e) {
                Console.printerr("Неисправимая ошибка.");
                System.exit(1);
            }

        }
        return (answer.equals("д")) ? true : false;
    }
}
