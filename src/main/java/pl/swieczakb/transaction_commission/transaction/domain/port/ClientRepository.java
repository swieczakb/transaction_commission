package pl.swieczakb.transaction_commission.transaction.domain.port;

import pl.swieczakb.transaction_commission.transaction.domain.model.ClientId;
import pl.swieczakb.transaction_commission.transaction.domain.model.TransactionDate;

public interface ClientRepository {

  boolean isSpecialClient(ClientId clientId);

  boolean clientNotExist(ClientId clientId);
}
