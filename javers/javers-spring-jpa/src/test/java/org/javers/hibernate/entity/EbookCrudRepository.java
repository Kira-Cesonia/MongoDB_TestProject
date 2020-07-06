package org.javers.hibernate.entity;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface EbookCrudRepository extends JpaRepository<Ebook, String> {
}
