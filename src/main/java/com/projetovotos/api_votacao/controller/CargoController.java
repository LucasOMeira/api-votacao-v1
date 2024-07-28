package com.projetovotos.api_votacao.controller;

import com.projetovotos.api_votacao.dto.CargoDTO;
import com.projetovotos.api_votacao.model.Cargo;
import com.projetovotos.api_votacao.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @GetMapping
    public List<CargoDTO> getAll() {
        return cargoService.findAll().stream()
                .map(cargo -> {
                    CargoDTO dto = new CargoDTO();
                    dto.setId(cargo.getId());
                    dto.setNome(cargo.getNome());
                    return dto;
                }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargoDTO> getById(@PathVariable Long id) {
        return cargoService.findById(id)
                .map(cargo -> {
                    CargoDTO dto = new CargoDTO();
                    dto.setId(cargo.getId());
                    dto.setNome(cargo.getNome());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CargoDTO create(@RequestBody CargoDTO cargoDTO) {
        Cargo cargo = new Cargo();
        cargo.setNome(cargoDTO.getNome());
        Cargo savedCargo = cargoService.save(cargo);
        cargoDTO.setId(savedCargo.getId());
        return cargoDTO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cargoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}