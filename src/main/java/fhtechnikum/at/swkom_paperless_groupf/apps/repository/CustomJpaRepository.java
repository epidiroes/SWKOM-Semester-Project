package fhtechnikum.at.swkom_paperless_groupf.apps.repository;

import jakarta.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

public class CustomJpaRepository<T, ID> extends SimpleJpaRepository<T, ID> {
    private final EntityManager em;

    public CustomJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.em = em;
    }

    // NOTE: original save-method doesn't work in all possible cases (see DBInitializer for example)

    @Override
    @Transactional
    public <S extends T> S save(S entity) {
        return em.merge(entity);
    }
}