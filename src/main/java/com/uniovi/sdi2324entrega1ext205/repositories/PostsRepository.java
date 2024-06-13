package com.uniovi.sdi2324entrega1ext205.repositories;


import com.uniovi.sdi2324entrega1ext205.entities.Post;
import com.uniovi.sdi2324entrega1ext205.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostsRepository extends CrudRepository<Post, Long> {
    @Query("SELECT r FROM Post r WHERE r.user = ?1 ORDER BY r.id ASC")
    Page<Post> findAllByUser(User user, Pageable pageable);

    Page<Post> findAll(Pageable pageable);
}
