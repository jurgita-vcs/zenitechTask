package com.zenitech.task.zenitechTask.repository;

import com.zenitech.task.zenitechTask.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findByPosted(boolean posted);
    List<Response> findByTitleContaining(String title);
    //List<Response> findByTitleContainingSort(String title, Sort sort);

    //Page<Response> findByPostedPage(boolean posted, Pageable pageable);
    //Page<Response> findByTitleContainingPage(String title, Pageable pageable);

}
