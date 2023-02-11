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

@Service
public class BatidaService {
    @Autowired
    private final PontoRepository pontoRepository;

    public BatidaService(PontoRepository pontoRepository) {
        this.pontoRepository = pontoRepository;
    }

    public ResponseEntity<?> insereBatida(Momento momento) {

        if (formatoDataInvalido(momento)){
            Mensagem msg = new Mensagem("Data e hora em formato inválido");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        if (pontoRepository.countPontos(momento)>=4){
            Mensagem msg = new Mensagem("Apenas 4 horários podem ser registrados por dia");
            return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
        }

        Registro reg = pontoRepository.inserePonto(momento);
        return new ResponseEntity<>(reg, HttpStatus.CREATED);

//        msg = new Mensagem("Deve haver no mínimo 1 hora de almoço");
//        msg = new Mensagem("Sábado e domingo não são permitidos como dia de trabalho");
//        return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
//        msg = new Mensagem("Horários já registrado");
//        return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
    }

    private boolean formatoDataInvalido(Momento momento) {
        return !GenericValidator.isDate(momento.getDataHora(), "dd-MM-yyyy HH:mm:ss", true);
    }
}
