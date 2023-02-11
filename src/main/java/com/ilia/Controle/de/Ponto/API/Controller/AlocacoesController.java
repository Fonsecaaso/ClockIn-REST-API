package com.ilia.Controle.de.Ponto.API.Controller;

import com.ilia.Controle.de.Ponto.API.Entity.Alocacao;
import com.ilia.Controle.de.Ponto.API.Service.AlocacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/v1/alocacoes")
public class AlocacoesController {
    @Autowired
    private final AlocacoesService alocacoesService;

    public AlocacoesController(AlocacoesService alocacoesService) {
        this.alocacoesService = alocacoesService;
    }

    @PostMapping
    public ResponseEntity<?> insereAlocacao(@RequestBody Alocacao alocacao) throws ParseException {
        return alocacoesService.insereAlocacao(alocacao);
    }
}
