package com.owoma.frasesapi.service;

import com.owoma.frasesapi.dto.request.FraseRequest;
import com.owoma.frasesapi.dto.response.FraseResponse;
import com.owoma.frasesapi.exception.ResourceNotFoundException;
import com.owoma.frasesapi.model.Autor;
import com.owoma.frasesapi.model.Categoria;
import com.owoma.frasesapi.model.Frase;
import com.owoma.frasesapi.repository.AutorRepository;
import com.owoma.frasesapi.repository.CategoriaRepository;
import com.owoma.frasesapi.repository.FraseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class FraseService {

    private final FraseRepository fraseRepository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    public FraseService(
            FraseRepository fraseRepository,
            AutorRepository autorRepository,
            CategoriaRepository categoriaRepository
    ) {
        this.fraseRepository = fraseRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public Page<FraseResponse> getAll(Pageable pageable) {
        return fraseRepository.findAll(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public FraseResponse getById(Long id) {
        Frase frase = fraseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Frase no encontrada con id " + id));
        return toResponse(frase);
    }

    @Transactional(readOnly = true)
    public FraseResponse getFraseDelDia(LocalDate fecha) {
        LocalDate targetDate = fecha == null ? LocalDate.now() : fecha;
        Frase frase = fraseRepository.findByFechaProgramada(targetDate)
                .orElseThrow(() -> new ResourceNotFoundException("No hay frase programada para la fecha " + targetDate));
        return toResponse(frase);
    }

    @Transactional
    public FraseResponse create(FraseRequest request) {
        Frase frase = new Frase();
        applyRequest(frase, request);
        return toResponse(fraseRepository.save(frase));
    }

    @Transactional
    public FraseResponse update(Long id, FraseRequest request) {
        Frase frase = fraseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Frase no encontrada con id " + id));
        applyRequest(frase, request);
        return toResponse(fraseRepository.save(frase));
    }

    @Transactional
    public void delete(Long id) {
        if (!fraseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Frase no encontrada con id " + id);
        }
        fraseRepository.deleteById(id);
    }

    private void applyRequest(Frase frase, FraseRequest request) {
        Autor autor = autorRepository.findById(request.autorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con id " + request.autorId()));
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id " + request.categoriaId()));

        frase.setTexto(request.texto().trim());
        frase.setFechaProgramada(request.fechaProgramada());
        frase.setAutor(autor);
        frase.setCategoria(categoria);
    }

    private FraseResponse toResponse(Frase frase) {
        return new FraseResponse(
                frase.getId(),
                frase.getTexto(),
                frase.getFechaProgramada(),
                frase.getAutor().getId(),
                frase.getAutor().getNombre(),
                frase.getCategoria().getId(),
                frase.getCategoria().getNombre()
        );
    }
}
