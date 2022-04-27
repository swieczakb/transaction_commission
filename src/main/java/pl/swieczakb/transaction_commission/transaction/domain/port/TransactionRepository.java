package pl.swieczakb.transaction_commission.transaction.domain.port;

import java.util.Optional;
import pl.swieczakb.transaction_commission.transaction.domain.model.Amount;
import pl.swieczakb.transaction_commission.transaction.domain.model.ClientId;
import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCurrency;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionDate;

public interface TransactionRepository {

  Optional<Transaction> save(TransactionDate transactionDate, Amount amount,
      TransactionCurrency currency, ClientId clientId);

  boolean hasClientHighTurnoverDiscount(ClientId clientId, TransactionDate executionDate);

}
