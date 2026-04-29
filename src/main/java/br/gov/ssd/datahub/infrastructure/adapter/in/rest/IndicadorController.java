package br.gov.ssd.datahub.infrastructure.adapter.in.rest;

import br.gov.ssd.datahub.infrastructure.adapter.in.rest.dto.ItemIndicadorDTO;
import br.gov.ssd.datahub.infrastructure.adapter.out.persistence.AtendimentoNormalizadoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/indicadores/atendimentos")
@RequiredArgsConstructor
public class IndicadorController {

    private final AtendimentoNormalizadoJpaRepository repository;

    @GetMapping("/por-periodo")
    public ResponseEntity<List<ItemIndicadorDTO>> porPeriodo(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) String fonte) {

        List<Object[]> rows = repository.countByFonteNoPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(toDTO(rows));
    }

    @GetMapping("/por-municipio")
    public ResponseEntity<List<ItemIndicadorDTO>> porMunicipio(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) String fonte) {

        List<Object[]> rows = repository.countByMunicipio(fonte, dataInicio, dataFim);
        return ResponseEntity.ok(toDTO(rows));
    }

    @GetMapping("/por-especialidade")
    public ResponseEntity<List<ItemIndicadorDTO>> porEspecialidade(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) String fonte) {

        List<Object[]> rows = repository.countByEspecialidade(fonte, dataInicio, dataFim);
        return ResponseEntity.ok(toDTO(rows));
    }

    @GetMapping("/por-cid10")
    public ResponseEntity<List<ItemIndicadorDTO>> porCid10(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) String fonte) {

        List<Object[]> rows = repository.countByCid10(fonte, dataInicio, dataFim);
        return ResponseEntity.ok(toDTO(rows));
    }

    @GetMapping("/perfil-demografico")
    public ResponseEntity<List<ItemIndicadorDTO>> perfilDemografico(
            @RequestParam(required = false) String fonte) {

        List<Object[]> rows = repository.countBySexo(fonte);
        return ResponseEntity.ok(toDTO(rows));
    }

    private List<ItemIndicadorDTO> toDTO(List<Object[]> rows) {
        return rows.stream()
                .map(r -> new ItemIndicadorDTO(
                        r[0] != null ? r[0].toString() : "Não informado",
                        r[1] != null ? r[1].toString() : null,
                        ((Number) r[2]).longValue()))
                .toList();
    }
}
