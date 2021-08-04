package primo.musicdb.model.binding;

import org.hibernate.validator.constraints.Length;
import primo.musicdb.model.validators.FieldMatch;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@FieldMatch(
        first = "password",
        second = "confirmPassword"
)
public class UserRegistrationBindingModel {
    private String username;
    private String email;
    private String fullName;
    private String password;
    private String confirmPassword;


    @NotEmpty
    @Length(min = 3)
    public String getUsername() {
        return username;
    }

    public UserRegistrationBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @NotEmpty
    @Email
    public String getEmail() {
        return email;
    }

    public UserRegistrationBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @NotEmpty
    @Length(min = 3)
    public String getFullName() {
        return fullName;
    }

    public UserRegistrationBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @NotEmpty
    @Length(min = 5, max = 20)
    public String getPassword() {
        return password;
    }

    public UserRegistrationBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @NotEmpty
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
