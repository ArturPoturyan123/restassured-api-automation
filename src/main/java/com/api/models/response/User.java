package com.api.models.response;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String _id;
    private String email;
    private String createdAt;
    private String updatedAt;
    private int __v;
}