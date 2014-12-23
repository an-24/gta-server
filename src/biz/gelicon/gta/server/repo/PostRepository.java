package biz.gelicon.gta.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import biz.gelicon.gta.server.data.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
