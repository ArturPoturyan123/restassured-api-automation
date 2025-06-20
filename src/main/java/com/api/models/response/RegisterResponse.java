package com.api.models.response;

import com.api.models.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data                       // Ստեղծում է getter/setter, equals, hashCode, toString
@NoArgsConstructor          // Դատարկ constructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterResponse {


    private String message;
    private User user;
    private String token;


}
