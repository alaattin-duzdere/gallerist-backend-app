package com.example.gallerist.model;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class OwnableEntity extends BaseEntity{

    @ManyToOne
    private User owner;

    public Long getOwnerId() {
        return owner.getId();
    }
}
