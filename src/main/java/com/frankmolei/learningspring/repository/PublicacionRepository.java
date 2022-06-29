package com.frankmolei.learningspring.repository;

import com.frankmolei.learningspring.model.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepository extends JpaRepository<Publicacion,Long> {
}
