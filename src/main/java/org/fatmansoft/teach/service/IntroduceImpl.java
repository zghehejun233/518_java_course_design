package org.fatmansoft.teach.service;

import org.fatmansoft.teach.dto.StudentScoresDTO;
import org.fatmansoft.teach.models.academic.CourseSelection;
import org.fatmansoft.teach.models.academic.Score;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author 16645
 */
@Service
public class IntroduceImpl {

    @Resource
    private StudentRepository studentRepository;

    public List<StudentScoresDTO> getScoreData(Integer studentId) {
        List<StudentScoresDTO> resultList = new ArrayList<>();
        Student student = null;
        Optional<Student> op;
        if (studentId != null) {
            op = studentRepository.findById(studentId);
            if (op.isPresent()) {
                student = op.get();
                System.out.println(student.getStudentName());
            }
        }
        if (student != null) {
            for (Score value : student.getScores()) {
                StudentScoresDTO temp = new StudentScoresDTO();
                temp.setScore(value.getScore());
                temp.setCourse(value.getCourse().getName());
                temp.setCredit(value.getCourse().getCredit());
                Set<CourseSelection> tempRelatedCourseSelection = value.getCourse().getCourseSelections();
                for (CourseSelection relatedCourse : tempRelatedCourseSelection) {
                    Integer id = relatedCourse.getStudent().getStudentId();
                    if (studentId.equals(id)) {
                        temp.setType(relatedCourse.getType());
                    }
                }
                resultList.add(temp);
            }
        }
        return resultList;
    }
}
