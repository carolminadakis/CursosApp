package com.cursosapp.repostitory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cursosapp.models.Aluno;
import com.cursosapp.models.Cursos;

@Repository
public interface AlunoRepository extends CrudRepository<Aluno, String>{
	Iterable<Aluno> findByCurso(Cursos curso);
	Aluno findByCpf(Long cpf);
}
