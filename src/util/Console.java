package util;

import exceptions.ScriptRecursionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class which works with input.
 */
public class Console {

    private Scanner scanner;
    private LabWorkAsker asker;
    private ArrayList<String> scriptStack = new ArrayList<>();

    public Console(Scanner sc, LabWorkAsker la){
        scanner = sc;
        asker = la;
    }

    /**
     * Prints one line.
     * @param o
     */
    public static void println(Object o){
        System.out.println(o);
    }

    /**
     * Prints some text.
     * @param o
     */
    public static void print(Object o){
        System.out.print(o);
    }

    /**
     * Prints the error.
     * @param o
     */
    public static void printerr(Object o){
        System.err.println(o);
    }

    /**
     * Works with input in user mode.
     */
    public void userMode(){
        String[] command = new String[2];
        byte commandCode = 0;
        try{
            while (true) {
                command = (scanner.nextLine().trim() + " ").split(" ", 2);
                command[1] = command[1].trim();
            }
        }
        catch (NoSuchElementException exception) {
            Console.printerr("Ввод не обнаружен.");
        } catch (IllegalStateException exception) {
            Console.printerr("Неисправимая ошибка.");
        }
    }



    /**
     * Works with input in script mode.
     * @param param
     * @return
     */
    public byte scriptMode(String param){
        String[] command = new String[2];
        byte commandCode = 0;
        scriptStack.add(param);
        try (Scanner sc = new Scanner(new File(param))){
            if (!sc.hasNext()){
                throw new NoSuchElementException();
            }
            Scanner sc1 = asker.getScanner();
            asker.setScanner(sc);
            asker.setFileInput(true);
            do {
                command = (sc.nextLine().trim() + " ").split(" ",2);
                command[1] = command[1].trim();
                while (sc.hasNextLine() && command[0].isEmpty()){
                    command = (sc.nextLine().trim() + " ").split(" ", 2);
                    command[1] = command[1].trim();
                }
                Console.println(String.join(" ", command));
                if (command[0].equals("execute_script")) {
                    for (String script: scriptStack) {
                        if (command[1].equals(script)) throw new ScriptRecursionException();
                    }
                }
            } while (sc.hasNextLine() && commandCode == (byte) 0);
            asker.setScanner(sc1);
            asker.setFileInput(false);
            if (commandCode != 0 && !command[1].isEmpty() && !(command[0].equals("execute_script"))){
                Console.println("Введенные данные некорректны!");
            }
            return commandCode;

        } catch (FileNotFoundException exception) {
            Console.printerr("Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            Console.printerr("Файл является пустым!");
        } catch (ScriptRecursionException exception) {
            Console.printerr("Найдена ресурсия в скрипте!");
        } catch (IllegalStateException exception) {
            Console.printerr("Неисправимая ошибка!");
            System.exit(0);
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return (byte) 1;
    }


}
