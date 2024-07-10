package com.jmc.analisecredito.service;

import com.jmc.analisecredito.domain.Proposta;
import com.jmc.analisecredito.exceptions.StrategyException;
import com.jmc.analisecredito.service.strategy.CalculoPonto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnaliseCreditoService {

    private final List<CalculoPonto> calculoPontoList;

    private final NotificacaoRabbitService notificacaoRabbitService;

    private final String exchangePropostaConcluida;

    public AnaliseCreditoService(NotificacaoRabbitService notificacaoRabbitService,
                                 List<CalculoPonto> calculoPontoList,
                                 @Value("${rabbitmq.propostaconcluida.exchange}") String exchangePropostaConcluida) {
        this.notificacaoRabbitService = notificacaoRabbitService;
        this.calculoPontoList = calculoPontoList;
        this.exchangePropostaConcluida = exchangePropostaConcluida;
    }

    public void analisar(Proposta proposta) {
        try {
            int pontos = calculoPontoList.stream().mapToInt(impl -> impl.calcular(proposta)).sum();
            boolean isAprovada = pontos > 350;
            proposta.setAprovada(isAprovada);
        } catch (StrategyException ex) {
            proposta.setAprovada(false);
            proposta.setObservacao(ex.getMessage());
        }
        notificacaoRabbitService.notificar(exchangePropostaConcluida, proposta);
    }
}
