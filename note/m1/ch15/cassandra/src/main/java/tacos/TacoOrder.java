package tacos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import com.datastax.oss.driver.api.core.uuid.Uuids;

import lombok.Data;

@Data
@Table("orders")
public class TacoOrder implements Serializable {

  private static final long serialVersionUID = 1L;

  @PrimaryKey
  private UUID id = Uuids.timeBased();

  private Date placedAt = new Date();

  private String deliveryName;
  private String deliveryStreet;
  private String deliveryCity;
  private String deliveryState;
  private String deliveryZip;
  private String ccNumber;
  private String ccExpiration;
  private String ccCVV;

  @Column("tacos")
  private List<TacoUDT> tacos = new ArrayList<>();

  public void addTaco(Taco taco) {
    this.tacos.add(TacoUDTUtils.toTacoUDT(taco));
  }

  public void addTaco(TacoUDT taco) {
    this.tacos.add(taco);
  }

}
