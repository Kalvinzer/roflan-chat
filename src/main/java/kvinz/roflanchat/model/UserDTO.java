package kvinz.roflanchat.model;

import com.fasterxml.jackson.annotation.*;
import kvinz.roflanchat.domain.ResetToken;
import kvinz.roflanchat.domain.Role;
import kvinz.roflanchat.domain.VerificationToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonFilter("userFilter")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

        private Long id;

        @Size(min=2,max=30)
        @NotNull
        @NotEmpty
        private String username;

        @Size(min=2,max=30)
        @NotNull
        @NotEmpty
        private String password;

        @NotNull
        @NotEmpty
        @Email
        private String email;

        private boolean enabled;

        @JsonProperty(value = "password_confirm")
        @NotNull
        @NotEmpty
        private String matchingPassword;

        private VerificationTokenDTO token;

        private ResetTokenDTO resetToken;

        private Set<Role> roles;

}
