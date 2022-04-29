package org.fatmansoft.teach.service;

import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.dto.AverageScoreDTO;
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

    public Student getStudent(Integer studentId) {
        Student student = null;
        Optional<Student> op;
        if (studentId != null) {
            op = studentRepository.findById(studentId);
            if (op.isPresent()) {
                student = op.get();
            }
        }
        return student;
    }

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

                System.out.println(temp);
                resultList.add(temp);
            }
        }
        System.out.println("最后找到列表的长度为：" + resultList.size());
        return resultList;
    }

    public AverageScoreDTO getAverage(List<StudentScoresDTO> data) {
        final int COMMON_FULL_SCORE = 100;
        final int BASE_SCORE = 50;

        double averageScoreForAll = 0;
        double averageScoreForMajor = 0;
        double fullCreditsForAll = 0;
        double fullCreditsForMajor = 0;

        for (StudentScoresDTO value : data) {
            fullCreditsForAll += value.getCredit();
            try {
                if (value.getType().equals("1")) {
                    fullCreditsForMajor += value.getCredit();
                }
            } catch (NullPointerException nullPointerException) {
                SystemApplicationListener.logger.warn("存在未指定的记分方式");
                SystemApplicationListener.logger.warn(nullPointerException.toString());
            } catch (Exception e) {
                SystemApplicationListener.logger.warn(e.toString());
            }
        }
        ;

        for (StudentScoresDTO value : data) {
            averageScoreForAll += value.getScore() * (value.getCredit() / fullCreditsForAll);

            try {
                if (value.getType().equals("1")) {
                    averageScoreForMajor += value.getScore() * (value.getCredit() / fullCreditsForMajor);
                }
            } catch (NullPointerException nullPointerException) {
                SystemApplicationListener.logger.warn("存在未指定的记分方式");
                SystemApplicationListener.logger.warn(nullPointerException.toString());
            } catch (Exception e) {
                SystemApplicationListener.logger.warn(e.toString());
            }
        }
        return new AverageScoreDTO(
                averageScoreForAll,
                0.1 * (averageScoreForAll - BASE_SCORE),
                averageScoreForMajor,
                0.1 * (averageScoreForMajor - BASE_SCORE)
        );


    }
}
