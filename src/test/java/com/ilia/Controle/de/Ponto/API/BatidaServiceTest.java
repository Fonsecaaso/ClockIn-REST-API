package com.ilia.Controle.de.Ponto.API;

import com.ilia.Controle.de.Ponto.API.Entity.Mensagem;
import com.ilia.Controle.de.Ponto.API.Entity.Momento;
import com.ilia.Controle.de.Ponto.API.Service.BatidaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BatidaServiceTest {
    @InjectMocks
    private BatidaService batidaService;

    @Test
    public void QuandoDataSemHorarioRetornaBADREQUEST() {
        Momento momento = new Momento();
        String dataHora = "2023-02-12";
        momento.setDataHora(dataHora);

        Mensagem msg = new Mensagem("Campo obrigatório não informado");

        ResponseEntity<?> res = batidaService.insereBatida(momento);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertEquals(msg, res.getBody());
    }
    @Test
    public void QuandoDataComHorarioInvalidoRetornaBADREQUEST() {
        Momento momento = new Momento();
        String dataHora = "2023-02-12 08:99:00";
        momento.setDataHora(dataHora);

        Mensagem msg = new Mensagem("Data e hora em formato inválido");

        ResponseEntity<?> res = batidaService.insereBatida(momento);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertEquals(msg, res.getBody());
    }
    @Test
    public void QuandoFimDeSemanaRetornaFORBIDDEN() {
        Momento momento = new Momento();
        String dataHora = "2023-02-12 08:00:00";
        momento.setDataHora(dataHora);

        Mensagem msg = new Mensagem("Sábado e domingo não são permitidos como dia de trabalho");

        ResponseEntity<?> res = batidaService.insereBatida(momento);

        assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
        assertEquals(msg, res.getBody());
    }
}
