package com.cursosapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cursosapp.models.Aluno;
import com.cursosapp.models.Cursos;
import com.cursosapp.repostitory.AlunoRepository;
import com.cursosapp.repostitory.CursosRepository;

@Controller
public class CursosController {

	@Autowired
	private CursosRepository cr;

	@Autowired
	private AlunoRepository ar;

//O método abaixo recebe as informações que declaramos na página html formCursos, e retorna a página do cadastramento.
	
	@RequestMapping(value = "/cadastrarCursos", method = RequestMethod.GET)
	public String form() {
		return "cursos/formCursos";

	}

//O método abaixo salva o cadastramento do curso, validando os campos e
//	redireciona a página para a página de cadastramento novamente.
	
	@RequestMapping(value = "/cadastrarCursos", method = RequestMethod.POST)
	public String form(@Valid Cursos curso, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique se completou todos os campos.");
			return "redirect:/cadastrarCursos";
		}
		cr.save(curso);
		attributes.addFlashAttribute("mensagem", "Curso adicionado com sucesso.");
		return "redirect:/cadastrarCursos";
	}

//O método abaixo faz a busca no banco de dados e a impressão das informações contidas nele
//Usamos o ModelAndView para que consigamos printar para o usuário a lista de cursos
//E usamos o Iterable para que consigamos iterar a lista de cursos, encontrando todos e printando na tela do usuário.
	
	@RequestMapping("/listaCursos")
	public ModelAndView listaCursos() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Cursos> crs = cr.findAll();
		mv.addObject("cursos", crs);
		return mv;
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesCurso(@PathVariable("codigo") long codigo) {
		Cursos curso = cr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("cursos/detalhesCurso");
		mv.addObject("cursos", curso);

		Iterable<Aluno> aluno = ar.findByCurso(curso);
		mv.addObject("alunos", aluno);
		return mv;
	}
	
	@RequestMapping("/deletaCurso")
	public String deletaCurso(long codigo) {
		Cursos curso = cr.findByCodigo(codigo);
		cr.delete(curso);
		return "redirect:/listaCursos";
	}

	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String detalhesCursoPost(@PathVariable("codigo") long codigo, @Valid Aluno aluno, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique se completou todos os campos.");
			return "redirect:/{codigo}";
		}
		Cursos curso = cr.findByCodigo(codigo);
		aluno.setCurso(curso);
		ar.save(aluno);
		attributes.addFlashAttribute("mensagem", "Aluno adicionado com sucesso.");
		return "redirect:/{codigo}";
	}
	
	@RequestMapping("/deletaAluno")
	public String deletaAluno(Long cpf) {
		Aluno aluno = ar.findByCpf(cpf);
		ar.delete(aluno);
		
		Cursos curso = aluno.getCurso();
		long recebeCodigo = curso.getCodigo();
		return "redirect:/" + recebeCodigo;
	}
}
