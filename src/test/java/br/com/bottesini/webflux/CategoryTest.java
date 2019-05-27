package br.com.bottesini.webflux;

import br.com.bottesini.webflux.controller.CategoryController;
import br.com.bottesini.webflux.domain.Category;
import br.com.bottesini.webflux.repository.CategoryRepository;
import br.com.bottesini.webflux.service.CategoryService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CategoryTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryService categoryService;
    CategoryController categoryController;

    @Before
    public void setup(){
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryService = new CategoryService(categoryRepository);
        categoryController = new CategoryController(categoryService);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void list() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(
                        Flux.just(
                                Category.builder().description("Cat1").build(),
                                Category.builder().description("Cat2").build()));

        webTestClient.get()
                .uri(CategoryController.BASE_CATEGORY_URI)
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getById() {
        BDDMockito.given(categoryRepository.findById("someId"))
                .willReturn(Mono.just(Category.builder().description("desc").build()));

        webTestClient.get()
                .uri(CategoryController.BASE_CATEGORY_URI+"/someId")
                .exchange()
                .expectBody(Category.class);
    }
}