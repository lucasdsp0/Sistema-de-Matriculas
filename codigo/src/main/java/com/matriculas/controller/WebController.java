package com.matriculas.controller;

import com.matriculas.model.Aluno;
import com.matriculas.model.Disciplina;
import com.matriculas.model.Professor;
import com.matriculas.model.Usuario;
import com.matriculas.model.enums.StatusDisciplina;
import com.matriculas.repository.AlunoRepository;
import com.matriculas.repository.DisciplinaRepository;
import com.matriculas.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, 
                               HttpSession session, Model model) {
        // Busca o aluno por login
        Aluno aluno = alunoRepository.findAll().stream()
            .filter(a -> a.getLogin().equals(username) && a.getSenha().equals(password))
            .findFirst()
            .orElse(null);

        if (aluno != null) {
            // Armazena o usuário na sessão
            session.setAttribute("usuarioLogado", aluno);
            return "redirect:/aluno/matriculas";
        } else {
            model.addAttribute("erro", "Credenciais inválidas");
            return "login";
        }
    }

    @GetMapping("/disciplinas/lista")
    public String listaDisciplinas(Model model) {
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        return "disciplinas/lista";
    }

    
    @GetMapping("/disciplinas/form")
    public String formDisciplina(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        return "disciplinas/form";
    }

   
    @PostMapping("/disciplinas/save")
    public String salvarDisciplina(Disciplina disciplina) {
        disciplina.setStatus(StatusDisciplina.ATIVA); // Define como ativa por padrão
        disciplina.setObrigatorio(true); // Define como obrigatória por padrão
        disciplinaRepository.save(disciplina);
        return "redirect:/disciplinas/lista"; // Redireciona de volta para a tabela após salvar
    }

    @GetMapping("/aluno/matriculas")
    public String matriculasAluno(HttpSession session, Model model) {
        // Busca o aluno logado da sessão
        Aluno alunoLogado = (Aluno) session.getAttribute("usuarioLogado");
        
        if (alunoLogado != null) {
            // Busca o aluno do banco para evitar LazyInitializationException
            Aluno aluno = alunoRepository.findById(alunoLogado.getId()).orElse(null);
            if (aluno != null) {
                // Busca as matrículas do aluno (agora com inicialização adequada)
                model.addAttribute("matriculas", aluno.getMatriculas());
            }
        } else {
            // Se não houver usuário logado, redireciona para login
            return "redirect:/login";
        }
        
        return "aluno/matriculas";
    }

    @GetMapping("/aluno/lista")
    public String listaAlunos(Model model) {
        // Lista todos os alunos cadastrados no sistema
        model.addAttribute("alunos", alunoRepository.findAll());
        return "aluno/lista";
    }

    @GetMapping("/professor/alunos")
    public String alunosProfessor(HttpSession session, Model model) {
        // Busca o professor logado da sessão
        Professor professor = (Professor) session.getAttribute("usuarioLogado");
        
        if (professor != null) {
            // Busca os alunos do professor (através de suas disciplinas)
            model.addAttribute("professor", professor);
            // Os alunos serão renderizados através das disciplinas do professor
        } else {
            return "redirect:/login";
        }
        
        return "professor/alunos";
    }
}