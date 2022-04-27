package pl.swieczakb.transaction_commission.transaction.adapter.clientservice;

import java.util.List;
import pl.swieczakb.transaction_commission.transaction.domain.model.ClientId;
import pl.swieczakb.transaction_commission.transaction.domain.port.ClientRepository;

public class FakeClientService implements ClientRepository {

  private final List<ClientEntity> mockDb;

  public FakeClientService() {
    this.mockDb = List.of(new ClientEntity(1, false), new ClientEntity(42, true));
  }

  public FakeClientService(
      List<ClientEntity> mockDb) {
    this.mockDb = mockDb;
  }

  @Override
  public boolean isSpecialClient(ClientId clientId) {
    return mockDb.stream()
        .filter(clientEntity -> clientEntity.getId() == clientId.getId())
        .map(ClientEntity::isSpecial)
        .findAny()
        .orElseThrow(() -> new IllegalStateException("Client doesn't exist!"));
  }

  @Override
  public boolean clientNotExist(ClientId clientId) {
    return mockDb.stream().noneMatch(clientEntity -> clientEntity.getId() == clientId.getId());
  }
}
