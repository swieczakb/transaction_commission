package pl.swieczakb.transaction_commission.transaction.adapter.api;

import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCommission;
import pl.swieczakb.transaction_commission.transaction.domain.port.TransactionService;

@Component
public class TransactionFacade {

  private static final Logger LOG = LoggerFactory.getLogger(TransactionFacade.class);

  private final TransactionService transactionService;

  public TransactionFacade(
      TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  public Either<ErrorResponse, DelegateTransactionResponse> sendTransaction(
      DelegateTransactionRequest request) {
    try {
      final TransactionCommission transactionCommission = transactionService.propagateTransaction(
          Transaction.of(
              request.getTransactionDate(),
              request.getAmount(), request.getCurrency(), request.getClientId()));
      return Either.right(DelegateTransactionResponse.of(transactionCommission));
    } catch (DomainException e) {
      LOG.error("Domain exception has been thrown",e);
      return Either.left(ErrorResponse.of(e));
    } catch (Exception e) {
      LOG.error("Unknown exception has been thrown",e);
      return Either.left(ErrorResponse.of(new UnknownErrorException(e.getMessage())));
    }
  }
}
