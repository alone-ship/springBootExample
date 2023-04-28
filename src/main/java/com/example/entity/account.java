package com.example.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class account implements Serializable {
    int id;
    String account;
    String email;
    String password;
    String role;
}
