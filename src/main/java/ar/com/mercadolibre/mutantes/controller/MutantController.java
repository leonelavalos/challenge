package ar.com.mercadolibre.mutantes.controller;

import ar.com.mercadolibre.mutantes.dto.DnaDTO;
import ar.com.mercadolibre.mutantes.service.MutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST})
@RequestMapping(value = "/api")
public class MutantController {

    @Autowired
    private MutantService mutantService;

    @PostMapping(value = "mutant", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> isMutant(@RequestBody DnaDTO dna) {
        try {
            if(mutantService.isMutant(dna.getDna()))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.toString());
        }
    }

    @GetMapping("stats")
    public ResponseEntity<?> getStats() {
        return ResponseEntity.status(HttpStatus.OK).body(mutantService.getStats());
    }
}
