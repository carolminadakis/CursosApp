package com.cursosapp.repostitory;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cursosapp.models.Cursos;

@Repository
public interface CursosRepository extends CrudRepository<Cursos, String>{
	
	Cursos findByCodigo(long codigo);

}
