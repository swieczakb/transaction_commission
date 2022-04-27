package pl.swieczakb.transaction_commission.transaction.adapter.api;

public enum ErrorCode {

  TC_001("Exception.ValidationError"),
  TC_002("Exception.UnknownError"),
  TC_003("Exception.OperationFailed");

  private final String description;

  ErrorCode(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
