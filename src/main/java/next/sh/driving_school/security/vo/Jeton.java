package next.sh.driving_school.security.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Jeton {
    private String successJeton;
    private String refreshJeton;

}
