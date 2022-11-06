package com.io.github.msj.mscurso.repository;

import com.io.github.msj.mscurso.model.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    List<Inscricao> findByIdCurso (Long idCurso);

}
