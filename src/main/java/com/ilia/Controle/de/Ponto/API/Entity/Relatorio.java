package com.ilia.Controle.de.Ponto.API.Entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Relatorio {
    private String mes;
    private String horasTrabalhadas;
    private String horasExcedentes;
    private String horasDevidas;
    private List<Registro> registros;
    private List<Alocacao> alocacoes;
}
