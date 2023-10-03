package com.example.toyproject.modules.jpa;

import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.modules.user.enums.RoleType;
import com.example.toyproject.modules.user.enums.UserStatus;
import com.example.toyproject.modules.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void init() {
        setUp(
                "kkr",
                "1234",
                RoleType.ACADEMY,
                UserStatus.ACTIVE
        );
    }

    @Test
    public void selectAll() {
        var users = userRepository.findAll();
        assertNotEquals(users.size(), 0);

        assertEquals(users.get(0).getUsername(), "yoon");
        assertEquals(users.get(0).getPassword(), "1234");
        assertEquals(users.get(0).getRole(), RoleType.ACADEMY);
        assertEquals(users.get(0).getStatus(), UserStatus.ACTIVE);
    }

    @Test
    public void selectAndUpdate() {
        //given
        Long id = 1L;
        var optionalUser = this.userRepository.findById(1L);

        if (optionalUser.isPresent()) {
            var result = optionalUser.get();

            assertEquals(result.getUsername(), "yoon");
            assertEquals(result.getPassword(), "1234");
            assertEquals(result.getRole(), RoleType.ACADEMY);
            assertEquals(result.getStatus(), UserStatus.ACTIVE);

            //when
            String password = "4312";
            result.setPassword(password);
            User merge = entityManager.merge(result);

            //then
            assertEquals(merge.getPassword(), "4312");
        }
    }

    @Test
    public void insertAndDelete() {
        //given
        var user = setUp(
                "yoon",
                "1234",
                RoleType.ACADEMY,
                UserStatus.ACTIVE
        );

        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            User result = optionalUser.get();
            assertEquals(result.getUsername(), "yoon");
            assertEquals(result.getPassword(), "1234");
            assertEquals(result.getRole(), RoleType.ACADEMY);
            assertEquals(result.getStatus(), UserStatus.ACTIVE);

            entityManager.remove(result);
            Optional<User> deleteUser = userRepository.findById(user.getId());

            deleteUser.ifPresent(Assertions::assertNull);
        } else {
            assertNotNull(optionalUser.get());
        }
    }

    public User setUp(
            String username,
            String password,
            RoleType role,
            UserStatus status
    ) {
        return entityManager.persist(
                        new User(
                            null,
                            username,
                            password,
                            role,
                            status
                        )
                );
    }

}
