package pl.swieczakb.transaction_commission.transaction.adapter.api;

public abstract class DomainException extends Exception{

    public DomainException(String message) {
        super(message);
    }

    public abstract ErrorCode getErrorCode();

    public abstract int getStatus();

}
