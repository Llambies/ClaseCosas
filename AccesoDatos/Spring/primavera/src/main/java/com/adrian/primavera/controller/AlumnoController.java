package com.adrian.primavera.controller;

import com.adrian.primavera.models.Alumno;
import com.adrian.primavera.services.AlumnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alumnos")
@Tag(name = "Alumnos", description = "API para gestión de alumnos y aprobaciones")
public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @Operation(summary = "Obtener todos los alumnos", description = "Retorna una lista con todos los alumnos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de alumnos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Alumno>> obtenerTodos() {
        List<Alumno> alumnos = alumnoService.obtenerTodos();
        return ResponseEntity.ok(alumnos);
    }

    @Operation(summary = "Obtener alumno por ID", description = "Retorna un alumno específico según su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alumno encontrado"),
        @ApiResponse(responseCode = "404", description = "Alumno no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtenerPorId(@PathVariable Long id) {
        Optional<Alumno> alumno = alumnoService.obtenerPorId(id);
        return alumno.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener alumnos aprobados", description = "Retorna una lista con todos los alumnos que han sido aprobados")
    @ApiResponse(responseCode = "200", description = "Lista de alumnos aprobados")
    @GetMapping("/aprobados")
    public ResponseEntity<List<Alumno>> obtenerAprobados() {
        List<Alumno> alumnos = alumnoService.obtenerAprobados();
        return ResponseEntity.ok(alumnos);
    }

    @Operation(summary = "Obtener alumnos pendientes", description = "Retorna una lista con todos los alumnos pendientes de aprobación")
    @ApiResponse(responseCode = "200", description = "Lista de alumnos pendientes")
    @GetMapping("/pendientes")
    public ResponseEntity<List<Alumno>> obtenerPendientes() {
        List<Alumno> alumnos = alumnoService.obtenerPendientes();
        return ResponseEntity.ok(alumnos);
    }

    @Operation(summary = "Crear nuevo alumno", description = "Crea un nuevo alumno en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Alumno creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Alumno> crear(@RequestBody Alumno alumno) {
        try {
            Alumno nuevoAlumno = alumnoService.guardar(alumno);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAlumno);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Actualizar alumno", description = "Actualiza los datos de un alumno existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alumno actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Alumno no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizar(@PathVariable Long id, @RequestBody Alumno alumno) {
        try {
            Optional<Alumno> alumnoExistente = alumnoService.obtenerPorId(id);
            if (alumnoExistente.isPresent()) {
                alumno.setId(id);
                Alumno alumnoActualizado = alumnoService.guardar(alumno);
                return ResponseEntity.ok(alumnoActualizado);
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Aprobar alumno", description = "Aprueba un alumno cambiando su estado de aprobado a true")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alumno aprobado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Alumno no encontrado")
    })
    @PatchMapping("/{id}/aprobar")
    public ResponseEntity<Alumno> aprobar(@PathVariable Long id) {
        try {
            Alumno alumno = alumnoService.aprobar(id);
            return ResponseEntity.ok(alumno);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Rechazar alumno", description = "Rechaza un alumno cambiando su estado de aprobado a false")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alumno rechazado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Alumno no encontrado")
    })
    @PatchMapping("/{id}/rechazar")
    public ResponseEntity<Alumno> rechazar(@PathVariable Long id) {
        try {
            Alumno alumno = alumnoService.rechazar(id);
            return ResponseEntity.ok(alumno);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar alumno", description = "Elimina un alumno del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Alumno eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Alumno no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            alumnoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
