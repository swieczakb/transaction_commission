package pl.swieczakb.transaction_commission.transaction.domain.model.exception;

import org.springframework.http.HttpStatus;
import pl.swieczakb.transaction_commission.transaction.adapter.api.DomainException;
import pl.swieczakb.transaction_commission.transaction.adapter.api.ErrorCode;

public class OperationFailedException extends DomainException {

  public OperationFailedException(String message) {
    super(message);
  }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.TC_003;
  }

  @Override
  public int getStatus() {
    return HttpStatus.SERVICE_UNAVAILABLE.value();
  }
}
