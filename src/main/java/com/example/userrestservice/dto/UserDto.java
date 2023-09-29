package com.example.userrestservice.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  private String phoneNumber;

  private LocalDate birthDate;
}
