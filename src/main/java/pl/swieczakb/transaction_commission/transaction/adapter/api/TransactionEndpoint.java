package pl.swieczakb.transaction_commission.transaction.adapter.api;

import io.vavr.control.Either;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transaction")
public class TransactionEndpoint {

  private final TransactionFacade transactionApiService;

  public TransactionEndpoint(
      TransactionFacade transactionApiService) {
    this.transactionApiService = transactionApiService;
  }

  @PostMapping
  public ResponseEntity sendTransaction(
      @RequestBody DelegateTransactionRequest request) {
    final Either<ResponseEntity<ErrorResponse>, ResponseEntity<DelegateTransactionResponse>> responseEntities =
        transactionApiService.sendTransaction(request)
            .map(ResponseEntity::ok)
            .mapLeft(errorResponse -> ResponseEntity.status(errorResponse.getStatus())
                .body(errorResponse));
    return responseEntities.isRight() ? responseEntities.get() : responseEntities.getLeft();
  }
}
