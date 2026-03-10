package com.matriculas.service;

import com.matriculas.model.Disciplina;
import com.matriculas.model.enums.StatusDisciplina;
import com.matriculas.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    private static final int MIN_ALUNOS_ATIVACAO = 3;

    @Transactional
    public void processarFechamentoMatriculas() {
        List<Disciplina> disciplinas = disciplinaRepository.findAll();

        for (Disciplina d : disciplinas) {
            // Se tiver menos de 3 alunos efetivados, a disciplina é cancelada
            if (d.getAlunosMatriculados().size() < MIN_ALUNOS_ATIVACAO) {
                d.setStatus(StatusDisciplina.INATIVA);
                System.out.println("Disciplina " + d.getNome() + " CANCELADA por falta de alunos (Mínimo 3).");
            } else {
                d.setStatus(StatusDisciplina.ATIVA);
                System.out.println("Disciplina " + d.getNome() + " ATIVADA com sucesso!");
            }
            disciplinaRepository.save(d);
        }
    }
}