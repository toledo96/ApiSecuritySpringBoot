package com.frankmolei.learningspring.service;

import com.frankmolei.learningspring.dto.ComentarioDTO;
import com.frankmolei.learningspring.exceptions.BlogAppException;
import com.frankmolei.learningspring.exceptions.ResourceNotFoundException;
import com.frankmolei.learningspring.model.Comentario;
import com.frankmolei.learningspring.model.Publicacion;
import com.frankmolei.learningspring.repository.ComentarioReposiotry;
import com.frankmolei.learningspring.repository.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComentarioReposiotry comentarioReposiotry;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO) {
        Comentario comentario = mapearEntidad(comentarioDTO);
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        comentario.setPublicacion(publicacion);
        Comentario nuevoComentario = comentarioReposiotry.save(comentario);
        return mapearDTO(nuevoComentario);
    }

    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(long publicacionId) {
        List<Comentario> comentarios = comentarioReposiotry.findByPublicacionId(publicacionId);
        return comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId).orElseThrow( () -> new ResourceNotFoundException("Publicacion","id",publicacionId));
        Comentario comentario = comentarioReposiotry.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario","id",comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicación");
        }

        return mapearDTO(comentario);
    }

    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO solicitudDeComentario) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId).orElseThrow( () -> new ResourceNotFoundException("Publicacion","id",publicacionId));
        Comentario comentario = comentarioReposiotry.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario","id",comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicación");
        }
        comentario.setNombre(solicitudDeComentario.getNombre());
        comentario.setEmail(solicitudDeComentario.getEmail());
        comentario.setCuerpo(solicitudDeComentario.getCuerpo());
        Comentario comentarioActualizado = comentarioReposiotry.save(comentario);
        return mapearDTO(comentarioActualizado);
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacionId));

        Comentario comentario = comentarioReposiotry.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
        }

        comentarioReposiotry.delete(comentario);
    }

    private ComentarioDTO mapearDTO(Comentario comentarioDTO){
        ComentarioDTO comentarioDTOResultado = modelMapper.map(comentarioDTO,ComentarioDTO.class);
        return comentarioDTOResultado;
    }

    private Comentario mapearEntidad(ComentarioDTO comentarioDTO){
        Comentario comentarioResultado = modelMapper.map(comentarioDTO,Comentario.class);
        return comentarioResultado;
    }

}
