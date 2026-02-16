package com.owoma.frasesapi.controller;

import com.owoma.frasesapi.dto.request.CategoriaRequest;
import com.owoma.frasesapi.dto.response.CategoriaResponse;
import com.owoma.frasesapi.dto.response.FraseResponse;
import com.owoma.frasesapi.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public Page<CategoriaResponse> getAll(Pageable pageable) {
        return categoriaService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public CategoriaResponse getById(@PathVariable Long id) {
        return categoriaService.getById(id);
    }

    @GetMapping("/{id}/frases")
    public Page<FraseResponse> getFrasesByCategoria(@PathVariable Long id, Pageable pageable) {
        return categoriaService.getFrasesByCategoria(id, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponse create(@Valid @RequestBody CategoriaRequest request) {
        return categoriaService.create(request);
    }

    @PutMapping("/{id}")
    public CategoriaResponse update(@PathVariable Long id, @Valid @RequestBody CategoriaRequest request) {
        return categoriaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoriaService.delete(id);
    }
}
