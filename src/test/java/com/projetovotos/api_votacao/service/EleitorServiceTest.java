package com.projetovotos.api_votacao.service;

import com.projetovotos.api_votacao.model.Eleitor;
import com.projetovotos.api_votacao.repository.EleitorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EleitorServiceTest {

    @InjectMocks
    private EleitorService eleitorService;

    @Mock
    private EleitorRepository eleitorRepository;

    public EleitorServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveEleitor_Success() {
        Eleitor eleitor = new Eleitor();
        eleitor.setNome("Eleitor 1");
        eleitor.setCpf("12345678900");

        when(eleitorRepository.existsByNome(anyString())).thenReturn(false);
        when(eleitorRepository.existsByCpf(anyString())).thenReturn(false);
        when(eleitorRepository.save(any(Eleitor.class))).thenReturn(eleitor);

        Eleitor savedEleitor = eleitorService.save(eleitor);

        assertNotNull(savedEleitor);
        assertEquals("Eleitor 1", savedEleitor.getNome());
    }

}
