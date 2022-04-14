package models;

import org.fatmansoft.teach.SpringBootSecurityJwtApplication;
import org.fatmansoft.teach.models.student_basic.SocialRelation;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.student_basic.EducationExperienceRepository;
import org.fatmansoft.teach.repository.student_basic.FamilyRepository;
import org.fatmansoft.teach.repository.student_basic.SocialRelationRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @author GuoSurui
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootSecurityJwtApplication.class)
public class StudentBasicTest {
    @Resource
    private EducationExperienceRepository educationExperienceRepository;
    @Resource
    private SocialRelationRepository socialRelationRepository;
    private FamilyRepository familyRepository;
    @Resource
    private StudentRepository studentRepository;

    @Test
    public void test(){
        Student student = new Student();
        student.setStudentId(Integer.valueOf(2));
        student.setStudentNum("s");
        studentRepository.save(student);

        SocialRelation socialRelation = new SocialRelation();
        socialRelation.setSocialRelationId(Integer.valueOf(2));
        socialRelation.setDescription("s");
        socialRelation.setStudent(student);

        Set<SocialRelation> socialRelationSet = new HashSet<SocialRelation>();
        socialRelationSet.add(socialRelation);

        student.setSocialRelations(socialRelationSet);
        socialRelationRepository.saveAll(socialRelationSet);
    }

}
