package com.cursosapp.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Aluno {

	@Id
	private Long cpf;
	private String nomeAluno;
	
	@ManyToOne
	private Cursos curso;
	
	public Long getCpf() {
		return cpf;
	}
	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}
	public String getNomeAluno() {
		return nomeAluno;
	}
	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}
	
	public Cursos getCurso() {
		return curso;
	}
	public void setCurso(Cursos curso) {
		this.curso = curso;
	}
}
