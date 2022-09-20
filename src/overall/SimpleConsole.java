package overall;

public class SimpleConsole {

    public static void print(Object obj){
        System.out.print(obj);
    }

    public static void println(Object obj){
        System.out.println(obj);
    }

    public static void println(){
        System.out.println();
    }

    public static void printerr(Object obj){
        System.err.println("Ошибка: " + obj);
    }


}
