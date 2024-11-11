package br.org.clavedesol.CrudJava.service;

import br.org.clavedesol.CrudJava.controller.CreateOficineiroDto;
import br.org.clavedesol.CrudJava.controller.UpdateOficineiroDto;
import br.org.clavedesol.CrudJava.entity.Oficineiro;
import br.org.clavedesol.CrudJava.repository.InstrumentoRepository;
import br.org.clavedesol.CrudJava.repository.OficineiroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OficineiroService {

    private final InstrumentoRepository instrumentoRepository;
    private final OficineiroRepository oficineiroRepository;

    public OficineiroService(OficineiroRepository oficineiroRepository, InstrumentoRepository instrumentoRepository) {
        this.oficineiroRepository = oficineiroRepository;
        this.instrumentoRepository = instrumentoRepository;
    }

    public UUID createOficineiro(CreateOficineiroDto createOficineiroDto) {
        // DTO -> ENTITY
        var entity = new Oficineiro(
                UUID.randomUUID(),
                createOficineiroDto.nomeOficineiro(),
                createOficineiroDto.idadeOficineiro(),
                createOficineiroDto.tempoEmpresa(),
                createOficineiroDto.fotoOficineiro(),
                null); // Aqui passamos null, já que o instrumento será adicionado depois

        var oficineiroSaved = oficineiroRepository.save(entity);
        return oficineiroSaved.getIdOficineiro();
    }

    public Optional<Oficineiro> getOficineiroById(String oficineiroId) {
        return oficineiroRepository.findById(UUID.fromString(oficineiroId));
    }

    public List<Oficineiro> listOficineiros() {
        return oficineiroRepository.findAll();
    }

    public void updateOficineiroById(String oficineiroId, UpdateOficineiroDto updateOficineiroDto) {
        var oficineiroExists = oficineiroRepository.findById(UUID.fromString(oficineiroId));
        if (oficineiroExists.isPresent()) {
            var oficineiro = oficineiroExists.get();

            // Acesse os dados com getters em vez de setters para a atualização
            if (updateOficineiroDto.getNomeOficineiro() != null) {
                oficineiro.setNomeOficineiro(updateOficineiroDto.getNomeOficineiro());
            }

            if (updateOficineiroDto.getIdadeOficineiro() > 0) {
                oficineiro.setIdadeOficineiro(updateOficineiroDto.getIdadeOficineiro());
            }

            if (updateOficineiroDto.getTempoEmpresa() > 0) {
                oficineiro.setTempoEmpresa(updateOficineiroDto.getTempoEmpresa());
            }

            if (updateOficineiroDto.getFotoOficineiro() != null) {
                oficineiro.setFotoOficineiro(updateOficineiroDto.getFotoOficineiro());
            }

            // Salve o oficineiro após as atualizações
            oficineiroRepository.save(oficineiro);
        }
    }

    public void deleteOficineiro(String oficineiroId) {
        var oficineiroExists = oficineiroRepository.existsById(UUID.fromString(oficineiroId));
        if (oficineiroExists) {
            oficineiroRepository.deleteById(UUID.fromString(oficineiroId));
        }
    }
}
