package com.ilia.Controle.de.Ponto.API.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registro {
    private String dia;
    private List<String> horarios;
}
