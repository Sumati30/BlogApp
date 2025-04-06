package com.BlogApp.demo.demo.Repositories;



import com.BlogApp.demo.demo.Entities.BlogEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity,Long> {
}
