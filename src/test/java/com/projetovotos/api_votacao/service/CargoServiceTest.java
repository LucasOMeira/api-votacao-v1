package com.projetovotos.api_votacao.service;

import com.projetovotos.api_votacao.model.Cargo;
import com.projetovotos.api_votacao.repository.CargoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CargoServiceTest {

    @InjectMocks
    private CargoService cargoService;

    @Mock
    private CargoRepository cargoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Cargo cargo = new Cargo();
        cargo.setId(1L);
        cargo.setNome("Cargo Teste");

        when(cargoRepository.findAll()).thenReturn(Collections.singletonList(cargo));

        assertEquals(1, cargoService.findAll().size());
        assertEquals("Cargo Teste", cargoService.findAll().get(0).getNome());
    }

    @Test
    void testFindById() {
        Cargo cargo = new Cargo();
        cargo.setId(1L);
        cargo.setNome("Cargo Teste");

        when(cargoRepository.findById(1L)).thenReturn(Optional.of(cargo));

        Optional<Cargo> foundCargo = cargoService.findById(1L);
        assertTrue(foundCargo.isPresent());
        assertEquals("Cargo Teste", foundCargo.get().getNome());
    }

    @Test
    void testFindById_NotFound() {
        when(cargoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Cargo> foundCargo = cargoService.findById(1L);
        assertFalse(foundCargo.isPresent());
    }

    @Test
    void testSave() {
        Cargo cargo = new Cargo();
        cargo.setId(1L);
        cargo.setNome("Cargo Teste");

        when(cargoRepository.save(cargo)).thenReturn(cargo);

        Cargo savedCargo = cargoService.save(cargo);
        assertEquals("Cargo Teste", savedCargo.getNome());
        verify(cargoRepository, times(1)).save(cargo);
    }

    @Test
    void testDeleteById() {
        doNothing().when(cargoRepository).deleteById(1L);

        cargoService.deleteById(1L);

        verify(cargoRepository, times(1)).deleteById(1L);
    }
}
