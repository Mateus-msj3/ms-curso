package com.io.github.msj.mscurso.service;

import com.io.github.msj.mscurso.dto.request.InscricaoRequestDTO;
import com.io.github.msj.mscurso.dto.response.InscricaoCursoResponseDTO;
import com.io.github.msj.mscurso.dto.response.InscricaoFinalizadaResponseDTO;
import com.io.github.msj.mscurso.dto.response.InscricaoMensagemResponseDTO;

import java.util.List;

public interface InscricaoService {

    InscricaoMensagemResponseDTO salvar(InscricaoRequestDTO inscricaoRequestDTO);

    InscricaoMensagemResponseDTO finalizar(Long idCurso);

    List<InscricaoCursoResponseDTO> listarPorIdCurso(Long idCurso);

    List<InscricaoFinalizadaResponseDTO> inscritosFinalizados(Long idCurso);

}
