package com.ilia.Controle.de.Ponto.API.Repository.Mapper;

import com.ilia.Controle.de.Ponto.API.Entity.Alocacao;
import com.ilia.Controle.de.Ponto.API.Entity.Registro;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class AlocacaoRowMapper implements RowMapper<Alocacao> {
    @Override
    public Alocacao mapRow(ResultSet rs, int rowNum) throws SQLException {
        Alocacao alocacao = new Alocacao();
        alocacao.setDia(rs.getString("dia"));
        alocacao.setTempo(rs.getString("tempo"));
        alocacao.setNomeProjeto(rs.getString("nomeProjeto"));

        return alocacao;
    }
}
