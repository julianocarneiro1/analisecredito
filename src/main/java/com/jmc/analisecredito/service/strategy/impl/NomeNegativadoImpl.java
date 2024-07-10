package com.jmc.analisecredito.service.strategy.impl;

import com.jmc.analisecredito.constante.MensagemConstante;
import com.jmc.analisecredito.domain.Proposta;
import com.jmc.analisecredito.exceptions.StrategyException;
import com.jmc.analisecredito.service.strategy.CalculoPonto;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Random;

@Order(1) //ordem da injeção de dependência no service
@Component
public class NomeNegativadoImpl implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        if(nomeNegativado()) {
            throw new StrategyException(String.format(MensagemConstante.CLIENTE_NEGATIVADO, proposta.getUsuario().getNome()));
        }

        return 100;
    }

    private boolean nomeNegativado() {
        return new Random().nextBoolean();
    }
}
