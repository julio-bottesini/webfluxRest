package br.com.bottesini.webflux.service;

import br.com.bottesini.webflux.domain.Category;
import br.com.bottesini.webflux.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Flux<Category> list(){
        return categoryRepository.findAll();
    }

    public Mono<Category> getById(String id){
        return categoryRepository.findById(id);
    }
}
