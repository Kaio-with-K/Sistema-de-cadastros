package br.org.clavedesol.CrudJava.controller;

import br.org.clavedesol.CrudJava.entity.Oficineiro;
import br.org.clavedesol.CrudJava.service.OficineiroService;
import br.org.clavedesol.CrudJava.service.InstrumentoService;  // Importando o InstrumentoService
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;  // Adicionando a importação de HttpStatus

@RestController
@RequestMapping("/oficineiros")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Permite requisições do front-end
public class OficineiroController {

    private final OficineiroService oficineiroService;
    private final InstrumentoService instrumentoService;  // Declarando a variável instrumentoService

    // Injeção de dependências no construtor
    public OficineiroController(OficineiroService oficineiroService, InstrumentoService instrumentoService) {
        this.oficineiroService = oficineiroService;
        this.instrumentoService = instrumentoService;  // Inicializando a variável instrumentoService
    }

    @PostMapping
    public ResponseEntity<Oficineiro> createOficineiro(@RequestBody CreateOficineiroDto createOficineiroDto) {
        var oficineiroId = oficineiroService.createOficineiro(createOficineiroDto);
        var oficineiroCriado = oficineiroService.getOficineiroById(oficineiroId.toString())
                .orElseThrow(() -> new RuntimeException("Oficineiro não encontrado"));
        return ResponseEntity.status(HttpStatus.CREATED).body(oficineiroCriado);
    }


    @GetMapping("/{idOficineiro}")
    public ResponseEntity<Oficineiro> getOficineiro(@PathVariable("idOficineiro") String idOficineiro) {
        var oficineiro = oficineiroService.getOficineiroById(idOficineiro);
        if (oficineiro.isPresent()) {
            return ResponseEntity.ok(oficineiro.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<OficineiroDTO>> listOficineiros() {
        List<Oficineiro> oficineiros = oficineiroService.listOficineiros();
        List<OficineiroDTO> oficineiroDTOs = oficineiros.stream()
                .map(oficineiro -> {
                    // Agora a construção de OficineiroDTO não necessita mais de lista de InstrumentoDTO
                    return new OficineiroDTO(oficineiro); // Usando o construtor correto
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(oficineiroDTOs);
    }

    @PutMapping("/{idOficineiro}")
    public ResponseEntity<Void> updateOficineiroById(@PathVariable("idOficineiro") String idOficineiro,
                                                     @RequestBody UpdateOficineiroDto updateOficineiroDto) {
        oficineiroService.updateOficineiroById(idOficineiro, updateOficineiroDto);
        return ResponseEntity.noContent().build();
    }





    @DeleteMapping("/{idOficineiro}")
    public ResponseEntity<Void> deleteOficineiroById(@PathVariable("idOficineiro") String idOficineiro) {
        oficineiroService.deleteOficineiro(idOficineiro);
        return ResponseEntity.noContent().build();
    }
}
