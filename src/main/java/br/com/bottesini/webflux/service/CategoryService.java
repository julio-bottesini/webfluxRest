package br.com.bottesini.webflux.service;

import br.com.bottesini.webflux.domain.Category;
import br.com.bottesini.webflux.repository.CategoryRepository;
import org.reactivestreams.Publisher;
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

    public Mono<Void> create(Publisher category) {
        return categoryRepository.saveAll(category).then();
    }

    public Mono<Category> update(String id, Category category) {

        if(categoryRepository.findById(id).block() != null) {
            category.setId(id);
            return categoryRepository.save(category);
        }

        return null;
    }

    public Mono<Category> partialUpdate(String id, Category category) {

        Category foundCategory = categoryRepository.findById(id).block();

        if(foundCategory != null && !foundCategory.getDescription().equals(category.getDescription())) {

            foundCategory.setDescription(category.getDescription());
            return categoryRepository.save(foundCategory);
        }

        return Mono.just(foundCategory);
    }
}
