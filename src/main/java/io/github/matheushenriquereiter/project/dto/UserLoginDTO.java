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
public class UserLoginDTO {
    @NotBlank(message = "User email must not be empty")
    @Email(message = "User email must be valid.")
    private String email;

    @NotBlank(message = "User password must not be empty")
    @Size(min = 3, max = 50, message = "User password must be between 3 and 50 characters long")
    private String password;
}