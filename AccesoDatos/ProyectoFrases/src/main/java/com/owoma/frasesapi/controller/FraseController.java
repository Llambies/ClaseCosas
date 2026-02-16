package com.owoma.frasesapi.controller;

import com.owoma.frasesapi.dto.request.FraseRequest;
import com.owoma.frasesapi.dto.response.FraseResponse;
import com.owoma.frasesapi.service.FraseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/frases")
public class FraseController {

    private final FraseService fraseService;

    public FraseController(FraseService fraseService) {
        this.fraseService = fraseService;
    }

    @GetMapping
    public Page<FraseResponse> getAll(Pageable pageable) {
        return fraseService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public FraseResponse getById(@PathVariable Long id) {
        return fraseService.getById(id);
    }

    @GetMapping("/dia")
    public FraseResponse getFraseDelDia(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha
    ) {
        return fraseService.getFraseDelDia(fecha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FraseResponse create(@Valid @RequestBody FraseRequest request) {
        return fraseService.create(request);
    }

    @PutMapping("/{id}")
    public FraseResponse update(@PathVariable Long id, @Valid @RequestBody FraseRequest request) {
        return fraseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        fraseService.delete(id);
    }
}
