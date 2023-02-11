package com.ilia.Controle.de.Ponto.API.Repository.Mapper;

import com.ilia.Controle.de.Ponto.API.Entity.Registro;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class RegistroRowMapper implements RowMapper<Registro> {
    @Override
    public Registro mapRow(ResultSet rs, int rowNum) throws SQLException {
        Registro registro = new Registro();
        registro.setDia(rs.getString("dia"));
        registro.setHorarios(Arrays.asList(rs.getString("horarios").split(",")));

        return registro;
    }
}
