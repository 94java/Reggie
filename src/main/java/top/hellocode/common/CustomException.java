package top.hellocode.common;

/**
 * @author HelloCode
 * @blog https://www.hellocode.top
 * @date 2022年11月08日 15:25
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
