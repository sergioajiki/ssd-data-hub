package br.gov.ssd.datahub.application.usecase;

import br.gov.ssd.datahub.application.port.in.NormalizarFiocruzUseCase;
import br.gov.ssd.datahub.application.port.out.AtendimentoNormalizadoRepositoryPort;
import br.gov.ssd.datahub.application.port.out.FiocruzRepositoryPort;
import br.gov.ssd.datahub.domain.model.AtendimentoFiocruz;
import br.gov.ssd.datahub.domain.model.AtendimentoNormalizado;
import br.gov.ssd.datahub.domain.model.FonteDados;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NormalizarFiocruzUseCaseImpl implements NormalizarFiocruzUseCase {

    private final FiocruzRepositoryPort fiocruzPort;
    private final AtendimentoNormalizadoRepositoryPort normalizadoPort;

    @Override
    public long executar() {
        List<AtendimentoFiocruz> atendimentos = fiocruzPort.buscarTodos();
        log.info("Fiocruz: normalizando {} registros", atendimentos.size());

        List<AtendimentoNormalizado> normalizados = atendimentos.stream()
                .map(this::normalizar)
                .toList();

        normalizadoPort.salvarTodos(normalizados);
        return normalizadoPort.contarPorFonte(FonteDados.FIOCRUZ);
    }

    private AtendimentoNormalizado normalizar(AtendimentoFiocruz a) {
        String idOrigem = a.getCpfPaciente() + "|" + a.getDataAgendamento() + "|" + a.getHoraAtendimento();

        Map<String, Object> extras = new HashMap<>();
        if (a.getRacaPaciente() != null)           extras.put("racaPaciente", a.getRacaPaciente());
        if (a.getEtniaPaciente() != null)          extras.put("etniaPaciente", a.getEtniaPaciente());
        if (a.getEstabelecimento() != null)        extras.put("estabelecimento", a.getEstabelecimento());
        if (a.getCboMedico() != null)              extras.put("cboMedico", a.getCboMedico());
        if (a.getCpfMedico() != null)              extras.put("cpfMedico", a.getCpfMedico());
        if (a.getClassificacaoConclusao() != null) extras.put("classificacaoConclusao", a.getClassificacaoConclusao());
        if (a.getHoraAtendimento() != null)        extras.put("horaAtendimento", a.getHoraAtendimento().toString());
        if (a.getTelefone() != null)               extras.put("telefone", a.getTelefone());

        return AtendimentoNormalizado.builder()
                .fonte(FonteDados.FIOCRUZ)
                .idOrigem(idOrigem)
                .cpf(a.getCpfPaciente())
                .cns(a.getCnsPaciente())
                .nomePaciente(a.getNomePaciente())
                .dataNascimento(a.getDataNascimento())
                .sexo(null)
                .municipio(a.getMunicipio())
                .especialidade(a.getEspecialidade())
                .cid10Principal(a.getCidConsulta())
                .cid10Secundario(null)
                .dataEvento(a.getDataSolicitacao())
                .status(a.getStatusConsulta())
                .tipoServico(a.getTipoServico())
                .sourceFile(a.getSourceFile())
                .dadosExtras(extras)
                .build();
    }
}
