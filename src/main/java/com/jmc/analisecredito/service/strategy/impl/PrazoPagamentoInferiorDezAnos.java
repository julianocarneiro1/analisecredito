package com.jmc.analisecredito.service.strategy.impl;

import com.jmc.analisecredito.domain.Proposta;
import com.jmc.analisecredito.service.strategy.CalculoPonto;

public class PrazoPagamentoInferiorDezAnos implements CalculoPonto {

    @Override
    public int calcular(Proposta proposta) {
        return proposta.getPrazoPagamento() < 120 ? 80 : 0;
    }
}
