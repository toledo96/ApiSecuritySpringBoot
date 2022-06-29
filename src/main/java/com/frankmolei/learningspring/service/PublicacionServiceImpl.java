package com.frankmolei.learningspring.service;

import com.frankmolei.learningspring.dto.PublicacionDTO;
import com.frankmolei.learningspring.dto.PublicacionRespuesta;
import com.frankmolei.learningspring.exceptions.ResourceNotFoundException;
import com.frankmolei.learningspring.model.Publicacion;
import com.frankmolei.learningspring.repository.PublicacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionServiceImpl implements PublicacionService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
        Publicacion publicacion = mapearEntidad(publicacionDTO);

        Publicacion nuevaPublicacion = publicacionRepository.save(publicacion);

        PublicacionDTO publicacionRespuesta = mapearDTO(nuevaPublicacion);
        return publicacionRespuesta;
    }

    @Override
    public PublicacionRespuesta obtenerPublicaciones(int numeroDePagina, int medidaDePagina,String ordenarPor,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina,medidaDePagina, sort);
        Page<Publicacion> publicaciones = publicacionRepository.findAll(pageable);
        List<Publicacion> listaPublicaciones = publicaciones.getContent();
        List<PublicacionDTO> contenido = listaPublicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());
        PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
        publicacionRespuesta.setContenido(contenido);
        publicacionRespuesta.setNumeroPagina(publicaciones.getNumber());
        publicacionRespuesta.setMedidaPagina(publicaciones.getSize());
        publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
        publicacionRespuesta.setTotalPaginas(publicaciones.getTotalPages());
        publicacionRespuesta.setUltima(publicaciones.isLast());
        return publicacionRespuesta;
    }

    @Override
    public PublicacionDTO obtenerPublicacionPorId(long id) {
        Publicacion publicacion = publicacionRepository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Publicacion","id",id));
        return mapearDTO(publicacion);
    }

    @Override
    public PublicacionDTO actualizarPublicacion(long id, PublicacionDTO publicacionDTO) {
        Publicacion publicacion = publicacionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion","id",id));
        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        publicacion.setContenido(publicacionDTO.getContenido());
        Publicacion publicacionActualizada = publicacionRepository.save(publicacion);
        return mapearDTO(publicacionActualizada);
    }

    @Override
    public void eliminarPublicacion(long id) {
        Publicacion publicacion = publicacionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion","id",id));
        publicacionRepository.delete(publicacion);
    }

    private PublicacionDTO mapearDTO(Publicacion publicacion){
        PublicacionDTO publicacionRespuesta = modelMapper.map(publicacion,PublicacionDTO.class);
        return publicacionRespuesta;
    }

    private Publicacion mapearEntidad(PublicacionDTO publicacionDTO){
        Publicacion publicacion = modelMapper.map(publicacionDTO,Publicacion.class);
        return publicacion;
    }

}
