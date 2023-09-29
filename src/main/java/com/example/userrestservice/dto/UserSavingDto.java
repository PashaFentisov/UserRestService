package com.example.userrestservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserSavingDto {

  @NotBlank(message = "User firstName can`t be null or empty")
  private String firstName;
  @NotBlank(message = "User lastName can`t be null or empty")
  private String lastName;
  @Email(message = "Wrong email pattern")
  private String email;

  private String phoneNumber;
  @NotNull
  private LocalDate birthDate;
}
