package pl.swieczakb.transaction_commission.transaction.domain.port;

import pl.swieczakb.transaction_commission.transaction.domain.model.Amount;
import pl.swieczakb.transaction_commission.transaction.domain.model.ClientId;
import pl.swieczakb.transaction_commission.transaction.domain.model.ExchangeCurrency;
import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionDate;

public interface TransactionRepository {

  Transaction save(TransactionDate transactionDate, Amount amount, ExchangeCurrency currency, ClientId clientId);

  boolean hasClientHighTurnoverDiscount(ClientId clientId, TransactionDate executionDate);

}
