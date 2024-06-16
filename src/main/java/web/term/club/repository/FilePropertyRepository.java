package web.term.club.repository;

import web.term.club.domain.FileProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilePropertyRepository extends JpaRepository<FileProperty, Long> {
    boolean existsByUploadFileName(String name);
}
