package pl.swieczakb.transaction_commission.transaction.domain.port;

import pl.swieczakb.transaction_commission.transaction.adapter.api.DomainException;
import pl.swieczakb.transaction_commission.transaction.adapter.exchangerate.ExchangeResult;
import pl.swieczakb.transaction_commission.transaction.domain.CommissionCalculator;
import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCommission;

public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final ExchangeRateService exchangeRateService;
  private final CommissionCalculator commissionCalculator;
  private final ClientRepository clientRepository;

  public TransactionService(
      TransactionRepository transactionRepository,
      ExchangeRateService exchangeRateService,
      CommissionCalculator commissionCalculator,
      ClientRepository clientRepository) {
    this.transactionRepository = transactionRepository;
    this.exchangeRateService = exchangeRateService;
    this.commissionCalculator = commissionCalculator;
    this.clientRepository = clientRepository;
  }

  public TransactionCommission propagateTransaction(
      Transaction transaction) throws DomainException {
    transaction.validate(clientRepository);
    final ExchangeResult exchangeResult = exchangeRateService.exchangeToEuro(
        transaction.getAmount().getValue(),
        transaction.getCurrency().getCurrency(),
        transaction.getDate().getDate());
    final Transaction updatedTransaction = transactionRepository.save(transaction.getDate(),
        exchangeResult.getAmount(),
        exchangeResult.getCurrency(),
        transaction.getClientId());
    return commissionCalculator.calculate(updatedTransaction);
  }
}
