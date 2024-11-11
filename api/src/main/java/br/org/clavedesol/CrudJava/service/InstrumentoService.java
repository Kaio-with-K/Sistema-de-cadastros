package br.org.clavedesol.CrudJava.service;

import br.org.clavedesol.CrudJava.controller.CreateInstrumentoDto;
import br.org.clavedesol.CrudJava.controller.InstrumentoDTO;  // Importando o DTO
import br.org.clavedesol.CrudJava.entity.Instrumento;
import br.org.clavedesol.CrudJava.entity.Oficineiro;
import br.org.clavedesol.CrudJava.repository.InstrumentoRepository;
import br.org.clavedesol.CrudJava.repository.OficineiroRepository;
import org.springframework.stereotype.Service;
import br.org.clavedesol.CrudJava.controller.UpdateInstrumentoDto;  // Importando o UpdateInstrumentoDto

import java.util.List;
import java.util.Optional;  // Importando Optional
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InstrumentoService {

    private final InstrumentoRepository instrumentoRepository;
    private final OficineiroRepository oficineiroRepository;

    public InstrumentoService(InstrumentoRepository instrumentoRepository, OficineiroRepository oficineiroRepository) {
        this.instrumentoRepository = instrumentoRepository;
        this.oficineiroRepository = oficineiroRepository;
    }

    public UUID createInstrumento(CreateInstrumentoDto createInstrumentoDto) {
        Oficineiro oficineiro = oficineiroRepository.findById(UUID.fromString(createInstrumentoDto.oficineiroId()))
                .orElseThrow(() -> new RuntimeException("Oficineiro não encontrado"));

        var entity = new Instrumento(
                UUID.randomUUID(),
                createInstrumentoDto.nomeInstrumento(),
                createInstrumentoDto.tempoAprendizado(),
                createInstrumentoDto.fotoInstrumento(),
                oficineiro
        );

        var instrumentoSaved = instrumentoRepository.save(entity);
        return instrumentoSaved.getIdInstrumento();
    }

    public Optional<Instrumento> getInstrumentoById(String instrumentoId) {
        return instrumentoRepository.findById(UUID.fromString(instrumentoId));
    }

    public List<Instrumento> listInstrumentos() {
        return instrumentoRepository.findAll();
    }

    // Método que retorna uma lista de InstrumentoDTO ao invés de Instrumento
    public List<InstrumentoDTO> getInstrumentosByOficineiro(UUID oficineiroId) {
        List<Instrumento> instrumentos = instrumentoRepository.findByOficineiro_IdOficineiro(oficineiroId);
        return instrumentos.stream()
                .map(instrumento -> new InstrumentoDTO(instrumento))  // Mapeando para InstrumentoDTO
                .collect(Collectors.toList());
    }

    public void updateInstrumentoById(String instrumentoId, UpdateInstrumentoDto updateInstrumentoDto) {
        var instrumentoExist = instrumentoRepository.findById(UUID.fromString(instrumentoId));
        if (instrumentoExist.isPresent()) {
            var instrumento = instrumentoExist.get();
            if (updateInstrumentoDto.nomeInstrumento() != null) {
                instrumento.setNomeInstrumento(updateInstrumentoDto.nomeInstrumento());
            }

            if (updateInstrumentoDto.tempoAprendizado() > 0) {
                instrumento.setTempoAprendizado(updateInstrumentoDto.tempoAprendizado());
            }

            if (updateInstrumentoDto.fotoInstrumento() != null) {
                instrumento.setFotoInstrumento(updateInstrumentoDto.fotoInstrumento());
            }

            if (updateInstrumentoDto.oficineiroId() != null) {
                Optional<Oficineiro> oficineiroOptional = oficineiroRepository.findById(UUID.fromString(updateInstrumentoDto.oficineiroId()));
                if (oficineiroOptional.isPresent()) {
                    instrumento.setOficineiro(oficineiroOptional.get());
                } else {
                    throw new RuntimeException("Oficineiro não encontrado");
                }
            }
            instrumentoRepository.save(instrumento);
        }
    }

    public void deleteInstrumento(String instrumentoId) {
        var instrumentoExist = instrumentoRepository.existsById(UUID.fromString(instrumentoId));
        if (instrumentoExist) {
            instrumentoRepository.deleteById(UUID.fromString(instrumentoId));
        }
    }
}
