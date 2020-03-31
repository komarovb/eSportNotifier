package com.komarov.patel.research.methodology.esportservice.model;

import lombok.Data;

@Data
public class Team {
    private long id;
    private String name;
    private String imageUrl;

    public Team(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Team(long id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String toString() {
        return this.name;
    }
}
