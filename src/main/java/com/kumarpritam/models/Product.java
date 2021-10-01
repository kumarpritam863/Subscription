package com.kumarpritam.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @NonNull
    private Integer id;
    @NonNull
    private String name;
    private String description;
}
