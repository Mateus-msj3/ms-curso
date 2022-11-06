package com.io.github.msj.mscurso.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InscricaoFinalizadaResponseDTO {

    private Integer cpf;

    private BigDecimal nota;

    private String situacao;

}

