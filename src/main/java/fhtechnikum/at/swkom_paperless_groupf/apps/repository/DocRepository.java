package fhtechnikum.at.swkom_paperless_groupf.apps.repository;

import fhtechnikum.at.swkom_paperless_groupf.apps.entity.Doc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocRepository extends JpaRepository<Doc, Long> {
}
