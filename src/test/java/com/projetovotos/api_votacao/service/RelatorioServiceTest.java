package com.projetovotos.api_votacao.service;

import com.projetovotos.api_votacao.dto.RelatorioDTO;
import com.projetovotos.api_votacao.model.Candidato;
import com.projetovotos.api_votacao.model.Sessao;
import com.projetovotos.api_votacao.model.Voto;
import com.projetovotos.api_votacao.repository.CandidatoRepository;
import com.projetovotos.api_votacao.repository.SessaoRepository;
import com.projetovotos.api_votacao.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RelatorioServiceTest {

    @InjectMocks
    private RelatorioService relatorioService;

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private CandidatoRepository candidatoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGerarRelatorio_SessaoNaoEncontradaOuNaoEncerrada() {
        when(sessaoRepository.findById(anyLong())).thenReturn(Optional.empty());

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            relatorioService.gerarRelatorio(1L);
        });
        assertEquals("Sessão não encontrada ou ainda não foi encerrada.", thrown.getMessage());
    }

    @Test
    void testGerarRelatorio_VotosMinimos() {
        Sessao sessao = new Sessao();
        sessao.setId(1L);
        sessao.setInicio(LocalDateTime.now().minusDays(1));
        sessao.setFim(LocalDateTime.now().minusHours(1));

        Candidato candidato = new Candidato();
        candidato.setNome("Candidato Teste");

        Voto voto = new Voto();
        voto.setCandidato(candidato);
        voto.setSessao(sessao);

        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));
        when(votoRepository.findBySessao(sessao)).thenReturn(Collections.singletonList(voto));

        RelatorioDTO relatorio = relatorioService.gerarRelatorio(1L);

        assertNotNull(relatorio);
        assertEquals("PRESIDENTE", relatorio.getCargo());
        assertEquals(1, relatorio.getTotalVotos());
        assertTrue(relatorio.getCandidatosVotos().containsKey("Candidato Teste"));
        assertEquals(1, relatorio.getCandidatosVotos().get("Candidato Teste"));
        assertEquals("Nenhum vencedor", relatorio.getVencedor());
    }

    @Test
    void testGerarRelatorio_ComResultados() {
        Sessao sessao = new Sessao();
        sessao.setId(1L);
        sessao.setInicio(LocalDateTime.now().minusDays(1));
        sessao.setFim(LocalDateTime.now().minusHours(1));

        Candidato candidato1 = new Candidato();
        candidato1.setNome("Candidato 1");

        Candidato candidato2 = new Candidato();
        candidato2.setNome("Candidato 2");

        Voto voto1 = new Voto();
        voto1.setCandidato(candidato1);
        voto1.setSessao(sessao);

        Voto voto2 = new Voto();
        voto2.setCandidato(candidato2);
        voto2.setSessao(sessao);

        List<Voto> votos = Arrays.asList(voto1, voto2);

        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));
        when(votoRepository.findBySessao(sessao)).thenReturn(votos);

        RelatorioDTO relatorio = relatorioService.gerarRelatorio(1L);

        assertNotNull(relatorio);
        assertEquals("PRESIDENTE", relatorio.getCargo());
        assertEquals(2, relatorio.getTotalVotos());
        assertTrue(relatorio.getCandidatosVotos().containsKey("Candidato 1"));
        assertTrue(relatorio.getCandidatosVotos().containsKey("Candidato 2"));
        assertEquals(1, relatorio.getCandidatosVotos().get("Candidato 1"));
        assertEquals(1, relatorio.getCandidatosVotos().get("Candidato 2"));
        assertEquals("Candidato 1", relatorio.getVencedor());
    }
}
