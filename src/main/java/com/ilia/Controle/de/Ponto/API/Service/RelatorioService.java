package com.ilia.Controle.de.Ponto.API.Service;

import com.ilia.Controle.de.Ponto.API.Entity.Alocacao;
import com.ilia.Controle.de.Ponto.API.Entity.Mensagem;
import com.ilia.Controle.de.Ponto.API.Entity.Registro;
import com.ilia.Controle.de.Ponto.API.Entity.Relatorio;
import com.ilia.Controle.de.Ponto.API.Repository.PontoRepository;
import com.ilia.Controle.de.Ponto.API.Repository.RelatorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RelatorioService {
    @Autowired
    private final RelatorioRepository relatorioRepository;
    @Autowired
    private final PontoRepository pontoRepository;

    public RelatorioService(RelatorioRepository relatorioRepository, PontoRepository pontoRepository) {
        this.relatorioRepository = relatorioRepository;
        this.pontoRepository = pontoRepository;
    }

    public ResponseEntity<?> geraRelatorioMensal(String mes) {

        List<Registro> registros = pontoRepository.getRegistros(mes);
        List<Alocacao> alocacoes = pontoRepository.getAlocacoes(mes);

        if (registros.size() == 0) {
            Mensagem msg = new Mensagem("Relatório não encontrado");
            return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
        }

        int horasTrabalhadas = pontoRepository.getHorasTrabalhadas(mes);

        AtomicInteger horasRegistradas = new AtomicInteger();

        registros.forEach(registro -> {
            Collections.sort(registro.getHorarios());
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            if (registro.getHorarios().size()>1){
                try {
                    Date date1 = format.parse(registro.getHorarios().get(0));
                    Date date2 = format.parse(registro.getHorarios().get(1));
                    horasRegistradas.addAndGet(date2.getHours() - date1.getHours());
                } catch (ParseException ignored) {
                }
            }
            if (registro.getHorarios().size()>3){
                try {
                    Date date3 = format.parse(registro.getHorarios().get(2));
                    Date date4 = format.parse(registro.getHorarios().get(3));
                    horasRegistradas.addAndGet(date4.getHours() - date3.getHours());
                } catch (ParseException ignored) {
                }
            }
        });

        int horasExcedentes = 0;
        int horasDevidas = 0;

        if (horasRegistradas.intValue()<horasTrabalhadas){
            horasDevidas = horasTrabalhadas - horasRegistradas.intValue();
        }else{
            horasExcedentes = horasRegistradas.intValue() - horasTrabalhadas;
        }

        Relatorio relatorio = Relatorio.builder()
                .mes(mes)
                .registros(registros)
                .horasTrabalhadas(Integer.toString(horasTrabalhadas))
                .horasExcedentes(Integer.toString(horasExcedentes))
                .horasDevidas(Integer.toString(horasDevidas))
                .alocacoes(alocacoes)
                .build();

        return new ResponseEntity<>(relatorio, HttpStatus.CREATED);

    }

}
