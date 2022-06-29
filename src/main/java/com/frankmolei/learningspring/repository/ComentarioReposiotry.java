package com.frankmolei.learningspring.repository;

import com.frankmolei.learningspring.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioReposiotry extends JpaRepository<Comentario,Long> {

    public List<Comentario> findByPublicacionId(long id);

}
