package com.io.github.msj.mscurso.service.implemetation;

import com.io.github.msj.mscurso.dto.request.CursoRequestDTO;
import com.io.github.msj.mscurso.dto.response.CursoResponseDTO;
import com.io.github.msj.mscurso.dto.response.CursoSalvoResponseDTO;
import com.io.github.msj.mscurso.enums.SituacaoInscricao;
import com.io.github.msj.mscurso.model.Curso;
import com.io.github.msj.mscurso.repository.CursoRepository;
import com.io.github.msj.mscurso.service.CursoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CursoResponseDTO> listarTodos() {
        List<Curso> cursos = cursoRepository.findAll();
        return cursos.stream()
                .map(curso -> modelMapper.map(curso, CursoResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CursoSalvoResponseDTO salvar(CursoRequestDTO cursoRequestDTO) {
        Curso cursoRequest = modelMapper.map(cursoRequestDTO, Curso.class);
        defineSituacaoInscricao(cursoRequest);
        Curso curso = cursoRepository.save(cursoRequest);
        CursoSalvoResponseDTO responseDTO = modelMapper.map(curso, CursoSalvoResponseDTO.class);
        responseDTO.setMensagem("Curso salvo com sucesso!");
        return responseDTO;
    }

    private void defineSituacaoInscricao(Curso curso) {
        if (curso.getSituacaoInscricao() == null) {
            curso.setSituacaoInscricao(SituacaoInscricao.EM_ANDAMENTO);
        }
    }

}
