package br.gov.ssd.datahub.application.port.out;

import br.gov.ssd.datahub.domain.model.AtendimentoNormalizado;
import br.gov.ssd.datahub.domain.model.FonteDados;

import java.util.List;

public interface AtendimentoNormalizadoRepositoryPort {

    void salvarTodos(List<AtendimentoNormalizado> atendimentos);

    long contarPorFonte(FonteDados fonte);
}
