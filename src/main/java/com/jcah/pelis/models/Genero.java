package com.jcah.pelis.models;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
public class Genero {

    @Id
    @Column
    private Integer id;

    private String titulo;

    

    public Genero() {
    }

    public Genero(Integer id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    

    



}
