package next.sh.driving_school.rest.provided.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@ToString
public class EleveVo {
    private String uuid;
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 30, message = "Username must be between 6 and 30 characters long")
    private String username;
    @Email
    @NotNull
    @NotEmpty
    @Size(min = 10, max = 30)
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 6)
    private String password;
    private String lastName;
    private String firstName;
    private String ville;
    private String address;
    private String gender;
    boolean status;
    private String accountState;
    @NotNull
    @NotEmpty
    @Size(min = 6)
    private String cin;
    @NotNull
    @NotEmpty
    private String phone;
    private String roles;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date updateDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date endDate;
}
