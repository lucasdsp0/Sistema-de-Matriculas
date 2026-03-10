package com.matriculas.model;

import jakarta.persistence.*;

/**
 * Classe base para todos os usuários do sistema.
 * Usada como superclasse mapeada pelo JPA (herança com JOINED ou SINGLE_TABLE).
 */
@MappedSuperclass
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    // ------ Construtores ------

    public Usuario() {
    }

    public Usuario(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    // ------ Método de negócio ------

    /**
     * Verifica se as credenciais fornecidas são válidas.
     */
    public boolean doLogin(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }

    // ------ Getters e Setters ------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
