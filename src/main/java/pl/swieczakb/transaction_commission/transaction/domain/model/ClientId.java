package pl.swieczakb.transaction_commission.transaction.domain.model;

import pl.swieczakb.transaction_commission.transaction.domain.model.exception.ValidationException;
import pl.swieczakb.transaction_commission.transaction.domain.port.ClientRepository;

public class ClientId {

  private final Long id;

  public ClientId(Long clientId) {
    this.id = clientId;
  }

  public static ClientId of(Long clientId) {
    return new ClientId(clientId);
  }

  public Long getId() {
    return id;
  }

  public void validate(ClientRepository clientRepository) throws ValidationException {
    if (id == null){
      throw new ValidationException("ClientId can't be null!");
    }
    if(clientRepository.clientNotExist(this)){
      throw new ValidationException("Client doesn't exist!");
    }
  }
}
