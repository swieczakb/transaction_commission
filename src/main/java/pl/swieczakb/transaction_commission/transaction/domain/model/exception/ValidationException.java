package pl.swieczakb.transaction_commission.transaction.domain.model.exception;

import org.springframework.http.HttpStatus;
import pl.swieczakb.transaction_commission.transaction.adapter.api.DomainException;
import pl.swieczakb.transaction_commission.transaction.adapter.api.ErrorCode;

public class ValidationException extends DomainException {

  public ValidationException(String message) {
    super(message);
  }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.TC_001;
  }

  @Override
  public int getStatus() {
    return HttpStatus.BAD_REQUEST.value();
  }
}
