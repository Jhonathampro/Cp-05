package com.github.jhonathampro.api_registro_jhonatham_559114_m1.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "registroId")
@Entity
@Table(name = "tb_registro_meteorologico")
public class RegistroMeteorologico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registro_id")
    private Long registroId;

    @Column(name = "estacao_nome", nullable = false, length = 100)
    private String estacaoNome;

    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistro;

    @Column(name = "temperatura", nullable = false, precision = 5, scale = 2)
    private BigDecimal temperatura;

    @Column(name = "percentual_umidade", nullable = false, precision = 5, scale = 2)
    private BigDecimal percentualUmidade;

    @Column(name = "precipitacao", nullable = false, precision = 6, scale = 2)
    private BigDecimal precipitacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "condicao_climatica", nullable = false, length = 20)
    private CondicaoClimatica condicaoClimatica;
}
