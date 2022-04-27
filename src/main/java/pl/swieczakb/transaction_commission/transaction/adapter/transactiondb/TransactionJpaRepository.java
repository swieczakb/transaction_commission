package pl.swieczakb.transaction_commission.transaction.adapter.transactiondb;

import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {

  @Query(value = "SELECT SUM(t.AMOUNT)  FROM TRANSACTION t WHERE t.CLIENT_ID= ?1 AND MONTH(t.EXECUTION_DATE) = MONTH(?2) AND YEAR(t.EXECUTION_DATE) = YEAR(?2)",
      nativeQuery = true)
  Optional<Integer> sumByClientIdAndExecutionDateThisMonth(Long clientId, LocalDate date);
}
