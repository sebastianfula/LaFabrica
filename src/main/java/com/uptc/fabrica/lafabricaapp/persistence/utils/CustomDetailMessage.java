package com.uptc.fabrica.lafabricaapp.persistence.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomDetailMessage {
    private int code;
    private String message;
    private ArrayList data;
}
