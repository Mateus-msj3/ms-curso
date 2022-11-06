package com.io.github.msj.mscurso.service.implemetation;

import com.io.github.msj.mscurso.dto.request.CursoRequestDTO;
import com.io.github.msj.mscurso.dto.request.InscricaoRequestDTO;
import com.io.github.msj.mscurso.dto.response.*;
import com.io.github.msj.mscurso.enums.Situacao;
import com.io.github.msj.mscurso.enums.SituacaoInscricao;
import com.io.github.msj.mscurso.exception.NegocioException;
import com.io.github.msj.mscurso.model.Curso;
import com.io.github.msj.mscurso.model.Inscricao;
import com.io.github.msj.mscurso.repository.CursoRepository;
import com.io.github.msj.mscurso.repository.InscricaoRepository;
import com.io.github.msj.mscurso.service.CursoService;
import com.io.github.msj.mscurso.service.InscricaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InscricaoServiceImpl implements InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public InscricaoMensagemResponseDTO salvar(InscricaoRequestDTO inscricaoRequestDTO) {
        Inscricao inscricaoRequest = modelMapper.map(inscricaoRequestDTO, Inscricao.class);
        Inscricao inscricao = inscricaoRepository.save(inscricaoRequest);
        InscricaoMensagemResponseDTO inscricaoMensagemResponseDTO = modelMapper.map(inscricao, InscricaoMensagemResponseDTO.class);
        inscricaoMensagemResponseDTO.setMensagem("Inscrição realizada com sucesso.");
        return inscricaoMensagemResponseDTO;
    }

    @Override
    public InscricaoMensagemResponseDTO finalizar(Long idCurso) {

        Integer numeroVagasCurso = cursoService.quantidadeDeVagas();

        List<Inscricao> inscricoesEncontradas = inscricaoRepository.findByIdCurso(idCurso);

        // Se a lista encontrada é menor ou igual ao numero de vagas já adiciona todos como selecionado
        if (inscricoesEncontradas.size() <= numeroVagasCurso) {
            for (Inscricao inscricoesEncontrada : inscricoesEncontradas) {
                inscricoesEncontrada.setSituacao(Situacao.SELECIONADO);
                inscricaoRepository.save(inscricoesEncontrada);
            }
            return new InscricaoMensagemResponseDTO("Inscrição finalizada com sucesso.");
        } else {
            //Ordena a Lista com as maiores notas
            inscricoesEncontradas.sort(Comparator.comparing(Inscricao::getNota));

            for (Inscricao inscricoesEncontrada : inscricoesEncontradas) {
                if (inscricoesEncontradas.size() <= numeroVagasCurso) {
                    inscricoesEncontrada.setSituacao(Situacao.SELECIONADO);
                    inscricaoRepository.save(inscricoesEncontrada);
                }else {
                    inscricoesEncontrada.setSituacao(Situacao.NAO_SELECIONADO);
                    inscricaoRepository.save(inscricoesEncontrada);
                }


            }
            return new InscricaoMensagemResponseDTO("Inscrição finalizada com sucesso.");
        }
    }

    @Override
    public List<InscricaoCursoResponseDTO> listarPorIdCurso(Long idCurso) {
        return null;
    }

    @Override
    public List<InscricaoFinalizadaResponseDTO> inscritosFinalizados(Long idCurso) {
        return null;
    }
}
