package com.example.mytweetdetectorapp.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public String username;
    public String email;
    public String password;

    public User(String username, String email) {
        this.username= username;
        this.email = email;
    }
}
