package com.matriculas.model;

import jakarta.persistence.*;

/**
 * Representa um funcionário da secretaria.
 * Responsável por gerar currículos e gerenciar disciplinas.
 */
@Entity
@Table(name = "secretarias")
public class Secretaria extends Usuario {

    // ------ Construtores ------

    public Secretaria() {
    }

    public Secretaria(String nome, String login, String senha) {
        super(nome, login, senha);
    }
}
