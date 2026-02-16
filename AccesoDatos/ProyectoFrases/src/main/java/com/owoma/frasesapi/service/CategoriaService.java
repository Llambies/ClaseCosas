package com.owoma.frasesapi.service;

import com.owoma.frasesapi.dto.request.CategoriaRequest;
import com.owoma.frasesapi.dto.response.CategoriaResponse;
import com.owoma.frasesapi.dto.response.FraseResponse;
import com.owoma.frasesapi.exception.ResourceNotFoundException;
import com.owoma.frasesapi.model.Categoria;
import com.owoma.frasesapi.model.Frase;
import com.owoma.frasesapi.repository.CategoriaRepository;
import com.owoma.frasesapi.repository.FraseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final FraseRepository fraseRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, FraseRepository fraseRepository) {
        this.categoriaRepository = categoriaRepository;
        this.fraseRepository = fraseRepository;
    }

    @Transactional(readOnly = true)
    public Page<CategoriaResponse> getAll(Pageable pageable) {
        return categoriaRepository.findAll(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public CategoriaResponse getById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id " + id));
        return toResponse(categoria);
    }

    @Transactional(readOnly = true)
    public Page<FraseResponse> getFrasesByCategoria(Long categoriaId, Pageable pageable) {
        if (!categoriaRepository.existsById(categoriaId)) {
            throw new ResourceNotFoundException("Categoria no encontrada con id " + categoriaId);
        }
        return fraseRepository.findByCategoriaId(categoriaId, pageable).map(this::toFraseResponse);
    }

    @Transactional
    public CategoriaResponse create(CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNombre(request.nombre().trim());
        return toResponse(categoriaRepository.save(categoria));
    }

    @Transactional
    public CategoriaResponse update(Long id, CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id " + id));
        categoria.setNombre(request.nombre().trim());
        return toResponse(categoriaRepository.save(categoria));
    }

    @Transactional
    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria no encontrada con id " + id);
        }
        categoriaRepository.deleteById(id);
    }

    private CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(categoria.getId(), categoria.getNombre());
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
