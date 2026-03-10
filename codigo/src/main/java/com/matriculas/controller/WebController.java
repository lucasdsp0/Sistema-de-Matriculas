package com.matriculas.controller;

import com.matriculas.model.*;
import com.matriculas.model.enums.StatusDisciplina;
import com.matriculas.model.enums.StatusMatricula;
import com.matriculas.repository.*;
import com.matriculas.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private SecretariaRepository secretariaRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private CurriculoRepository curriculoRepository;

    @Autowired
    private MatriculaService matriculaService;

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
        // Tenta buscar como Aluno
        Aluno aluno = alunoRepository.findAll().stream()
            .filter(a -> a.getLogin().equals(username) && a.getSenha().equals(password))
            .findFirst()
            .orElse(null);

        if (aluno != null) {
            session.setAttribute("usuarioLogado", aluno);
            session.setAttribute("tipoUsuario", "ALUNO");
            return "redirect:/aluno/matriculas";
        }

        // Tenta buscar como Professor
        Professor professor = professorRepository.findAll().stream()
            .filter(p -> p.getLogin().equals(username) && p.getSenha().equals(password))
            .findFirst()
            .orElse(null);

        if (professor != null) {
            session.setAttribute("usuarioLogado", professor);
            session.setAttribute("tipoUsuario", "PROFESSOR");
            return "redirect:/professor/alunos";
        }

        // Tenta buscar como Secretaria
        Secretaria secretaria = secretariaRepository.findAll().stream()
            .filter(s -> s.getLogin().equals(username) && s.getSenha().equals(password))
            .findFirst()
            .orElse(null);

        if (secretaria != null) {
            session.setAttribute("usuarioLogado", secretaria);
            session.setAttribute("tipoUsuario", "SECRETARIA");
            return "redirect:/";
        }

        model.addAttribute("erro", "Credenciais inválidas");
        return "login";
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
            // Busca o professor atualizado do banco
            Professor professorAtualizado = professorRepository.findById(professor.getId()).orElse(null);
            if (professorAtualizado != null) {
                model.addAttribute("professor", professorAtualizado);
                model.addAttribute("isciplinas", professorAtualizado.getDisciplinas());
            }
        } else {
            return "redirect:/login";
        }
        
        return "professor/alunos";
    }

    // ========== ENDPOINTS DE CADASTRO ==========

    @GetMapping("/aluno/cadastro")
    public String formCadastroAluno(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "aluno/cadastro";
    }

    @PostMapping("/aluno/save")
    public String salvarAluno(@ModelAttribute Aluno aluno, Model model) {
        try {
            // Valida se login já existe
            boolean loginExiste = alunoRepository.findAll().stream()
                .anyMatch(a -> a.getLogin().equals(aluno.getLogin()));
            
            if (loginExiste) {
                model.addAttribute("erro", "Login já cadastrado no sistema!");
                model.addAttribute("aluno", aluno);
                return "aluno/cadastro";
            }

            if (aluno.getNumMatricula() == null || aluno.getNumMatricula() == 0) {
                model.addAttribute("erro", "Número de matrícula é obrigatório!");
                model.addAttribute("aluno", aluno);
                return "aluno/cadastro";
            }

            alunoRepository.save(aluno);
            model.addAttribute("sucesso", "Aluno cadastrado com sucesso!");
            return "redirect:/aluno/lista";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao cadastrar aluno: " + e.getMessage());
            model.addAttribute("aluno", aluno);
            return "aluno/cadastro";
        }
    }

    @GetMapping("/professor/cadastro")
    public String formCadastroProfessor(Model model) {
        model.addAttribute("professor", new Professor());
        return "professor/cadastro";
    }

    @PostMapping("/professor/save")
    public String salvarProfessor(@ModelAttribute Professor professor, Model model) {
        try {
            // Valida se login já existe
            boolean loginExiste = professorRepository.findAll().stream()
                .anyMatch(p -> p.getLogin().equals(professor.getLogin()));
            
            if (loginExiste) {
                model.addAttribute("erro", "Login já cadastrado no sistema!");
                model.addAttribute("professor", professor);
                return "professor/cadastro";
            }

            if (professor.getNumCadastro() == null || professor.getNumCadastro() == 0) {
                model.addAttribute("erro", "Número de cadastro é obrigatório!");
                model.addAttribute("professor", professor);
                return "professor/cadastro";
            }

            professorRepository.save(professor);
            model.addAttribute("sucesso", "Professor cadastrado com sucesso!");
            return "redirect:/aluno/lista";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao cadastrar professor: " + e.getMessage());
            model.addAttribute("professor", professor);
            return "professor/cadastro";
        }
    }

    // ========== ENDPOINTS DE MATRÍCULA ==========

    @GetMapping("/matricula/formulario")
    public String formularioMatricula(HttpSession session, Model model) {
        Aluno alunoLogado = (Aluno) session.getAttribute("usuarioLogado");
        
        if (alunoLogado == null) {
            return "redirect:/login";
        }

        // Carrega o aluno com suas matrículas
        Aluno aluno = alunoRepository.findById(alunoLogado.getId()).orElse(null);
        if (aluno != null) {
            model.addAttribute("aluno", aluno);
            model.addAttribute("disciplinas", disciplinaRepository.findAll());
        }

        return "matricula/formulario";
    }

    @PostMapping("/matricula/efetuar")
    public String efetuarMatricula(@RequestParam List<Long> disciplinaIds, 
                                   HttpSession session, Model model) {
        try {
            Aluno alunoLogado = (Aluno) session.getAttribute("usuarioLogado");
            if (alunoLogado == null) {
                return "redirect:/login";
            }

            Aluno aluno = alunoRepository.findById(alunoLogado.getId()).orElse(null);
            if (aluno == null) {
                model.addAttribute("erro", "Aluno não encontrado!");
                return "redirect:/matricula/formulario";
            }

            List<Disciplina> disciplinas = new ArrayList<>();
            for (Long id : disciplinaIds) {
                Disciplina d = disciplinaRepository.findById(id).orElse(null);
                if (d != null) {
                    disciplinas.add(d);
                }
            }

            if (disciplinas.isEmpty()) {
                model.addAttribute("erro", "Selecione pelo menos uma disciplina!");
                return "redirect:/matricula/formulario";
            }

            Matricula matricula = new Matricula(aluno, StatusMatricula.EFETIVADA);
            matricula.setDisciplinas(disciplinas);

            // Executa a validação de negócio
            Matricula matriculaSalva = matriculaService.efetuarMatricula(matricula);
            
            model.addAttribute("sucesso", "Matrícula efetivada com sucesso!");
            return "redirect:/aluno/matriculas";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao efetuar matrícula: " + e.getMessage());
            return "redirect:/matricula/formulario";
        }
    }

    @PostMapping("/matricula/cancelar/{matriculaId}")
    public String cancelarMatricula(@PathVariable Long matriculaId, Model model) {
        try {
            Matricula matricula = matriculaRepository.findById(matriculaId).orElse(null);
            if (matricula != null) {
                matricula.setStatus(StatusMatricula.CANCELADA);
                matriculaRepository.save(matricula);
                model.addAttribute("sucesso", "Matrícula cancelada com sucesso!");
            }
            return "redirect:/aluno/matriculas";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao cancelar matrícula: " + e.getMessage());
            return "redirect:/aluno/matriculas";
        }
    }

    // ========== ENDPOINTS DE CURRÍCULO ==========

    @GetMapping("/curriculo/form")
    public String formCurriculo(Model model) {
        model.addAttribute("curriculo", new Curriculo());
        model.addAttribute("disciplinas", disciplinaRepository.findAll());
        return "curriculo/form";
    }

    @PostMapping("/curriculo/save")
    public String salvarCurriculo(@ModelAttribute Curriculo curriculo, 
                                  @RequestParam(required = false) List<Long> disciplinaIds,
                                  Model model) {
        try {
            if (curriculo.getSemestre() == null || curriculo.getSemestre().isEmpty()) {
                model.addAttribute("erro", "Semestre é obrigatório!");
                model.addAttribute("curriculo", curriculo);
                model.addAttribute("disciplinas", disciplinaRepository.findAll());
                return "curriculo/form";
            }

            if (curriculo.getDataInicio() == null) {
                curriculo.setDataInicio(LocalDate.now());
            }

            // Adiciona as disciplinas selecionadas
            if (disciplinaIds != null && !disciplinaIds.isEmpty()) {
                for (Long id : disciplinaIds) {
                    Disciplina d = disciplinaRepository.findById(id).orElse(null);
                    if (d != null) {
                        curriculo.addDisciplina(d);
                    }
                }
            }

            curriculoRepository.save(curriculo);
            model.addAttribute("sucesso", "Currículo gerado com sucesso!");
            return "redirect:/curriculo/lista";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao salvar currículo: " + e.getMessage());
            model.addAttribute("curriculo", curriculo);
            model.addAttribute("disciplinas", disciplinaRepository.findAll());
            return "curriculo/form";
        }
    }

    @GetMapping("/curriculo/lista")
    public String listaCurriculos(Model model) {
        model.addAttribute("curriculos", curriculoRepository.findAll());
        return "curriculo/lista";
    }

    // ========== ENDPOINTS DE PERÍODO DE MATRÍCULA ==========

    @GetMapping("/periodo/encerrar")
    public String formEncerrarPeriodo(Model model) {
        model.addAttribute("curriculos", curriculoRepository.findAll());
        return "periodo/encerrar";
    }

    @PostMapping("/periodo/encerrar")
    public String encerrarPeriodoMatricula(Model model) {
        try {
            // Processa o fechamento das matrículas
            // Verifica quais disciplinas devem ser canceladas (< 3 alunos)
            List<Disciplina> disciplinas = disciplinaRepository.findAll();
            int disciplinasDesativadas = 0;
            
            for (Disciplina d : disciplinas) {
                if (d.getStatus() == StatusDisciplina.ATIVA) {
                    int alunosMatriculados = d.getAlunosMatriculados().size();
                    
                    if (alunosMatriculados < 3) {
                        d.setStatus(StatusDisciplina.INATIVA);
                        disciplinaRepository.save(d);
                        disciplinasDesativadas++;
                        System.out.println("Disciplina '" + d.getNome() + "' DESATIVADA: apenas " + alunosMatriculados + " aluno(s)");
                    }
                }
            }
            
            model.addAttribute("sucesso", "Período de matrícula encerrado! " + disciplinasDesativadas + " disciplina(s) desativada(s).");
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao encerrar período: " + e.getMessage());
            model.addAttribute("curriculos", curriculoRepository.findAll());
            return "periodo/encerrar";
        }
    }

    // ========== ENDPOINTS DE SECRETARIA ==========

    @GetMapping("/secretaria/cadastro")
    public String formCadastroSecretaria(Model model) {
        model.addAttribute("secretaria", new Secretaria());
        return "secretaria/cadastro";
    }

    @PostMapping("/secretaria/save")
    public String salvarSecretaria(@ModelAttribute Secretaria secretaria, Model model) {
        try {
            boolean loginExiste = secretariaRepository.findAll().stream()
                .anyMatch(s -> s.getLogin().equals(secretaria.getLogin()));
            
            if (loginExiste) {
                model.addAttribute("erro", "Login já cadastrado!");
                model.addAttribute("secretaria", secretaria);
                return "secretaria/cadastro";
            }

            secretariaRepository.save(secretaria);
            model.addAttribute("sucesso", "Secretária cadastrada com sucesso!");
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao cadastrar: " + e.getMessage());
            model.addAttribute("secretaria", secretaria);
            return "secretaria/cadastro";
        }
    }
}