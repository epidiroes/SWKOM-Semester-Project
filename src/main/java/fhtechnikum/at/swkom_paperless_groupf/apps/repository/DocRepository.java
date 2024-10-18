package fhtechnikum.at.swkom_paperless_groupf.apps.repository;

import fhtechnikum.at.swkom_paperless_groupf.apps.entity.Doc;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Transactional
public interface DocRepository extends JpaRepository<Doc, Long> {
    @Override
    Optional<Doc> findById(Long id);

    @Override
    List<Doc> findAll();

}
