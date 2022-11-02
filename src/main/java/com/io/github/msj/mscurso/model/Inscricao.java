package com.io.github.msj.mscurso.model;

import com.io.github.msj.mscurso.enums.SituacaoInscricao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Inscricao {

    @Column(name = "cpf_pessoa")
    private String cpf;

    @Column(name = "nota_pessoa")
    private Double nota;

    @Column(name = "situacao_pessoa")
    private SituacaoInscricao situacaoInscricao;

}
