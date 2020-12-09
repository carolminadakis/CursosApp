package com.cursosapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cursosapp.models.Aluno;
import com.cursosapp.models.Cursos;
import com.cursosapp.repostitory.AlunoRepository;
import com.cursosapp.repostitory.CursosRepository;

@Controller
public class CursosController {

	@Autowired
	private CursosRepository er;
	
	@Autowired
	private AlunoRepository ar;
	
	
//O método abaixo recebe as informações que declaramos na página html formCursos, e retorna a página do cadastramento.
	@RequestMapping(value="/cadastrarCursos", method=RequestMethod.GET)
	public String form() {
		return "cursos/formCursos";
		
	}
		

//O método abaixo salva o cadastramento do curso e redireciona a página para a página de cadastramento novamente.
		@RequestMapping(value="/cadastrarCursos", method=RequestMethod.POST)
		public String form(Cursos curso) {
			
			er.save(curso);
			return "redirect:/cadastrarCursos";
		}
		
//O método abaixo faz a busca no banco de dados e a impressão das informações contidas nele
//Usamos o ModelAndView para que consigamos printar para o usuário a lista de cursos
//E usamos o Iterable para que consigamos iterar a lista de cursos, encontrando todos e printando na tela do usuário.
		@RequestMapping("/listaCursos")
		public ModelAndView listaCursos() {
			ModelAndView mv = new ModelAndView("index");
			Iterable<Cursos> crs = er.findAll();
			mv.addObject("cursos", crs);
			return mv;
		}
		
		@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
		public ModelAndView detalhesCurso(@PathVariable("codigo") long codigo) {
			Cursos curso = er.findByCodigo(codigo);
			ModelAndView mv = new ModelAndView("cursos/detalhesCurso");
			mv.addObject("cursos", curso);
//			
//			Iterable<Aluno> aluno = ar.findByCursos(curso);
//			mv.addObject("aluno", aluno);
			return mv;
		}
		@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
		public String detalhesCursoPost(@PathVariable("codigo") long codigo, Aluno aluno) {
			Cursos curso = er.findByCodigo(codigo);
			aluno.setCurso(curso);
			ar.save(aluno);
			return "redirect:/{codigo}";
		}
}
