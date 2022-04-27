package pl.swieczakb.transaction_commission.transaction.domain.port;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.swieczakb.transaction_commission.transaction.adapter.api.DomainException;
import pl.swieczakb.transaction_commission.transaction.domain.TestTransactionModule;
import pl.swieczakb.transaction_commission.transaction.domain.model.Amount;
import pl.swieczakb.transaction_commission.transaction.domain.model.ClientId;
import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCommission;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCurrency;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionDate;

@SpringBootTest
public class TransactionServiceIT {

  @Value("classpath:data/transaction_test_scenario.csv")
  private Resource csv;

  @Autowired
  private TransactionService transactionService;

  @Test
  void shouldCheckAllFlows() throws DomainException, IOException {
    //given
    final List<TestScenario> scenarios = Files.readAllLines(csv.getFile().toPath()).stream()
        .skip(1)
        .map(s -> Arrays.asList(s.split(",")))
        .map(TestScenario::of)
        .collect(Collectors.toList());

    for (TestScenario testScenario : scenarios) {
      //when
      final TransactionCommission result = transactionService.propagateTransaction(
          testScenario.getTestTransaction());

      //then
      assertThat(result.getAmount()).isEqualTo(testScenario.getResult().getAmount());
      assertThat(result.getCurrencyCode()).isEqualTo(testScenario.getResult().getCurrencyCode());
    }
  }

  private static class TestScenario {

    private final TransactionCommission result;
    private final Transaction testTransaction;

    public TestScenario(
        TransactionCommission result,
        Transaction testTransaction) {
      this.result = result;
      this.testTransaction = testTransaction;
    }

    public static TestScenario of(List<String> row) {
      return new TestScenario(
          TransactionCommission.of(new BigDecimal(row.get(4)), TransactionCurrency.of(row.get(5))),
          Transaction.of(
              TransactionDate.of(LocalDate.parse(row.get(1), DateTimeFormatter.ISO_LOCAL_DATE)),
              Amount.of(new BigDecimal(row.get(2))),
              TransactionCurrency.of(row.get(3)),
              ClientId.of(Long.valueOf(row.get(0)))));
    }

    public TransactionCommission getResult() {
      return result;
    }

    public Transaction getTestTransaction() {
      return testTransaction;
    }
  }
}
