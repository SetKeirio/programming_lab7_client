package exceptions;

public class WrongUsageException extends RuntimeException{
    public String getNeededArgument() {
        return neededArgument;
    }

    private String neededArgument;
    public WrongUsageException(){
        super();
    }

    public WrongUsageException(String message, String neededArgument){
        super(message);
        this.neededArgument = neededArgument;

    }
}
