package com.ilia.Controle.de.Ponto.API.Repository;

import com.ilia.Controle.de.Ponto.API.Entity.Relatorio;
import org.springframework.stereotype.Repository;

@Repository
public class RelatorioRepository {
    public Relatorio generateRelatorio(String mes) {
        return Relatorio.builder().build();
    }
}
