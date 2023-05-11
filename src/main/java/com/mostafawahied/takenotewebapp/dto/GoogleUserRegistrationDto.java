package com.mostafawahied.takenotewebapp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoogleUserRegistrationDto {
    private String email;
    private String givenName;
    private String familyName;
    private String pictureUrl;
}
