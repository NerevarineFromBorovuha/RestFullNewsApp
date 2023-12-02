package by.home.fullRestApp.repository;

import by.home.fullRestApp.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByNewsId(Pageable pageable, long newsId);


}
