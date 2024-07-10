package com.jmc.analisecredito.service.strategy;

import com.jmc.analisecredito.domain.Proposta;

public interface CalculoPonto {

    int calcular(Proposta proposta);
}