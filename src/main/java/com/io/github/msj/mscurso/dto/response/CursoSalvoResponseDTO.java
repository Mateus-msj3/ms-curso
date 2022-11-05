package com.io.github.msj.mscurso.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursoSalvoResponseDTO {

    private Integer idCurso;

    private String mensagem;
}
