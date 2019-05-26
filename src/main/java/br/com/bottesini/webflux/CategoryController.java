package br.com.bottesini.webflux;

import br.com.bottesini.webflux.domain.Category;
import br.com.bottesini.webflux.service.CategoryService;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(CategoryController.BASE_CATEGORY_URI)
public class CategoryController {

    public static final String BASE_CATEGORY_URI = "/api/v1/categories";

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public Flux<Category> list(){
        return categoryService.list();
    }

    @GetMapping("/{id}")
    public Mono<Category> getById(@PathVariable String id){
        return categoryService.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<Void> create(@RequestBody Publisher category){
        return categoryService.create(category);
    }
}
