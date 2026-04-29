package br.gov.ssd.datahub.infrastructure.adapter.out.persistence;

import br.gov.ssd.datahub.application.port.out.AtendimentoNormalizadoRepositoryPort;
import br.gov.ssd.datahub.domain.model.AtendimentoNormalizado;
import br.gov.ssd.datahub.domain.model.FonteDados;
import br.gov.ssd.datahub.infrastructure.adapter.out.persistence.entity.AtendimentoNormalizadoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class AtendimentoNormalizadoJpaAdapter implements AtendimentoNormalizadoRepositoryPort {

    private final AtendimentoNormalizadoJpaRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public void salvarTodos(List<AtendimentoNormalizado> atendimentos) {
        if (atendimentos.isEmpty()) return;

        String fonte = atendimentos.get(0).getFonte().name();
        Set<String> existentes = repository.findAllIdOrigemByFonte(fonte);

        List<AtendimentoNormalizadoEntity> novas = atendimentos.stream()
                .filter(a -> a.getIdOrigem() != null && !existentes.contains(a.getIdOrigem()))
                .map(this::toEntity)
                .toList();

        int duplicados = atendimentos.size() - novas.size();
        if (duplicados > 0) {
            log.info("Normalizado [{}]: {} registros já existentes ignorados", fonte, duplicados);
        }

        repository.saveAll(novas);
        log.info("Normalizado [{}]: {} registros persistidos", fonte, novas.size());
    }

    @Override
    public long contarPorFonte(FonteDados fonte) {
        return repository.countByFonte(fonte.name());
    }

    private AtendimentoNormalizadoEntity toEntity(AtendimentoNormalizado a) {
        String dadosExtrasJson = null;
        if (a.getDadosExtras() != null && !a.getDadosExtras().isEmpty()) {
            try {
                dadosExtrasJson = objectMapper.writeValueAsString(a.getDadosExtras());
            } catch (Exception e) {
                log.warn("Erro ao serializar dados_extras: {}", e.getMessage());
            }
        }

        return AtendimentoNormalizadoEntity.builder()
                .fonte(a.getFonte().name())
                .idOrigem(a.getIdOrigem())
                .cpf(a.getCpf())
                .cns(a.getCns())
                .nomePaciente(a.getNomePaciente())
                .dataNascimento(a.getDataNascimento())
                .sexo(a.getSexo())
                .municipio(a.getMunicipio())
                .especialidade(a.getEspecialidade())
                .cid10Principal(a.getCid10Principal())
                .cid10Secundario(a.getCid10Secundario())
                .dataEvento(a.getDataEvento())
                .status(a.getStatus())
                .tipoServico(a.getTipoServico())
                .sourceFile(a.getSourceFile())
                .dadosExtras(dadosExtrasJson)
                .build();
    }
}
