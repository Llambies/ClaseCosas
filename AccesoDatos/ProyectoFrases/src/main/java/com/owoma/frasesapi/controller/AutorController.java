package com.owoma.frasesapi.controller;

import com.owoma.frasesapi.dto.request.AutorRequest;
import com.owoma.frasesapi.dto.response.AutorResponse;
import com.owoma.frasesapi.dto.response.FraseResponse;
import com.owoma.frasesapi.service.AutorService;
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
@RequestMapping("/api/v1/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public Page<AutorResponse> getAll(Pageable pageable) {
        return autorService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public AutorResponse getById(@PathVariable Long id) {
        return autorService.getById(id);
    }

    @GetMapping("/{id}/frases")
    public Page<FraseResponse> getFrasesByAutor(@PathVariable Long id, Pageable pageable) {
        return autorService.getFrasesByAutor(id, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutorResponse create(@Valid @RequestBody AutorRequest request) {
        return autorService.create(request);
    }

    @PutMapping("/{id}")
    public AutorResponse update(@PathVariable Long id, @Valid @RequestBody AutorRequest request) {
        return autorService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        autorService.delete(id);
    }
}
