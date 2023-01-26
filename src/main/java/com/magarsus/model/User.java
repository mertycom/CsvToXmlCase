package com.magarsus.model;

import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private List<Role> roles;

    public void addRole(Role role){
        if (this.roles == null)
            this.roles=new ArrayList<>();
        this.roles.add(role);
    }

    public List<Role> getRoles() {
        return roles;
    }
}
