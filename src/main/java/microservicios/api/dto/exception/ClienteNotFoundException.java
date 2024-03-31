package microservicios.api.dto.exception;

public class ClienteNotFoundException extends RuntimeException{
    public ClienteNotFoundException(){
    }
    public ClienteNotFoundException(String message){
        super(message);
    }
    public ClienteNotFoundException(String message, Throwable cause){
        super(message,cause);
    }
    public ClienteNotFoundException(Throwable cause){
        super(cause);
    }
    public ClienteNotFoundException(String message, Throwable cause, boolean enableSuppresion, boolean writableStackTrace){
        super(message,cause,enableSuppresion,writableStackTrace);
    }
}
