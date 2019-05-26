package br.com.bottesini.webflux.bootstrap;

import br.com.bottesini.webflux.domain.Category;
import br.com.bottesini.webflux.repository.VendorRepository;
import br.com.bottesini.webflux.repository.CategoryRepository;
import br.com.bottesini.webflux.domain.Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public void run(String... args) throws Exception {

        if(categoryRepository.count().block() == 0){
            System.out.println("##### Loading category data...");

            categoryRepository.save(Category.builder().description("Category0").build()).block();
            categoryRepository.save(Category.builder().description("Category1").build()).block();
        }

        System.out.println("Total Categories: "+categoryRepository.count().block());

        if(vendorRepository.count().block() == 0){
            System.out.println("##### Loading vendor data...");

            vendorRepository.save(Vendor.builder().firstName("Julio").lastName("Bottesini").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Mark").lastName("Mayer").build()).block();
        }

        System.out.println("Total Vendors: "+vendorRepository.count().block());
    }
}
