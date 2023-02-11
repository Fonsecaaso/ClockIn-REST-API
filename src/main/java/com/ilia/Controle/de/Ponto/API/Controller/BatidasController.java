package com.ilia.Controle.de.Ponto.API.Controller;

import com.ilia.Controle.de.Ponto.API.Entity.Momento;
import com.ilia.Controle.de.Ponto.API.Service.BatidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/batidas")
public class BatidasController {
    @Autowired
    private final BatidaService batidaService;

    public BatidasController(BatidaService batidaService) {
        this.batidaService = batidaService;
    }

    @PostMapping
    public ResponseEntity<?> insereBatida(@RequestBody Momento momento){
        return batidaService.insereBatida(momento);
    }
}
