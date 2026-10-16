package com.github.jhonathampro.api_registro_jhonatham_559114_m1.dto;

import com.github.jhonathampro.api_registro_jhonatham_559114_m1.entities.CondicaoClimatica;
import com.github.jhonathampro.api_registro_jhonatham_559114_m1.entities.RegistroMeteorologico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistroMeteorologicoResponseDTO {

    private Long registroId;
    private String estacaoNome;
    private LocalDate dataRegistro;
    private BigDecimal temperatura;
    private BigDecimal percentualUmidade;
    private BigDecimal precipitacao;
    private CondicaoClimatica condicaoClimatica;

    public RegistroMeteorologicoResponseDTO(RegistroMeteorologico entity) {
        registroId = entity.getRegistroId();
        estacaoNome = entity.getEstacaoNome();
        dataRegistro = entity.getDataRegistro();
        temperatura = entity.getTemperatura();
        percentualUmidade = entity.getPercentualUmidade();
        precipitacao = entity.getPrecipitacao();
        condicaoClimatica = entity.getCondicaoClimatica();
    }
}