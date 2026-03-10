package com.matriculas.config;

import com.matriculas.model.Disciplina;
import com.matriculas.model.enums.StatusDisciplina;
import com.matriculas.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargaInicial implements CommandLineRunner {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Popula o banco
        if(disciplinaRepository.count() == 0) {
            disciplinaRepository.save(new Disciplina("Introdução à Computação", true, StatusDisciplina.ATIVA));
            disciplinaRepository.save(new Disciplina("Matemática Discreta", true, StatusDisciplina.ATIVA));
            System.out.println("Carga inicial de dados finalizada!");
        }
    }
}