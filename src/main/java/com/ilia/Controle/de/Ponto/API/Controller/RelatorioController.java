package com.ilia.Controle.de.Ponto.API.Controller;

import com.ilia.Controle.de.Ponto.API.Service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/folhas-de-ponto")
public class RelatorioController {
    @Autowired
    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping
    public ResponseEntity<?> geraRelatorioMensal(@RequestParam String mes){
        return relatorioService.geraRelatorioMensal(mes);
    }

}
