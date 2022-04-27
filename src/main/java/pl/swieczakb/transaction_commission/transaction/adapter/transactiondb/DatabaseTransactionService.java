package pl.swieczakb.transaction_commission.transaction.adapter.transactiondb;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import pl.swieczakb.transaction_commission.transaction.domain.model.Amount;
import pl.swieczakb.transaction_commission.transaction.domain.model.ClientId;
import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCurrency;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionDate;
import pl.swieczakb.transaction_commission.transaction.domain.port.TransactionRepository;

public class DatabaseTransactionService implements TransactionRepository {

  private final TransactionJpaRepository repository;

  public DatabaseTransactionService(
      TransactionJpaRepository repository) {
    this.repository = repository;
  }

  @Transactional
  @Override
  public Optional<Transaction> save(TransactionDate transactionDate, Amount amount,
      TransactionCurrency currency, ClientId clientId) {
    final TransactionEntity savedRepository = repository.save(
        TransactionEntity.of(transactionDate, amount, currency,
            clientId));
    return Optional.of(savedRepository.toDomain());
  }

  @Override
  public boolean hasClientHighTurnoverDiscount(ClientId clientId, TransactionDate executionDate) {
    return repository.sumByClientIdAndExecutionDateThisMonth(
        clientId.getId(), executionDate.getDate())
        .filter(sum -> sum >= 1000)
        .isPresent();
  }
}
