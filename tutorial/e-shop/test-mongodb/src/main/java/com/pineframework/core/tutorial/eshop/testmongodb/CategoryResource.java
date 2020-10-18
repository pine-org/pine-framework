package com.pineframework.core.tutorial.eshop.testmongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categories")
public class CategoryResource {

    @Autowired
    private CategoryOgmRepository repository;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody CategoryEntity entity) {
        Long id = repository.save(entity);
        return ResponseEntity.ok(id);
    }

}
