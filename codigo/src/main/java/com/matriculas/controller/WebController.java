package com.matriculas.controller;

import com.matriculas.model.Disciplina;
import com.matriculas.model.enums.StatusDisciplina;
import com.matriculas.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/disciplinas/lista")
    public String listaDisciplinas(Model model) {
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        return "disciplinas/lista";
    }

    // --- AJUSTE: Enviando uma disciplina vazia para o formulário preencher ---
    @GetMapping("/disciplinas/form")
    public String formDisciplina(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        return "disciplinas/form";
    }

    // --- NOVIDADE: Rota que recebe os dados do formulário e salva no banco! ---
    @PostMapping("/disciplinas/save")
    public String salvarDisciplina(Disciplina disciplina) {
        disciplina.setStatus(StatusDisciplina.ATIVA); // Define como ativa por padrão
        disciplina.setObrigatorio(true); // Define como obrigatória por padrão
        disciplinaRepository.save(disciplina);
        return "redirect:/disciplinas/lista"; // Redireciona de volta para a tabela após salvar
    }

    @GetMapping("/aluno/matriculas")
    public String matriculasAluno() {
        return "aluno/matriculas";
    }

    @GetMapping("/professor/alunos")
    public String alunosProfessor() {
        return "professor/alunos";
    }
}