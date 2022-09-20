package run;

import exceptions.LimitIgnoreException;
import exceptions.WrongElementsCountException;
import util.Console;
import util.UserHandler;

import java.util.Scanner;

/**
 * Main app class. Starts the program.
 */
public class App {
    private static String host;
    private static int port;
    private static final int TIMEOUT_RETRY = 10000; // ms
    private static final int ATTEMPTS_RETRY = 5;

    private static byte setupConnection(String[] args){
        try {
            if (args.length != 2){
                throw new WrongElementsCountException();
            }
            host = args[0];
            port = Integer.parseInt(args[1]);
            if (port <= 0) {
                throw new LimitIgnoreException();
            }
            return 0;
        }
        catch (WrongElementsCountException e){
            Console.printerr("Не удалось соединиться.");
            Console.println("Верное соединение: java -jar <имя jar>" + host + " " + port);
            return 1;
        }
        catch (LimitIgnoreException e){
            Console.printerr("Порт должен быть положительным!");
            return 2;
        }
        catch (NumberFormatException e){
            Console.printerr("Порт должен быть числом!");
            return 3;
        }
        //return -1;
    }
    public static void main(String[] args) {
        byte connectionCode = setupConnection(new String[]{"localhost", "1984"});
        if (connectionCode != 0){
            System.exit(0);
        }
        try (Scanner scanner = new Scanner(System.in)){
            UserHandler handler = new UserHandler(scanner);
            Client client = new Client(host, port, ATTEMPTS_RETRY, TIMEOUT_RETRY, handler);
            client.start();
        }
    }


}
