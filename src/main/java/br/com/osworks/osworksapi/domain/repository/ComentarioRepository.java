package br.com.osworks.osworksapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.osworks.osworksapi.domain.model.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

}
