package com.adrian.primavera.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adrian.primavera.models.Alumno;
@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
 // Buscamos alumnos cuyo email sea de un dominio concreto
 @Query("SELECT a FROM Alumno a WHERE a.email LIKE %:dominio")
 List<Alumno> findByEmailContaining(String dominio);
}
