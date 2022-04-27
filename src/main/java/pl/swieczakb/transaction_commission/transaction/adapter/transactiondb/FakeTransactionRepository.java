package pl.swieczakb.transaction_commission.transaction.adapter.transactiondb;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import pl.swieczakb.transaction_commission.transaction.domain.model.Amount;
import pl.swieczakb.transaction_commission.transaction.domain.model.ClientId;
import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCurrency;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionDate;
import pl.swieczakb.transaction_commission.transaction.domain.port.TransactionRepository;

public class FakeTransactionRepository implements
    TransactionRepository {

  private final List<TransactionEntity> mockDb;

  public FakeTransactionRepository() {
    this.mockDb = new ArrayList<>();
  }

  @Override
  public Optional<Transaction> save(TransactionDate transactionDate, Amount amount,
      TransactionCurrency currency,
      ClientId clientId) {
    final TransactionEntity entity = TransactionEntity.of(transactionDate, amount, currency,
        clientId);
    mockDb.add(entity);
    return Optional.of(entity.toDomain());
  }

  @Override
  public boolean hasClientHighTurnoverDiscount(ClientId clientId, TransactionDate executionDate) {
    return mockDb.stream()
        .filter(transactionEntity -> transactionEntity.getClientId().equals(clientId.getId()))
        .filter(transactionEntity -> isTransactionThisMonth(executionDate, transactionEntity))
        .map(TransactionEntity::getAmount)
        .reduce(BigDecimal::add)
        .filter(bigDecimal -> bigDecimal.compareTo(BigDecimal.valueOf(1000)) >= 0)
        .isPresent();
  }

  private boolean isTransactionThisMonth(TransactionDate executionDate,
      TransactionEntity transactionEntity) {
    return YearMonth.from(transactionEntity.getExecutionDate())
        .equals(YearMonth.from(executionDate.getDate()));
  }
}
