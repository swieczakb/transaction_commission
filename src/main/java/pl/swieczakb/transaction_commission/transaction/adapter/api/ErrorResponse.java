package pl.swieczakb.transaction_commission.transaction.adapter.api;

import java.time.LocalDateTime;

public class ErrorResponse {

  private final int status;
  private final String message;
  private final LocalDateTime timestamp;
  private final ErrorCode errorCode;

  public ErrorResponse(int status, String message, LocalDateTime timestamp,
      ErrorCode errorCode) {
    this.status = status;
    this.message = message;
    this.timestamp = timestamp;
    this.errorCode = errorCode;
  }

  public static ErrorResponse of(DomainException domainException) {
    return new ErrorResponse(domainException.getStatus(), domainException.getMessage(),
        LocalDateTime.now(), domainException.getErrorCode());
  }

  public int getStatus() {
    return status;
  }

  public String getMessage() {
    return message;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
