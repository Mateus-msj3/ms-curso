package com.io.github.msj.mscurso.service;

import com.io.github.msj.mscurso.dto.request.CursoRequestDTO;
import com.io.github.msj.mscurso.dto.response.CursoResponseDTO;
import com.io.github.msj.mscurso.dto.response.CursoSalvoResponseDTO;

import java.util.List;

public interface CursoService {

    List<CursoResponseDTO> listarTodos();

    CursoSalvoResponseDTO salvar(CursoRequestDTO cursoRequestDTO);

}