package com.projetovotos.api_votacao.service;

import com.projetovotos.api_votacao.model.Voto;
import com.projetovotos.api_votacao.model.Eleitor;
import com.projetovotos.api_votacao.repository.VotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VotoServiceTest {

    @Mock
    private VotoRepository votoRepository;

    @InjectMocks
    private VotoService votoService;

    private Voto voto;
    private Eleitor eleitor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eleitor = new Eleitor();
        eleitor.setId(1L);
        voto = new Voto();
        voto.setId(1L);
        voto.setEleitor(eleitor);  // Configurando o eleitor no voto
    }

    @Test
    void testVotar() {
        when(votoRepository.existsByEleitorId(1L)).thenReturn(false);
        when(votoRepository.save(any(Voto.class))).thenReturn(voto);

        Voto savedVoto = votoService.votar(voto);

        assertNotNull(savedVoto);
        verify(votoRepository, times(1)).save(voto);
    }

    @Test
    void testVotarEleitorJaVotou() {
        when(votoRepository.existsByEleitorId(1L)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> votoService.votar(voto));
    }
}
