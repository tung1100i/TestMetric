package com.example.demoooooooo.entities;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Metric {
    private String name;
    private String type;
    private List<String> query;
}
