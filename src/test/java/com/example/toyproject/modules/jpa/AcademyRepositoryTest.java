package com.example.toyproject.modules.jpa;

import com.example.toyproject.modules.academy.entity.Academy;
import com.example.toyproject.modules.academy.enums.AcademyStatus;
import com.example.toyproject.modules.academy.repository.AcademyRepository;
import com.example.toyproject.modules.common.jpa.BaseTime;
import com.example.toyproject.modules.user.entity.User;
import com.example.toyproject.modules.user.enums.RoleType;
import com.example.toyproject.modules.user.enums.UserConst;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AcademyRepositoryTest extends BaseTime {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    AcademyRepository academyRepository;

    @BeforeEach
    public void init() {
        setup(
            "권경렬",
            "윤선생아카데미석포초점",
            "051-111-1111",
            "010-1234-1234",
            AcademyStatus.ACTIVE
        );
    }

    @Test
    public void selectAll() {
        var academies = academyRepository.findAll();
        assertNotEquals(academies.size(), 0);

        assertEquals(academies.get(0).getFullName(), "권경렬");
        assertEquals(academies.get(0).getAcademyName(), "윤선생아카데미석포초점");
        assertEquals(academies.get(0).getContact(), "051-111-1111");
        assertEquals(academies.get(0).getPhone(), "010-1234-1234");
        assertEquals(academies.get(0).getStatus(), AcademyStatus.ACTIVE);
    }

    @Test
    void selectAndUpdate() {
        Optional<Academy> optionalAcademy = academyRepository.findById(1L);

        if (optionalAcademy.isPresent()) {
            Academy result = optionalAcademy.get();

            assertEquals(result.getFullName(), "권경렬");
            assertEquals(result.getAcademyName(), "윤선생아카데미석포초점");
            assertEquals(result.getContact(), "051-111-1111");
            assertEquals(result.getPhone(), "010-1234-1234");
            assertEquals(result.getStatus(), AcademyStatus.ACTIVE);

            String updateFullName = "김경렬";
            result.setFullName(updateFullName);
            Academy merge = entityManager.merge(result);

            assertEquals(merge.getFullName(), "김경렬");
        } else {
            assertNotNull(optionalAcademy.get());
        }
    }

    @Test
    void insertAndDelete() {
        Academy academy = setup(
                "조아라",
                "윤선생아카데미석포초2호점",
                "051-222-2222",
                "010-2222-2222",
                AcademyStatus.ACTIVE
        );

        Optional<Academy> optionalAcademy = academyRepository.findById(academy.getId());

        if (optionalAcademy.isPresent()) {
            Academy result = optionalAcademy.get();

            assertEquals(result.getFullName(), "조아라");
            assertEquals(result.getAcademyName(), "윤선생아카데미석포초2호점");
            assertEquals(result.getContact(), "051-222-2222");
            assertEquals(result.getPhone(), "010-2222-2222");
            assertEquals(result.getStatus(), AcademyStatus.ACTIVE);

            entityManager.remove(result);

            Optional<Academy> deleteAcademy = academyRepository.findById(result.getId());
            deleteAcademy.ifPresent(Assertions::assertNull);
        } else {
            assertNotNull(optionalAcademy.get());
        }

    }


    private Academy setup(String fullName, String academyName, String contact, String phone, AcademyStatus status) {
        return entityManager.persist(
                new Academy(
                null,
                fullName,
                academyName,
                contact,
                phone,
                null,
                null,
                status
            )
        );
    }
}
