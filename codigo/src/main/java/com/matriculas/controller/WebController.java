package com.matriculas.controller;

import com.matriculas.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    // Rota para a Home (Dashboard)
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Rota para a tela de Login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Rota para a lista de Disciplinas
    @GetMapping("/disciplinas/lista")
    public String listaDisciplinas(Model model) {
        // Envia a lista real do banco de dados para a tela
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        return "disciplinas/lista";
    }

    // Rota para o formulário de Nova Disciplina (Secretaria)
    @GetMapping("/disciplinas/form")
    public String formDisciplina() {
        return "disciplinas/form";
    }

    // Rota para o painel de Matrículas do Aluno
    @GetMapping("/aluno/matriculas")
    public String matriculasAluno() {
        return "aluno/matriculas";
    }

    // Rota para o painel de Alunos do Professor
    @GetMapping("/professor/alunos")
    public String alunosProfessor() {
        return "professor/alunos";
    }
}