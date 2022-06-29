package com.frankmolei.learningspring.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String nombreDelRecurso;
    private String nombreDelcampo;
    private Long valorDelCampo;

    public ResourceNotFoundException(String nombreDelRecurso,String nombreDelcampo,Long valorDelCampo){
        super(String.format("%s no encontrada con: %s : %s" , nombreDelRecurso,nombreDelcampo, valorDelCampo));
        this.nombreDelRecurso = nombreDelRecurso;
        this.nombreDelcampo = nombreDelcampo;
        this.valorDelCampo = valorDelCampo;
    }

    public String getNombreDelRecurso() {
        return nombreDelRecurso;
    }

    public void setNombreDelRecurso(String nombreDelRecurso) {
        this.nombreDelRecurso = nombreDelRecurso;
    }

    public String getNombreDelcampo() {
        return nombreDelcampo;
    }

    public void setNombreDelcampo(String nombreDelcampo) {
        this.nombreDelcampo = nombreDelcampo;
    }

    public Long getValorDelCampo() {
        return valorDelCampo;
    }

    public void setValorDelCampo(Long valorDelCampo) {
        this.valorDelCampo = valorDelCampo;
    }
}
