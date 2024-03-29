package mhkif.yc.docguardian.dtos.requests;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class UserReq {

    @NotBlank(message = "First name is Required")
    @Size(min = 3, max = 20, message = "First name must be between 3 and 20 characters")
    private String first_name ;

    @NotBlank(message = "Last Name is Required")
    @Size(min = 3, max = 20, message = "Last Name must be between 3 and 20 characters")
    private String last_name;

    @NotNull(message = "email field is required")
    @NotBlank(message = "email field is required")
    @Email(message = "this field must be a valid email")
    private String email;

    @NotNull(message = "Password is Required")
    @Size(min = 6, max = 26, message = "Email must be valid & between 6 and 26 characters")
    private String password;

    @NotNull(message = "phone is Required")
    @Size(min = 10, max = 10,  message = "Phone number must be 10 numbers")
    private String phone;



}