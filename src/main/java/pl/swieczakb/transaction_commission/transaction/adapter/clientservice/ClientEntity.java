package pl.swieczakb.transaction_commission.transaction.adapter.clientservice;

public class ClientEntity {

  private final long id;
  private final boolean isSpecial;

  public ClientEntity(long id, boolean isSpecial) {
    this.id = id;
    this.isSpecial = isSpecial;
  }

  public long getId() {
    return id;
  }

  public boolean isSpecial() {
    return isSpecial;
  }
}
