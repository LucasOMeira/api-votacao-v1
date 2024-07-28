package com.projetovotos.api_votacao.service;

import com.projetovotos.api_votacao.model.Candidato;
import com.projetovotos.api_votacao.repository.CandidatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CandidatoServiceTest {

    @InjectMocks
    private CandidatoService candidatoService;

    @Mock
    private CandidatoRepository candidatoRepository;

    public CandidatoServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void setup() {
        candidatoRepository.deleteAll();
    }

    @Test
    public void testSaveCandidato_Success() {
        Candidato candidato = new Candidato();
        candidato.setNome("Candidato 1");
        candidato.setPartido("Partido 1");

        when(candidatoRepository.existsByNome(anyString())).thenReturn(false);
        when(candidatoRepository.existsByPartido(anyString())).thenReturn(false);
        when(candidatoRepository.save(any(Candidato.class))).thenReturn(candidato);

        Candidato savedCandidato = candidatoService.save(candidato);

        assertNotNull(savedCandidato);
        assertEquals("Candidato 1", savedCandidato.getNome());
    }

    @Test
    public void testDeleteCandidato_WithVotes() {
        Long candidatoId = 1L;
        when(candidatoRepository.existsByIdAndVotosNotEmpty(anyLong())).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            candidatoService.deleteById(candidatoId);
        });
    }
}
