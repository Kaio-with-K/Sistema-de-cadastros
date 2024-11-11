package br.org.clavedesol.CrudJava.controller;

import br.org.clavedesol.CrudJava.entity.Instrumento;
import br.org.clavedesol.CrudJava.service.InstrumentoService;
import br.org.clavedesol.CrudJava.service.OficineiroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/instrumentos")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Permite requisições do front-end
public class InstrumentoController {

    private final InstrumentoService instrumentoService;
    private final OficineiroService oficineiroService;

    public InstrumentoController(InstrumentoService instrumentoService, OficineiroService oficineiroService) {
        this.instrumentoService = instrumentoService;
        this.oficineiroService = oficineiroService;
    }

    @PostMapping
    public ResponseEntity<Instrumento> createInstrumento(@RequestBody CreateInstrumentoDto createInstrumentoDto) {
        var instrumentoId = instrumentoService.createInstrumento(createInstrumentoDto); // Cria o instrumento
        var instrumentoCriado = instrumentoService.getInstrumentoById(instrumentoId.toString()).orElseThrow(() -> new RuntimeException("Instrumento não encontrado"));
        return ResponseEntity.status(HttpStatus.CREATED).body(instrumentoCriado); // Retorna o instrumento criado
    }


    @GetMapping("/{instrumentoId}") // Adicionada a barra inicial
    public ResponseEntity<Instrumento> getInstrumento(@PathVariable("instrumentoId") String instrumentoId) {
        var instrumento = instrumentoService.getInstrumentoById(instrumentoId);
        if (instrumento.isPresent()) {
            return ResponseEntity.ok(instrumento.get());
        } else  {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Instrumento>> listInstrumentos() {
        var instrumentos = instrumentoService.listInstrumentos();
        return ResponseEntity.ok(instrumentos);
    }

    @PutMapping("/{instrumentoId}")
    public ResponseEntity<Instrumento> updateInstrumentoById(@PathVariable("instrumentoId") String instrumentoId,
                                                             @RequestBody UpdateInstrumentoDto updateInstrumentoDto) {
        instrumentoService.updateInstrumentoById(instrumentoId, updateInstrumentoDto);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{instrumentoId}")
    public ResponseEntity<Void> deleteInstrumentoById(@PathVariable("instrumentoId") String instrumentoId) {
        instrumentoService.deleteInstrumento(instrumentoId);
        return ResponseEntity.noContent().build();
    }
}
