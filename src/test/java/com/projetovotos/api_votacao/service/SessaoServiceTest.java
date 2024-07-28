package com.projetovotos.api_votacao.service;

import com.projetovotos.api_votacao.model.Sessao;
import com.projetovotos.api_votacao.model.Voto;
import com.projetovotos.api_votacao.repository.SessaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SessaoServiceTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @InjectMocks
    private SessaoService sessaoService;

    private Sessao sessao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sessao = new Sessao();
        sessao.setId(1L);
        sessao.setInicio(LocalDateTime.now());
        sessao.setAberta(true);
        sessao.setVotos(new ArrayList<>());
    }

    @Test
    void testFecharSessao_Success() {
        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));
        when(sessaoRepository.save(sessao)).thenReturn(sessao);

        Sessao result = sessaoService.fecharSessao(1L);
        assertNotNull(result);
        assertFalse(result.isAberta());
        assertNotNull(result.getFim());
    }

    @Test
    void testFecharSessao_SessaoNotFound() {
        when(sessaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> sessaoService.fecharSessao(1L));
    }

    @Test
    void testFecharSessao_SessaoJaFechada() {
        sessao.setAberta(false);
        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));

        assertThrows(IllegalStateException.class, () -> sessaoService.fecharSessao(1L));
    }

    @Test
    void testFecharSessao_ZeroVotos() {
        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));
        when(sessaoRepository.save(sessao)).thenReturn(sessao);

        Sessao result = sessaoService.fecharSessao(1L);
        assertNotNull(result);
        assertFalse(result.isAberta());
        assertNotNull(result.getFim());
    }

    @Test
    void testFecharSessao_MaisDeDoisVotos() {
        Voto voto1 = new Voto();
        Voto voto2 = new Voto();
        sessao.getVotos().add(voto1);
        sessao.getVotos().add(voto2);  // Adicionando dois votos para simular a condição

        when(sessaoRepository.findById(1L)).thenReturn(Optional.of(sessao));
        when(sessaoRepository.save(sessao)).thenReturn(sessao);

        Sessao result = sessaoService.fecharSessao(1L);
        assertNotNull(result);
        assertFalse(result.isAberta());
        assertNotNull(result.getFim());
    }
}
