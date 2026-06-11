package io.github.matheushenriquereiter.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    @NotBlank(message = "Username must not be empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters long")
    private String username;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Email must be valid.")
    private String email;

    @NotBlank(message = "Password must not be empty")
    @Size(min = 3, max = 50, message = "Password must be between 3 and 50 characters long")
    private String password;
}
