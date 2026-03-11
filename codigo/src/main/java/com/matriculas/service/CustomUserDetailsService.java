package com.matriculas.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.matriculas.model.Aluno;
import com.matriculas.model.Professor;
import com.matriculas.model.Secretaria;
import com.matriculas.repository.AlunoRepository;
import com.matriculas.repository.ProfessorRepository;
import com.matriculas.repository.SecretariaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private SecretariaRepository secretariaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Tentando autenticar usuário: {}", username);

        // Tenta buscar como Aluno
        log.debug("Procurando por Aluno com login: {}", username);
        Aluno aluno = alunoRepository.findByLogin(username);
        if (aluno != null) {
            log.info("Aluno '{}' encontrado. Concedendo ROLE_ALUNO.", username);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ALUNO"));
            return new org.springframework.security.core.userdetails.User(aluno.getLogin(), aluno.getSenha(), authorities);
        }

        // Tenta buscar como Professor
        log.debug("Procurando por Professor com login: {}", username);
        Professor professor = professorRepository.findByLogin(username);
        if (professor != null) {
            log.info("Professor '{}' encontrado. Concedendo ROLE_PROFESSOR.", username);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_PROFESSOR"));
            return new org.springframework.security.core.userdetails.User(professor.getLogin(), professor.getSenha(), authorities);
        }

        // Tenta buscar como Secretaria
        log.debug("Procurando por Secretaria com login: {}", username);
        Secretaria secretaria = secretariaRepository.findByLogin(username);
        if (secretaria != null) {
            log.info("Secretaria '{}' encontrada. Concedendo ROLE_SECRETARIA.", username);
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_SECRETARIA"));
            return new org.springframework.security.core.userdetails.User(secretaria.getLogin(), secretaria.getSenha(), authorities);
        }

        log.warn("Usuário não encontrado em nenhuma das fontes de dados: {}", username);
        throw new UsernameNotFoundException("Usuário não encontrado: " + username);
    }
}
