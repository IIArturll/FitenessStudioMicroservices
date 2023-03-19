package it.academy.productservice.controllers;

import it.academy.productservice.core.nutrition.dtos.ProductDTO;
import it.academy.productservice.services.api.IProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final IProductService service;

    public ProductController(IProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody ProductDTO product) {
        service.add(product);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getPage(Pageable pageable){
        return ResponseEntity.status(200).body(service.getPage(pageable));
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?>update(@PathVariable(name = "uuid")UUID uuid,
                                   @PathVariable(name = "dt_update") Instant dtUpdate,
                                   @Valid @RequestBody ProductDTO productDTO) {
        service.update(uuid,dtUpdate,productDTO);
        return ResponseEntity.status(200).build();
    }
}
