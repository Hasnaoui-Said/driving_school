package next.sh.driving_school.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "Payiement")
public class Payiement {
    @Id
    UUID uuid;
    @NotEmpty @NotNull @Size(min = 500, max = 5000, message = "Total pay must between 500 and 5000")
    String totalPay;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date payiementDate;
    @ManyToOne
    Eleve eleve;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(String totalPay) {
        this.totalPay = totalPay;
    }

    public Date getPayiementDate() {
        return payiementDate;
    }

    public void setPayiementDate(Date payiementDate) {
        this.payiementDate = payiementDate;
    }

    @JsonIgnore
    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }
}
