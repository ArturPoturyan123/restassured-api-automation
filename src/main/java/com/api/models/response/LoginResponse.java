package com.api.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;





@Data                       // Ստեղծում է getter/setter, equals, hashCode, toString
@NoArgsConstructor          // Դատարկ constructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String message;
    private User user;

}
