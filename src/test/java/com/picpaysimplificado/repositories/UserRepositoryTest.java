package com.picpaysimplificado.repositories;

import java.math.BigDecimal;
import java.util.Optional;

import org.hibernate.usertype.UserType.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.UserDTO;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get user successfully from DB")
    public void findUserByDocument() {
        String document = "12345678";
        UserDTO data = new UserDTO("Sara", "Test", document, new BigDecimal(24), "sara@email.com", "123456", UserType.COMMON);
        this.createUser(data);
        
        Optional<User> result = this.userRepository.findUserByDocument(document);
    
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get user User from DB when user not exists")
    public void findUserByDocumentNotFound() {
        String document = "0001";
      
        Optional<User> result = this.userRepository.findUserByDocument(document);
    
        assertThat(result.isEmpty()).isTrue();
    }

    private User createUser(UserDTO data) {
        User newUser = new User(data);
        this.entityManager.persist(newUser);
        return newUser;
    }
}