package com.example.gallerist.utils;

import com.example.gallerist.model.OwnableEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


@Component
public class RepositoryResolver {

    private final ApplicationContext context;

    public RepositoryResolver(ApplicationContext context) {
        this.context = context;
    }

    @SuppressWarnings("unchecked")
    public <T extends OwnableEntity, ID> JpaRepository<T, ID> getRepository(Class<T> domainClass) {
        String repositoryBeanName = domainClass.getSimpleName().substring(0, 1).toLowerCase()
                + domainClass.getSimpleName().substring(1) + "Repository";
        try {
            return (JpaRepository<T, ID>) context.getBean(repositoryBeanName);
        } catch (Exception e) {
            throw new IllegalArgumentException("Repository bean not found for domain class: " + domainClass.getSimpleName()
                    + ". Expected bean name: " + repositoryBeanName, e);
        }
    }
}

