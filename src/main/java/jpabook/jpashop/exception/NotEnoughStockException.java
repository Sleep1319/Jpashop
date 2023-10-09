package jpabook.jpashop.exception;

public class NotEnoughStockException extends RuntimeException{
    //계속 돌아가는 단계에서 실행중이라 런타임익셉션으로
    public NotEnoughStockException() { super(); }

    public NotEnoughStockException(String message) { super(message); }

    public NotEnoughStockException(String message, Throwable cause) { super(message, cause); }

    public NotEnoughStockException(Throwable cause) { super(cause); }
}
