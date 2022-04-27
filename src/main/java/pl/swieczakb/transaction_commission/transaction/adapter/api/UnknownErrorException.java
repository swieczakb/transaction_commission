package pl.swieczakb.transaction_commission.transaction.adapter.api;

import org.springframework.http.HttpStatus;

public class UnknownErrorException extends
    DomainException {

  UnknownErrorException(String message) {
    super(message);
  }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.TC_002;
  }

  @Override
  public int getStatus() {
    return HttpStatus.INTERNAL_SERVER_ERROR.value();
  }
}
