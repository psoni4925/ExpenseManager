package in.pranjal.expensetrackerapi.exception;

public class ResourceNotFound extends RuntimeException {

    private static final long serialVersionID = 1L;

    public ResourceNotFound(String message){
        super(message);
    }
}
