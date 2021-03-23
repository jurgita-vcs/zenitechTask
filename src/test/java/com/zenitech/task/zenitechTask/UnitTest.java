package com.zenitech.task.zenitechTask;

import com.zenitech.task.zenitechTask.model.Response;
import com.zenitech.task.zenitechTask.repository.ResponseRepository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ResponseRepository repository;

    @Test
    public void should_find_no_responses_if_repository_is_empty() {
        Iterable<Response> responses = repository.findAll();
        assertThat(responses).isEmpty();
    }

    @Test
    public void should_store_a_response() {
        Response response = repository.save(new Response("Response title", "Response description", true));

        assertThat(response).hasFieldOrPropertyWithValue("title", "Response title");
        assertThat(response).hasFieldOrPropertyWithValue("description", "Response description");
        assertThat(response).hasFieldOrPropertyWithValue("posted", true);
    }

    @Test
    public void should_find_all_responses() {
        Response response1 = new Response("Response#1", "Description#1", true);
        entityManager.persist(response1);

        Response response2 = new Response("Response#2", "Description#2", false);
        entityManager.persist(response2);

        Response response3 = new Response("Response#3", "Description#3", true);
        entityManager.persist(response3);

        Iterable<Response> responses = repository.findAll();

        assertThat(responses).hasSize(3).contains(response1, response2, response3);
    }

    @Test
    public void should_find_response_by_id() {
        Response response1 = new Response("Response#1", "Description#1", true);
        entityManager.persist(response1);

        Response response2 = new Response("Response#2", "Description#2", false);
        entityManager.persist(response2);

        Response foundResponse = repository.findById(response2.getId()).get();
        assertThat(foundResponse).isEqualTo(response2);
    }

    @Test
    public void should_find_posted_responses() {
        Response response1 = new Response("Response#1", "Description#1", true);
        entityManager.persist(response1);

        Response response2 = new Response("Response#2", "Description#2", false);
        entityManager.persist(response2);

        Response response3 = new Response("Response#3", "Description#3", true);
        entityManager.persist(response3);

        Iterable<Response> responses = repository.findByPosted(true);

        assertThat(responses).hasSize(2).contains(response1, response3);
    }

    @Test
    public void should_find_responses_by_title_containing_string() {
        Response response1 = new Response("New Response#1", "Description#1", true);
        entityManager.persist(response1);

        Response response2 = new Response("Response#2", "Description#2", false);
        entityManager.persist(response2);

        Iterable<Response> responses = repository.findByTitleContaining("ring");

        assertThat(responses).hasSize(2).contains(response1, response2);
    }

    @Test
    public void should_update_response_by_id() {
        Response response1 = new Response("Response#1", "Description#1", true);
        entityManager.persist(response1);

        Response response2 = new Response("Response#2", "Description#2", false);
        entityManager.persist(response2);

        Response updatedResponse = new Response("Updated Response#2", "Updated Description#2", true);

        Response response = repository.findById(response2.getId()).get();
        response.setTitle(updatedResponse.getTitle());
        response.setDescription(updatedResponse.getDescription());
        response.setPosted(updatedResponse.isPosted());
        repository.save(response);

        Response checkResponse = repository.findById(response2.getId()).get();

        assertThat(checkResponse.getId()).isEqualTo(response2.getId());
        assertThat(checkResponse.getTitle()).isEqualTo(updatedResponse.getTitle());
        assertThat(checkResponse.getDescription()).isEqualTo(updatedResponse.getDescription());
        assertThat(checkResponse.isPosted()).isEqualTo(updatedResponse.isPosted());
    }

    @Test
    public void should_delete_response_by_id() {
        Response responses1 = new Response("Response#1", "Description#1", true);
        entityManager.persist(responses1);

        Response response2 = new Response("Response#2", "Description#2", false);
        entityManager.persist(response2);

        Response response3 = new Response("Response#3", "Description#3", true);
        entityManager.persist(response3);

        repository.deleteById(response2.getId());

        Iterable<Response> responses = repository.findAll();
        assertThat(responses).hasSize(2).contains(responses1, response3);
    }

    @Test
    public void should_delete_all_responses() {
        entityManager.persist(new Response("Response#1", "Description#1", true));
        entityManager.persist(new Response("Response#2", "Description#2", false));

        repository.deleteAll();
        assertThat(repository.findAll()).isEmpty();
    }
}