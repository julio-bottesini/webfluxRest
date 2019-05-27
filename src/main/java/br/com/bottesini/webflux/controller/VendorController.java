package br.com.bottesini.webflux.controller;

import br.com.bottesini.webflux.domain.Vendor;
import br.com.bottesini.webflux.service.VendorService;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(VendorController.BASE_VENDOR_URI)
public class VendorController {

    public static final String BASE_VENDOR_URI = "/api/v1/vendors";

    private VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public Flux<Vendor> list(){
        return vendorService.list();
    }

    @GetMapping("/{id}")
    public Mono<Vendor> getById(@PathVariable String id){
        return vendorService.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<Void> create(@RequestBody Publisher vendor){
        return vendorService.create(vendor);
    }

    @PutMapping("/{id}")
    public Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor){
        return vendorService.update(id, vendor);
    }
}
