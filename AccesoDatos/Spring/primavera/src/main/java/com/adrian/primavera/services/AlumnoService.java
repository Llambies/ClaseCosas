package com.adrian.primavera.services;

import org.springframework.stereotype.Service;
import com.adrian.primavera.repository.AlumnoRepository;
import com.adrian.primavera.models.Alumno;
import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {
 // Inyección de dependencias (El repositorio que creamos en el bloque 3)
 private final AlumnoRepository alumnoRepository;
 public AlumnoService(AlumnoRepository alumnoRepository) {
 this.alumnoRepository = alumnoRepository;
 }
 public List<Alumno> obtenerTodos() {
 return alumnoRepository.findAll();
 }
 
 public Optional<Alumno> obtenerPorId(Long id) {
 return alumnoRepository.findById(id);
 }
 
 public List<Alumno> obtenerAprobados() {
 return alumnoRepository.findAll().stream()
 .filter(Alumno::getAprobado)
 .toList();
 }
 
 public List<Alumno> obtenerPendientes() {
 return alumnoRepository.findAll().stream()
 .filter(a -> !a.getAprobado())
 .toList();
 }
 
 public Alumno guardar(Alumno alumno) {
 // Aquí iría la lógica de negocio antes de guardar
 if (alumno.getNombre() == null || alumno.getNombre().isEmpty()) {
 throw new RuntimeException("El nombre no puede estar vacío");
 }
 return alumnoRepository.save(alumno);
 }
 
 public Alumno aprobar(Long id) {
 Alumno alumno = alumnoRepository.findById(id)
 .orElseThrow(() -> new RuntimeException("Alumno no encontrado con id: " + id));
 alumno.setAprobado(true);
 return alumnoRepository.save(alumno);
 }
 
 public Alumno rechazar(Long id) {
 Alumno alumno = alumnoRepository.findById(id)
 .orElseThrow(() -> new RuntimeException("Alumno no encontrado con id: " + id));
 alumno.setAprobado(false);
 return alumnoRepository.save(alumno);
 }
 
 public void eliminar(Long id) {
 if (!alumnoRepository.existsById(id)) {
 throw new RuntimeException("Alumno no encontrado con id: " + id);
 }
 alumnoRepository.deleteById(id);
 }
}