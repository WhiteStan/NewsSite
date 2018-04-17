package by.makouski.news.exception;

/**
 * Created by Stanislau_Makouski on 10/14/2016.
 */
public class DAOException extends Exception {
    public DAOException(){
        super();
    }
    public DAOException(String message){
        super(message);
    }
    public DAOException(String message, Throwable cause){
        super(message, cause);
    }
    public DAOException(Throwable cause){
        super(cause);
    }
    public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
