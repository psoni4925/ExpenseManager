package in.pranjal.expensetrackerapi.entity;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserModel {

    @NotBlank(message = "name can't be empty")
    private String name;

    @NotNull(message = "password can't be empty")
    @Size(min = 5, message = "password should be atleast 5 characters")
    private String password;

    @Email(message = "Enter valid email")
    @NotNull(message = "Email should not be empty")
    private String email;

    private Long age = 0L;
}
