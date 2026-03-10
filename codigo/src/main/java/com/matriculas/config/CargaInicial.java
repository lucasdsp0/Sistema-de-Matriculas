package com.matriculas.config;

import com.matriculas.model.*;
import com.matriculas.model.enums.StatusDisciplina;
import com.matriculas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CargaInicial implements CommandLineRunner {

    @Autowired
    private DisciplinaRepository disciplinaRepository;
    
    @Autowired
    private AlunoRepository alunoRepository;
    
    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private SecretariaRepository secretariaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Popula o banco com dados iniciais
        
        // Criar professores
        if(professorRepository.count() == 0) {
            Professor prof1 = new Professor("Dr. Alan Turing", "turing", "senha123", 1001L);
            Professor prof2 = new Professor("Dra. Ada Lovelace", "ada", "senha123", 1002L);
            professorRepository.save(prof1);
            professorRepository.save(prof2);
            System.out.println("Professores inseridos!");
        }
        
        // Crear disciplinas
        if(disciplinaRepository.count() == 0) {
            Professor prof1 = professorRepository.findAll().get(0);
            Professor prof2 = professorRepository.findAll().get(1);
            
            Disciplina d1 = new Disciplina("Introdução à Computação", true, StatusDisciplina.ATIVA);
            d1.setProfessor(prof1);
            
            Disciplina d2 = new Disciplina("Matemática Discreta", true, StatusDisciplina.ATIVA);
            d2.setProfessor(prof2);
            
            Disciplina d3 = new Disciplina("Estrutura de Dados", true, StatusDisciplina.ATIVA);
            d3.setProfessor(prof1);
            d3.setObrigatorio(false);
            
            disciplinaRepository.save(d1);
            disciplinaRepository.save(d2);
            disciplinaRepository.save(d3);
            System.out.println("Disciplinas inseridas!");
        }
        
        // Criar aluno de teste
        if(alunoRepository.count() == 0) {
            Aluno aluno = new Aluno("João Silva", "joao", "senha123", 2023001L);
            alunoRepository.save(aluno);
            System.out.println("Aluno teste inserido!");
        }
        
        // Criar secretária de teste
        if(secretariaRepository.count() == 0) {
            Secretaria secretaria = new Secretaria("Maria Secretária", "secretaria", "senha123");
            secretariaRepository.save(secretaria);
            System.out.println("Secretária teste inserida!");
        }
        
        System.out.println("Carga inicial de dados finalizada!");
    }
}