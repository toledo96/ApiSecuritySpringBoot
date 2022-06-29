package com.frankmolei.learningspring.service;

import com.frankmolei.learningspring.dto.PublicacionDTO;
import com.frankmolei.learningspring.dto.PublicacionRespuesta;
import com.frankmolei.learningspring.model.Publicacion;

import java.util.List;

public interface PublicacionService {

    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);
    public PublicacionRespuesta obtenerPublicaciones(int numeroDePagina, int medidaDePagina,String ordenarPor,String sortDir);
    public PublicacionDTO obtenerPublicacionPorId(long id);
    public PublicacionDTO actualizarPublicacion(long id,PublicacionDTO publicacionDTO);
    public void eliminarPublicacion(long id);

}
