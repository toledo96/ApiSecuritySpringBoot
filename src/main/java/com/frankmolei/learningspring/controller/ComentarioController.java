package com.frankmolei.learningspring.controller;

import com.frankmolei.learningspring.dto.ComentarioDTO;
import com.frankmolei.learningspring.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDTO>  listarComentariosPorPublicacionId(@PathVariable(value = "publicacionId") Long idPublicacion){
        return comentarioService.obtenerComentariosPorPublicacionId(idPublicacion);
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> obtenerComentarioPorId(@PathVariable(value = "publicacionId") Long publicacionId,
                                                                  @PathVariable(value = "id") Long  comentarioId){
        ComentarioDTO comentarioDTO = comentarioService.obtenerComentarioPorId(publicacionId, comentarioId);
        return new ResponseEntity<>(comentarioDTO,HttpStatus.OK);
    }


    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    private ResponseEntity<ComentarioDTO> guardarComentario( @PathVariable(value = "publicacionId") long publicacionId,@Valid @RequestBody ComentarioDTO comentarioDTO){
        return new ResponseEntity<>(comentarioService.crearComentario(publicacionId,comentarioDTO), HttpStatus.CREATED);
    }

    @PutMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> actualizarComentario(@PathVariable(value = "publicacionId") Long publicacionId,@PathVariable(value = "id") Long comentarioId,@Valid @RequestBody ComentarioDTO comentarioDTO){
        ComentarioDTO comentarioActualizado = comentarioService.actualizarComentario(publicacionId, comentarioId, comentarioDTO);
        return new ResponseEntity<>(comentarioActualizado,HttpStatus.OK);
    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<String> eliminarComentario(@PathVariable(value = "publicacionId") Long publicacionId,@PathVariable(value = "id") Long comentarioId){
        comentarioService.eliminarComentario(publicacionId, comentarioId);
        return new ResponseEntity<>("Comentario eliminado con exito",HttpStatus.OK);
    }

}
