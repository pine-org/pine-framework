package com.pineframework.core.spring.restapi.restcontroller;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

public class RestModel extends RepresentationModel<RestModel> {

    private final Object id;

    public RestModel(Object id, Link initialLink) {
        super(initialLink);
        this.id = id;
    }

    public Object getId() {
        return id;
    }
}
