package br.com.bottesini.webflux.service;

import br.com.bottesini.webflux.domain.Vendor;
import br.com.bottesini.webflux.repository.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class VendorService {

    private VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public Flux<Vendor> list(){
        return vendorRepository.findAll();
    }

    public Mono<Vendor> getById(String id){
        return vendorRepository.findById(id);
    }

    public Mono<Void> create(Publisher category) {
        return vendorRepository.saveAll(category).then();
    }

    public Mono<Vendor> update(String id, Vendor vendor) {

        if(vendorRepository.findById(id).block() != null) {
            vendor.setId(id);
            return vendorRepository.save(vendor);
        }

        return null;
    }

    public Mono<Vendor> partialUpdate(String id, Vendor vendor) {

        Vendor foundVendor = vendorRepository.findById(id).block();

        if(foundVendor != null && !isVendorsInfoTheSame(vendor, foundVendor)) {

            foundVendor.setFirstName(vendor.getFirstName());
            foundVendor.setLastName(vendor.getLastName());
            return vendorRepository.save(foundVendor);
        }

        return Mono.just(foundVendor);
    }

    private boolean isVendorsInfoTheSame(Vendor vendor, Vendor foundVendor) {
        return foundVendor.getFirstName().equals(vendor.getFirstName()) &&
           foundVendor.getLastName().equals(vendor.getLastName());
    }
}
