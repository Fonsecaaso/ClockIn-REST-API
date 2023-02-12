package com.ilia.Controle.de.Ponto.API.Service;

import com.ilia.Controle.de.Ponto.API.Entity.Alocacao;
import com.ilia.Controle.de.Ponto.API.Entity.Mensagem;
import com.ilia.Controle.de.Ponto.API.Repository.PontoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;


@Service
public class AlocacoesService {
    @Autowired
    private final PontoRepository pontoRepository;

    public AlocacoesService(PontoRepository pontoRepository) {
        this.pontoRepository = pontoRepository;
    }

    public ResponseEntity<?> insereAlocacao(Alocacao alocacao) throws ParseException {
        if (Integer.parseInt(alocacao.getTempo())>Integer.parseInt(calcularHoras(alocacao.getDia()))){
            Mensagem msg = new Mensagem("Não pode alocar tempo maior que o tempo trabalhado no dia");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }

        pontoRepository.saveAlocacao(alocacao);
        return new ResponseEntity<>(alocacao, HttpStatus.CREATED);

    }

    private String calcularHoras(String dia) throws ParseException {
        int horas = (int)pontoRepository.horasPorDia(dia);
        return Integer.toString(horas);
    }
}
