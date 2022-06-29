package com.frankmolei.learningspring.controller;

import com.frankmolei.learningspring.dto.PublicacionDTO;
import com.frankmolei.learningspring.dto.PublicacionRespuesta;
import com.frankmolei.learningspring.service.PublicacionService;
import com.frankmolei.learningspring.utils.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    public PublicacionRespuesta listarPublicaciones(@RequestParam(value = "pageNo",defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO,required = false) int numDePaginas,
                                                    @RequestParam(value = "pageSize",defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required = false) int medidaDePagina,
                                                    @RequestParam(value = "sortBy",defaultValue = AppConstantes.ORDENAR_POR_DEFECTO,required = false) String ordenarPor,
                                                    @RequestParam(value = "sortDir",defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO,required = false) String sortDir){
        return publicacionService.obtenerPublicaciones(numDePaginas, medidaDePagina,ordenarPor,sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(publicacionService.obtenerPublicacionPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PublicacionDTO> guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO){
        return new ResponseEntity<>(publicacionService.crearPublicacion(publicacionDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PublicacionDTO> actualizarPublicacion(@Valid  @RequestBody PublicacionDTO publicacionDTO, @PathVariable(name = "id") long id){
        PublicacionDTO publicacionRespuesta = publicacionService.actualizarPublicacion(id,publicacionDTO);
        return new ResponseEntity<>(publicacionRespuesta,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable(name = "id") long id){
        publicacionService.eliminarPublicacion(id);
        return new ResponseEntity<>("Eliminado con exito",HttpStatus.OK);
    }

}
