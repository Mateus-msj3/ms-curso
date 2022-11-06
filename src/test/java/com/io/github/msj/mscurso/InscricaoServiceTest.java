package com.io.github.msj.mscurso;

import com.io.github.msj.mscurso.dto.request.InscricaoRequestDTO;
import com.io.github.msj.mscurso.dto.response.InscricaoCursoResponseDTO;
import com.io.github.msj.mscurso.dto.response.InscricaoFinalizadaResponseDTO;
import com.io.github.msj.mscurso.dto.response.InscricaoMensagemResponseDTO;
import com.io.github.msj.mscurso.enums.Situacao;
import com.io.github.msj.mscurso.model.Inscricao;
import com.io.github.msj.mscurso.repository.InscricaoRepository;
import com.io.github.msj.mscurso.service.implemetation.CursoServiceImpl;
import com.io.github.msj.mscurso.service.implemetation.InscricaoServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class InscricaoServiceTest {

    @InjectMocks
    InscricaoServiceImpl inscricaoService;

    @Mock
    InscricaoRepository inscricaoRepository;

    @Mock
    CursoServiceImpl cursoService;

    @Mock
    ModelMapper modelMapper;

    private final Long ID = 1L;

    @Test
    @DisplayName("Deve salvar uma inscrição")
    public void salvar(){

        var inscricaoRequestDTO = InscricaoRequestDTO.builder().idCurso(1).cpf("11122233344").nota(new BigDecimal(10)).build();
        var inscricao = Inscricao.builder().idCurso(1).cpf("11122233344").nota(new BigDecimal(10)).build();
        var inscricaoSalva = Inscricao.builder().id(ID).idCurso(1).cpf("11122233344").nota(new BigDecimal(10)).build();

        when(modelMapper.map(any(), any())).thenReturn(inscricao);
        when(inscricaoRepository.save(inscricao)).thenReturn(inscricaoSalva);
        InscricaoMensagemResponseDTO retorno = inscricaoService.salvar(inscricaoRequestDTO);

        verify(modelMapper, times(1)).map(any(), any());
        verify(inscricaoRepository, times(1)).save(eq(inscricao));

        assertNotNull(retorno, "Verfica se o retorno é diferente de null");
        assertEquals(retorno.getMensagem(), "Inscrição realizada com sucesso.");

    }

    @Test
    @DisplayName("Deve finalizar uma inscricao quando o tamanho da lista estiver igual ou menor ao número de vagas no curso")
    public void finalizar(){

        var inscricao = Inscricao.builder().id(ID).cpf("11122233344").idCurso(1).nota(BigDecimal.TEN).build();

        List<Inscricao> inscricoes = Arrays.asList(inscricao);

        when(inscricaoRepository.findByIdCurso(ID)).thenReturn(inscricoes);
        when(cursoService.quantidadeDeVagas()).thenReturn(1);
        when(inscricaoRepository.save(inscricao)).thenReturn(any());

        InscricaoMensagemResponseDTO retorno = inscricaoService.finalizar(ID);

        verify(inscricaoRepository, times(1)).findByIdCurso(eq(ID));
        verify(cursoService, times(1)).quantidadeDeVagas();
        verify(inscricaoRepository, times(1)).save(eq(inscricao));

        assertNotNull(retorno, "Verfica se o retorno é diferente de null");
        assertEquals(retorno.getMensagem(), "Inscrição finalizada com sucesso.");

    }

    @Test
    @DisplayName("Deve finalizar uma inscricao quando o tamanho da lista estiver maior que número de vagas no curso, deve selecionar apenas a maior nota")
    public void finalizarComNotasMaiores(){

        var inscricao = Inscricao.builder().id(ID).cpf("11122233344").idCurso(1).nota(new BigDecimal(10)).build();
        var inscricao2 = Inscricao.builder().id(2L).cpf("22233344411").idCurso(1).nota(new BigDecimal(4)).build();
        var inscricao3 = Inscricao.builder().id(3L).cpf("22233344411").idCurso(1).nota(new BigDecimal(7)).build();
        var inscricao4 = Inscricao.builder().id(4L).cpf("22233344411").idCurso(1).nota(new BigDecimal(3)).build();
        var inscricao5 = Inscricao.builder().id(5L).cpf("22233344411").idCurso(1).nota(new BigDecimal(6)).build();
        var inscricao6 = Inscricao.builder().id(6L).cpf("22233344411").idCurso(1).nota(new BigDecimal(5)).build();
        var inscricao7 = Inscricao.builder().id(7L).cpf("22233344411").idCurso(1).nota(new BigDecimal(8)).build();
        var inscricao8 = Inscricao.builder().id(8L).cpf("22233344411").idCurso(1).nota(new BigDecimal(7)).build();

        List<Inscricao> inscricoes = Arrays.asList(inscricao, inscricao2, inscricao3, inscricao4, inscricao5, inscricao6, inscricao7, inscricao8);

        when(inscricaoRepository.findByIdCurso(ID)).thenReturn(inscricoes);
        when(cursoService.quantidadeDeVagas()).thenReturn(5);
        when(inscricaoRepository.save(inscricao)).thenReturn(any());

        InscricaoMensagemResponseDTO retorno = inscricaoService.finalizar(ID);

        verify(inscricaoRepository, times(1)).findByIdCurso(eq(ID));
        verify(cursoService, times(1)).quantidadeDeVagas();
        verify(inscricaoRepository, times(1)).save(eq(inscricao));

        assertNotNull(retorno, "Verfica se o retorno é diferente de null");
        assertEquals(retorno.getMensagem(), "Inscrição finalizada com sucesso.");
    }

    @Test
    @DisplayName("Deve listar os cursos pelo id presente nas inscrições")
    public void listarPorIdCurso(){

        var inscricao = Inscricao.builder().id(ID).cpf("11122233344").idCurso(1).nota(new BigDecimal(10)).build();
        var inscricao2 = Inscricao.builder().id(2L).cpf("22233344411").idCurso(1).nota(new BigDecimal(4)).build();
        var inscricao3 = Inscricao.builder().id(3L).cpf("22233344411").idCurso(1).nota(new BigDecimal(7)).build();

        var retorno1 = InscricaoCursoResponseDTO.builder().cpf(inscricao.getCpf()).nota(inscricao.getNota()).build();
        var retorno2 = InscricaoCursoResponseDTO.builder().cpf(inscricao2.getCpf()).nota(inscricao2.getNota()).build();
        var retorno3 = InscricaoCursoResponseDTO.builder().cpf(inscricao3.getCpf()).nota(inscricao3.getNota()).build();

        List<Inscricao> inscricoes = Arrays.asList(inscricao, inscricao2, inscricao3);
        List<InscricaoCursoResponseDTO> responseDTOS =Arrays.asList(retorno1, retorno2, retorno3);

        when(inscricaoRepository.findByIdCurso(ID)).thenReturn(inscricoes);
        when(modelMapper.map(any(), any())).thenReturn(any());

        List<InscricaoCursoResponseDTO> retorno = inscricaoService.listarPorIdCurso(ID);

        verify(inscricaoRepository, times(1)).findByIdCurso(eq(ID));

        assertNotNull(retorno, "Verfica se o retorno é diferente de null");
    }

    @Test
    @DisplayName("Deve listar os inscritos com situação selecionado")
    public void listarInscritosFinalizados(){

        var inscricao = Inscricao.builder().id(ID).cpf("11122233344").idCurso(1).nota(new BigDecimal(10)).situacao(Situacao.SELECIONADO).build();
        var inscricao2 = Inscricao.builder().id(2L).cpf("22233344411").idCurso(1).nota(new BigDecimal(4)).situacao(Situacao.NAO_SELECIONADO).build();
        var inscricao3 = Inscricao.builder().id(3L).cpf("22233344411").idCurso(1).nota(new BigDecimal(7)).situacao(Situacao.SELECIONADO).build();

        var retorno1 = InscricaoFinalizadaResponseDTO.builder().cpf(inscricao.getCpf()).nota(inscricao.getNota()).situacao(Situacao.SELECIONADO).build();
        var retorno3 = InscricaoFinalizadaResponseDTO.builder().cpf(inscricao3.getCpf()).nota(inscricao3.getNota()).situacao(Situacao.SELECIONADO).build();

        List<Inscricao> inscricoes = Arrays.asList(inscricao, inscricao2, inscricao3);
        List<InscricaoFinalizadaResponseDTO> responseDTOS =Arrays.asList(retorno1, retorno3);

        when(inscricaoRepository.findByIdCurso(ID)).thenReturn(inscricoes);
        when(modelMapper.map(any(), any())).thenReturn(retorno1, retorno3);

        List<InscricaoFinalizadaResponseDTO> retorno = inscricaoService.inscritosFinalizados(ID);

        verify(inscricaoRepository, times(1)).findByIdCurso(eq(ID));

        assertNotNull(retorno, "Verfica se o retorno é diferente de null");
    }
}
