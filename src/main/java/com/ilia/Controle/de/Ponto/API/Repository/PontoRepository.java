package com.ilia.Controle.de.Ponto.API.Repository;

import com.ilia.Controle.de.Ponto.API.Entity.Alocacao;
import com.ilia.Controle.de.Ponto.API.Entity.Momento;
import com.ilia.Controle.de.Ponto.API.Entity.Registro;
import com.ilia.Controle.de.Ponto.API.Repository.Mapper.RegistroRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class PontoRepository {
    @Autowired
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PontoRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public int countPontos(Momento momento){
        String[] momentos = momento.getDataHora().split(" ");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("dia", momentos[0]);
        String sql = "select count(1) from ponto where dia = :dia" ;
        return namedParameterJdbcTemplate.queryForObject(sql,params, Integer.class);
    }
    public Registro inserePonto(Momento momento) {
        String[] momentos = momento.getDataHora().split(" ");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("dia", momentos[0]);
        params.addValue("hora", momentos[1]);
        String insert = "INSERT INTO ponto(dia,horario) values(:dia,:hora)";
        namedParameterJdbcTemplate.update(insert, params);

        String select = "SELECT horario from ponto where dia = :dia";
        List<String> horarios = namedParameterJdbcTemplate.queryForList(select, params, String.class);
        return new Registro(momentos[0], horarios);
    }

    public int horasPorDia(String dia) throws ParseException {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("dia", dia);
        String select = "SELECT horario from ponto where dia = :dia";
        List<String> horarios = namedParameterJdbcTemplate.queryForList(select, params, String.class);

        Collections.sort(horarios);

        int difference = 0;

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = format.parse(horarios.get(0));
        Date date2 = format.parse(horarios.get(1));
        Date date3 = format.parse(horarios.get(2));
        Date date4 = format.parse(horarios.get(3));

        difference += date2.getHours() - date1.getHours();
        difference += date4.getHours() - date3.getHours();

        return difference;
    }

    public void saveAlocacao(Alocacao alocacao) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("dia", alocacao.getDia());
        params.addValue("tempo", alocacao.getTempo());
        params.addValue("nomeProjeto", alocacao.getNomeProjeto());
        String sql = "insert into alocacao(dia, tempo, nomeProjeto) values(:dia, :tempo, :nomeProjeto)";

        namedParameterJdbcTemplate.update(sql,params);
    }

    public List<Registro> getRegistros(String mes) {
        String sql = "select dia, GROUP_CONCAT(horario SEPARATOR ',') as horarios from ponto where dia like :mes group by dia";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("mes", "%"+mes);

        return namedParameterJdbcTemplate.query(sql,params, new RegistroRowMapper());
    }

    public int getHorasTrabalhadas(String mes) {
        return jdbcTemplate.queryForObject("select sum(tempo) from alocacao where dia like '%"+mes+"'", Integer.class);
    }
}
