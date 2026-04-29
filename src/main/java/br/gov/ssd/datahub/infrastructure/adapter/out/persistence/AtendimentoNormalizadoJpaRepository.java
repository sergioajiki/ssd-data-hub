package br.gov.ssd.datahub.infrastructure.adapter.out.persistence;

import br.gov.ssd.datahub.infrastructure.adapter.out.persistence.entity.AtendimentoNormalizadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface AtendimentoNormalizadoJpaRepository extends JpaRepository<AtendimentoNormalizadoEntity, Long> {

    @Query("SELECT a.idOrigem FROM AtendimentoNormalizadoEntity a WHERE a.fonte = :fonte")
    Set<String> findAllIdOrigemByFonte(@Param("fonte") String fonte);

    long countByFonte(String fonte);

    @Query("""
        SELECT a.municipio, a.fonte, COUNT(a) FROM AtendimentoNormalizadoEntity a
        WHERE a.municipio IS NOT NULL
          AND (:fonte IS NULL OR a.fonte = :fonte)
          AND (:dataInicio IS NULL OR a.dataEvento >= :dataInicio)
          AND (:dataFim IS NULL OR a.dataEvento <= :dataFim)
        GROUP BY a.municipio, a.fonte
        ORDER BY COUNT(a) DESC
        """)
    List<Object[]> countByMunicipio(@Param("fonte") String fonte,
                                    @Param("dataInicio") LocalDate dataInicio,
                                    @Param("dataFim") LocalDate dataFim);

    @Query("""
        SELECT a.especialidade, a.fonte, COUNT(a) FROM AtendimentoNormalizadoEntity a
        WHERE a.especialidade IS NOT NULL
          AND (:fonte IS NULL OR a.fonte = :fonte)
          AND (:dataInicio IS NULL OR a.dataEvento >= :dataInicio)
          AND (:dataFim IS NULL OR a.dataEvento <= :dataFim)
        GROUP BY a.especialidade, a.fonte
        ORDER BY COUNT(a) DESC
        """)
    List<Object[]> countByEspecialidade(@Param("fonte") String fonte,
                                        @Param("dataInicio") LocalDate dataInicio,
                                        @Param("dataFim") LocalDate dataFim);

    @Query("""
        SELECT a.cid10Principal, a.fonte, COUNT(a) FROM AtendimentoNormalizadoEntity a
        WHERE a.cid10Principal IS NOT NULL
          AND (:fonte IS NULL OR a.fonte = :fonte)
          AND (:dataInicio IS NULL OR a.dataEvento >= :dataInicio)
          AND (:dataFim IS NULL OR a.dataEvento <= :dataFim)
        GROUP BY a.cid10Principal, a.fonte
        ORDER BY COUNT(a) DESC
        """)
    List<Object[]> countByCid10(@Param("fonte") String fonte,
                                @Param("dataInicio") LocalDate dataInicio,
                                @Param("dataFim") LocalDate dataFim);

    @Query("""
        SELECT a.sexo, a.fonte, COUNT(a) FROM AtendimentoNormalizadoEntity a
        WHERE a.sexo IS NOT NULL
          AND (:fonte IS NULL OR a.fonte = :fonte)
        GROUP BY a.sexo, a.fonte
        """)
    List<Object[]> countBySexo(@Param("fonte") String fonte);

    @Query("""
        SELECT a.fonte, COUNT(a) FROM AtendimentoNormalizadoEntity a
        WHERE (:dataInicio IS NULL OR a.dataEvento >= :dataInicio)
          AND (:dataFim IS NULL OR a.dataEvento <= :dataFim)
        GROUP BY a.fonte
        """)
    List<Object[]> countByFonteNoPeriodo(@Param("dataInicio") LocalDate dataInicio,
                                         @Param("dataFim") LocalDate dataFim);
}
