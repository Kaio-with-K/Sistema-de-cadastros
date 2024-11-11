package br.org.clavedesol.CrudJava.repository;

import br.org.clavedesol.CrudJava.entity.Instrumento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InstrumentoRepository extends JpaRepository<Instrumento, UUID> {
    // Método para buscar todos os instrumentos de um oficineiro
    public List<Instrumento> findByOficineiro_IdOficineiro(UUID oficineiroId);
}
