package com.github.jhonathampro.api_registro_jhonatham_559114_m1.dto;

import com.github.jhonathampro.api_registro_jhonatham_559114_m1.entities.CondicaoClimatica;
import com.github.jhonathampro.api_registro_jhonatham_559114_m1.entities.RegistroMeteorologico;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
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
public class RegistroMeteorologicoRequestDTO {

    @NotBlank(message = "O campo nome da estação é obrigatório")
    @Size(min = 3, max = 100, message = "O nome da estação deve ter entre 3 e 100 caracteres")
    @Schema(example = "Estação Meteorológica Central")
    private String estacaoNome;

    @NotNull(message = "O campo data do registro é obrigatório")
    @PastOrPresent(message = "A data do registro não pode ser posterior à data atual")
    private LocalDate dataRegistro;

    @NotNull(message = "O campo temperatura é obrigatório")
    private BigDecimal temperatura;

    @NotNull(message = "O campo percentual de umidade é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "O percentual de umidade deve ser no mínimo 0")
    @DecimalMax(value = "100.0", inclusive = true, message = "O percentual de umidade deve ser no máximo 100")
    private BigDecimal percentualUmidade;

    @NotNull(message = "O campo precipitação é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "A precipitação deve ser maior ou igual a zero")
    private BigDecimal precipitacao;

    @NotNull(message = "O campo condição climática é obrigatório")
    private CondicaoClimatica condicaoClimatica;

    public RegistroMeteorologicoRequestDTO(RegistroMeteorologico entity) {
        estacaoNome = entity.getEstacaoNome();
        dataRegistro = entity.getDataRegistro();
        temperatura = entity.getTemperatura();
        percentualUmidade = entity.getPercentualUmidade();
        precipitacao = entity.getPrecipitacao();
        condicaoClimatica = entity.getCondicaoClimatica();
    }
}