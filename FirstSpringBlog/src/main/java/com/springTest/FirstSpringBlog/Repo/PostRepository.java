package com.springTest.FirstSpringBlog.Repo;

import com.springTest.FirstSpringBlog.Models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

}
