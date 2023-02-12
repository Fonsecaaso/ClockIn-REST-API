package com.ilia.Controle.de.Ponto.API.Service;

import com.ilia.Controle.de.Ponto.API.Entity.Momento;
import com.ilia.Controle.de.Ponto.API.Entity.Registro;
import com.ilia.Controle.de.Ponto.API.Entity.Mensagem;
import com.ilia.Controle.de.Ponto.API.Repository.PontoRepository;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;

@Service
public class BatidaService {
    @Autowired
    private final PontoRepository pontoRepository;

    public BatidaService(PontoRepository pontoRepository) {
        this.pontoRepository = pontoRepository;
    }

    public ResponseEntity<?> insereBatida(Momento momento) {

        if (campoAusente(momento)){
            Mensagem msg = new Mensagem("Campo obrigatório não informado");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        if (formatoDataInvalido(momento)){
            Mensagem msg = new Mensagem("Data e hora em formato inválido");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        if (horarioJaRegistrado(momento)){
            Mensagem msg = new Mensagem("Horário já registrado");
            return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
        }

        if (fimDeSemana(momento)){
            Mensagem  msg = new Mensagem("Sábado e domingo não são permitidos como dia de trabalho");
            return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
        }

        int pontoBatidoNoDia = pontoRepository.countPontos(momento);

        if (pontoBatidoNoDia==2){
            Registro registro = pontoRepository.buscaRegistros(momento);
            Collections.sort(registro.getHorarios());
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            try {
                long diff = format.parse(momento.getDataHora().split(" ")[1]).getTime() - format.parse(registro.getHorarios().get(1)).getTime();
                if (diff < 3600000) {
                    Mensagem msg = new Mensagem("Deve haver no mínimo 1 hora de almoço");
                    return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
                }
            } catch (ParseException ignored) {
            }
        }

        if (pontoBatidoNoDia>=4){
            Mensagem msg = new Mensagem("Apenas 4 horários podem ser registrados por dia");
            return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
        }

        Registro reg = pontoRepository.inserePonto(momento);
        return new ResponseEntity<>(reg, HttpStatus.CREATED);
    }

    private boolean fimDeSemana(Momento momento) {
        LocalDate localDate = LocalDate.parse(momento.getDataHora().split(" ")[0]);
        String dayOfWeek = localDate.getDayOfWeek().toString();
        return "SATURDAY".equalsIgnoreCase(dayOfWeek) ||
                "SUNDAY".equalsIgnoreCase(dayOfWeek);
    }

    private boolean horarioJaRegistrado(Momento momento) {
        return pontoRepository.horarioJaRegistrado(momento);
    }

    private boolean campoAusente(Momento momento) {
        return momento==null
            || momento.getDataHora()==null
            || momento.getDataHora().split(" ").length<2;
    }

    private boolean formatoDataInvalido(Momento momento) {
        return !GenericValidator.isDate(momento.getDataHora(), "yyyy-MM-dd HH:mm:ss", true);
    }
}
