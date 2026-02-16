package com.owoma.frasesapi.service;

import com.owoma.frasesapi.dto.request.AutorRequest;
import com.owoma.frasesapi.dto.response.AutorResponse;
import com.owoma.frasesapi.dto.response.FraseResponse;
import com.owoma.frasesapi.exception.ResourceNotFoundException;
import com.owoma.frasesapi.model.Autor;
import com.owoma.frasesapi.model.Frase;
import com.owoma.frasesapi.repository.AutorRepository;
import com.owoma.frasesapi.repository.FraseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final FraseRepository fraseRepository;

    public AutorService(AutorRepository autorRepository, FraseRepository fraseRepository) {
        this.autorRepository = autorRepository;
        this.fraseRepository = fraseRepository;
    }

    @Transactional(readOnly = true)
    public Page<AutorResponse> getAll(Pageable pageable) {
        return autorRepository.findAll(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public AutorResponse getById(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con id " + id));
        return toResponse(autor);
    }

    @Transactional(readOnly = true)
    public Page<FraseResponse> getFrasesByAutor(Long autorId, Pageable pageable) {
        if (!autorRepository.existsById(autorId)) {
            throw new ResourceNotFoundException("Autor no encontrado con id " + autorId);
        }
        return fraseRepository.findByAutorId(autorId, pageable).map(this::toFraseResponse);
    }

    @Transactional
    public AutorResponse create(AutorRequest request) {
        Autor autor = new Autor();
        applyRequest(autor, request);
        return toResponse(autorRepository.save(autor));
    }

    @Transactional
    public AutorResponse update(Long id, AutorRequest request) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con id " + id));
        applyRequest(autor, request);
        return toResponse(autorRepository.save(autor));
    }

    @Transactional
    public void delete(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Autor no encontrado con id " + id);
        }
        autorRepository.deleteById(id);
    }

    private void applyRequest(Autor autor, AutorRequest request) {
        autor.setNombre(request.nombre().trim());
        autor.setAnioNacimiento(request.anioNacimiento());
        autor.setAnioFallecimiento(request.anioFallecimiento());
        autor.setProfesion(request.profesion() == null ? null : request.profesion().trim());
    }

    private AutorResponse toResponse(Autor autor) {
        return new AutorResponse(
                autor.getId(),
                autor.getNombre(),
                autor.getAnioNacimiento(),
                autor.getAnioFallecimiento(),
                autor.getProfesion()
        );
    }

    private FraseResponse toFraseResponse(Frase frase) {
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
