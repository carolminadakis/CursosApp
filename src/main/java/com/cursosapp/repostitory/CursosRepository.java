package com.cursosapp.repostitory;

import org.springframework.data.repository.CrudRepository;

import com.cursosapp.models.Cursos;

public interface CursosRepository extends CrudRepository<Cursos, String>{
	
	Cursos findByCodigo(long codigo);

}
