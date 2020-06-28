package com.banque.demo.sec;

import com.banque.demo.entities.Client;
import com.banque.demo.entities.Utilisateur;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class RestConfiguration extends RepositoryRestConfigurerAdapter{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        //TODO: Expose for specific entity!
        config.exposeIdsFor(Utilisateur.class);
        config.exposeIdsFor(Client.class);
        //config.exposeIdsFor(Position.class);

        //TODO: Expose id for all entities!
        entityManager.getMetamodel().getEntities().forEach(entity->{
            try {
                System.out.println("Model: " + entity.getName());
                Class<? extends Object> clazz = Class.forName(String.format("yourpackage.%s", entity.getName()));
                config.exposeIdsFor(clazz);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
