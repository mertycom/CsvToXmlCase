package com.magarsus.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
    private String role;

    @Override
    public String toString() {

        return role;
    }
}
