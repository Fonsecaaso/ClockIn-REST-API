package com.ilia.Controle.de.Ponto.API;

import com.ilia.Controle.de.Ponto.API.Entity.Mensagem;
import com.ilia.Controle.de.Ponto.API.Entity.Momento;
import com.ilia.Controle.de.Ponto.API.Entity.Registro;
import com.ilia.Controle.de.Ponto.API.Repository.PontoRepository;
import com.ilia.Controle.de.Ponto.API.Service.BatidaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BatidaServiceTest {
    @InjectMocks
    private BatidaService batidaService;

    @Mock
    PontoRepository pontoRepositoryMock;

    @Test
    public void QuandoDataSemHorarioRetornaBADREQUEST() {
        //arrange
        Momento momento = new Momento();
        momento.setDataHora("2023-02-12");

        //act
        ResponseEntity<?> res = batidaService.insereBatida(momento);

        //assert
        Mensagem msg = new Mensagem("Campo obrigatório não informado");
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertEquals(msg, res.getBody());
    }
    @Test
    public void QuandoDataComHorarioInvalidoRetornaBADREQUEST() {
        //arrange
        Momento momento = new Momento();
        momento.setDataHora("2023-02-12 08:99:00");

        //act
        ResponseEntity<?> res = batidaService.insereBatida(momento);

        //assert
        Mensagem msg = new Mensagem("Data e hora em formato inválido");
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertEquals(msg, res.getBody());
    }
    @Test
    public void QuandoFimDeSemanaRetornaFORBIDDEN() {
        //arrange
        Momento momento = new Momento();
        momento.setDataHora("2023-02-12 08:00:00");

        //act
        ResponseEntity<?> res = batidaService.insereBatida(momento);

        //assert
        Mensagem msg = new Mensagem("Sábado e domingo não são permitidos como dia de trabalho");
        assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
        assertEquals(msg, res.getBody());
    }
    @Test
    public void QuandoUltrapassa4RetornaFORBIDDEN() {
        //arrange
        when(pontoRepositoryMock.horarioJaRegistrado(any())).thenReturn(false);
        when(pontoRepositoryMock.countPontos(any())).thenReturn(4);

        Momento momento = new Momento();
        momento.setDataHora("2022-03-11 19:00:00");

        //act
        ResponseEntity<?> res = batidaService.insereBatida(momento);

        //assert
        Mensagem msg = new Mensagem("Apenas 4 horários podem ser registrados por dia");
        assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
        assertEquals(msg, res.getBody());
    }
    @Test
    public void QuandoMenos1HrAlmocoRetornaFORBIDDEN() {
        //arrange
        Registro registro = new Registro();
        registro.setHorarios(Arrays.asList("09:00:00","12:00:00"));

        when(pontoRepositoryMock.horarioJaRegistrado(any())).thenReturn(false);
        when(pontoRepositoryMock.countPontos(any())).thenReturn(2);
        when(pontoRepositoryMock.buscaRegistros(any())).thenReturn(registro);

        Momento momento = new Momento();
        momento.setDataHora("2022-03-11 12:50:00");

        //act
        ResponseEntity<?> res = batidaService.insereBatida(momento);

        //assert
        Mensagem msg = new Mensagem("Deve haver no mínimo 1 hora de almoço");
        assertEquals(HttpStatus.FORBIDDEN, res.getStatusCode());
        assertEquals(msg, res.getBody());
    }
}
