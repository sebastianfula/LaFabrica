package com.uptc.fabrica.lafabricaapp.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomDetailMessage<T> {
    private int code;
    private String message;
    private List<T> data;
}
