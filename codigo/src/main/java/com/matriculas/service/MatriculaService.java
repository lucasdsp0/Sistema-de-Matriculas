package com.matriculas.service;

import com.matriculas.model.Aluno;
import com.matriculas.model.Disciplina;
import com.matriculas.model.Matricula;
import com.matriculas.model.enums.StatusMatricula;
import com.matriculas.repository.DisciplinaRepository;
import com.matriculas.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    // Constantes das regras de negócio
    private static final int MAX_OBRIGATORIAS = 4;
    private static final int MAX_OPTATIVAS = 2;
    private static final int MAX_ALUNOS_DISCIPLINA = 60;

    @Transactional
    public Matricula efetuarMatricula(Matricula matricula) throws Exception {
        
        // Regra: Validar capacidade das disciplinas
        for (Disciplina d : matricula.getDisciplinas()) {
            if (d.getAlunosMatriculados().size() >= MAX_ALUNOS_DISCIPLINA) {
                throw new Exception("A disciplina " + d.getNome() + " já atingiu o limite de 60 alunos.");
            }
        }

        // Regra: Contar obrigatórias e optativas
        long qtdObrigatorias = matricula.getDisciplinas().stream().filter(Disciplina::isObrigatorio).count();
        long qtdOptativas = matricula.getDisciplinas().stream().filter(d -> !d.isObrigatorio()).count();

        if (qtdObrigatorias > MAX_OBRIGATORIAS) {
            throw new Exception("O aluno não pode se matricular em mais de 4 disciplinas obrigatórias.");
        }
        if (qtdOptativas > MAX_OPTATIVAS) {
            throw new Exception("O aluno não pode se matricular em mais de 2 disciplinas optativas.");
        }

        // Salva no banco de dados
        matricula.setStatus(StatusMatricula.EFETIVADA);
        Matricula matriculaSalva = matriculaRepository.save(matricula);

        // Notifica o sistema de cobrança após salvar
        notificarSistemaDeCobranca(matriculaSalva);

        return matriculaSalva;
    }

    private void notificarSistemaDeCobranca(Matricula matricula) {
        // Simulação da integração com o Sistema de Cobrança
        System.out.println("==================================================");
        System.out.println("[SISTEMA DE COBRANÇA NOTIFICADO]");
        System.out.println("Aluno: " + matricula.getAluno().getNome());
        System.out.println("Quantidade de Disciplinas cobradas: " + matricula.getDisciplinas().size());
        System.out.println("Status: " + matricula.getStatus());
        System.out.println("==================================================");
    }
}