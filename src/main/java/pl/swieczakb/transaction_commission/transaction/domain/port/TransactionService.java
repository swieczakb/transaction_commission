package pl.swieczakb.transaction_commission.transaction.domain.port;

import pl.swieczakb.transaction_commission.transaction.adapter.api.DomainException;
import pl.swieczakb.transaction_commission.transaction.adapter.exchangerate.ExchangeResult;
import pl.swieczakb.transaction_commission.transaction.domain.CommissionCalculator;
import pl.swieczakb.transaction_commission.transaction.domain.model.Amount;
import pl.swieczakb.transaction_commission.transaction.domain.model.ClientId;
import pl.swieczakb.transaction_commission.transaction.domain.model.OriginCurrency;
import pl.swieczakb.transaction_commission.transaction.domain.model.Transaction;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionCommission;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionDate;

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
      TransactionDate transactionDate,
      Amount amount,
      OriginCurrency currency,
      ClientId clientId) throws DomainException {
    transactionDate.validate();
    amount.validate();
    currency.validate();
    clientId.validate(clientRepository);
    final ExchangeResult exchangeResult = exchangeRateService.exchangeToEuro(amount.getValue(),
        currency.getCurrency(), transactionDate.getDate());
    final Transaction transaction = transactionRepository.save(transactionDate,
        exchangeResult.getAmount(),
        exchangeResult.getCurrency(),
        clientId);
    return commissionCalculator.calculate(transaction);
  }
}
