package com.api.models.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                       // Ստեղծում է getter/setter, equals, hashCode, toString
@NoArgsConstructor          // Դատարկ constructor
@AllArgsConstructor
public class RegisterRequest {

    private String email;
    private String password;

}
