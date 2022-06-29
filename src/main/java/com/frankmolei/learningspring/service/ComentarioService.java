package com.frankmolei.learningspring.service;

import com.frankmolei.learningspring.dto.ComentarioDTO;

import java.util.List;

public interface ComentarioService {
    public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO);

    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(long publicacionId);

    public ComentarioDTO obtenerComentarioPorId(Long publicacionId,Long comentarioId);

    public ComentarioDTO actualizarComentario(Long publicacionId,Long comentarioId,ComentarioDTO solicitudDeComentario);

    public void eliminarComentario(Long publicacionId,Long comentarioId);
}
