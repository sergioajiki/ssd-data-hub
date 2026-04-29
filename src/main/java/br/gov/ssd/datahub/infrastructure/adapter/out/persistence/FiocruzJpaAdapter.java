package br.gov.ssd.datahub.infrastructure.adapter.out.persistence;

import br.gov.ssd.datahub.application.port.out.FiocruzRepositoryPort;
import br.gov.ssd.datahub.domain.model.AtendimentoFiocruz;
import br.gov.ssd.datahub.infrastructure.adapter.out.persistence.entity.AtendimentoFiocruzEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class FiocruzJpaAdapter implements FiocruzRepositoryPort {

    private final FiocruzJpaRepository repository;

    @Override
    public void salvarTodos(List<AtendimentoFiocruz> atendimentos) {
        Set<String> existentesNoBanco = repository.findAllChavesDeduplicacao();

        Map<String, AtendimentoFiocruz> unicosNoBatch = new LinkedHashMap<>();
        for (AtendimentoFiocruz a : atendimentos) {
            String chave = gerarChave(a);
            if (chave != null) {
                unicosNoBatch.putIfAbsent(chave, a);
            }
        }

        List<AtendimentoFiocruzEntity> novas = unicosNoBatch.entrySet().stream()
                .filter(e -> !existentesNoBanco.contains(e.getKey()))
                .map(e -> toEntity(e.getValue()))
                .toList();

        int duplicados = atendimentos.size() - novas.size();
        if (duplicados > 0) {
            log.info("Fiocruz: {} registros duplicados ignorados", duplicados);
        }

        repository.saveAll(novas);
        log.info("Fiocruz: {} registros persistidos", novas.size());
    }

    private String gerarChave(AtendimentoFiocruz a) {
        if (a.getCpfPaciente() == null || a.getDataAgendamento() == null || a.getHoraAtendimento() == null) {
            return null;
        }
        return a.getCpfPaciente() + "|" + a.getDataAgendamento() + "|" + a.getHoraAtendimento();
    }

    private AtendimentoFiocruzEntity toEntity(AtendimentoFiocruz a) {
        return AtendimentoFiocruzEntity.builder()
                .chaveDeduplicacao(gerarChave(a))
                .dataSolicitacao(a.getDataSolicitacao())
                .tipoServico(a.getTipoServico())
                .dataAgendamento(a.getDataAgendamento())
                .horaAtendimento(a.getHoraAtendimento())
                .estabelecimento(a.getEstabelecimento())
                .especialidade(a.getEspecialidade())
                .especialidadeMedico(a.getEspecialidadeMedico())
                .cpfMedico(a.getCpfMedico())
                .cboMedico(a.getCboMedico())
                .statusConsulta(a.getStatusConsulta())
                .municipio(a.getMunicipio())
                .cpfPaciente(a.getCpfPaciente())
                .nomePaciente(a.getNomePaciente())
                .cnsPaciente(a.getCnsPaciente())
                .racaPaciente(a.getRacaPaciente())
                .etniaPaciente(a.getEtniaPaciente())
                .dataNascimento(a.getDataNascimento())
                .cidConsulta(a.getCidConsulta())
                .telefone(a.getTelefone())
                .tipoZona(a.getTipoZona())
                .enderecoCompleto(a.getEnderecoCompleto())
                .rua(a.getRua())
                .cep(a.getCep())
                .numImovel(a.getNumImovel())
                .bairro(a.getBairro())
                .complemento(a.getComplemento())
                .classificacaoConclusao(a.getClassificacaoConclusao())
                .descricaoEndereco(a.getDescricaoEndereco())
                .sourceFile(a.getSourceFile())
                .build();
    }
}
