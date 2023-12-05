package za.co.envirobank.envirobank.exceptions;

public class ResourceExpired extends RuntimeException{
    public ResourceExpired(String s) {
        super(s);
    }
}
