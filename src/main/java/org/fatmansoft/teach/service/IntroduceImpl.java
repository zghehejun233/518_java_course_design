package org.fatmansoft.teach.service;

import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.dto.AverageScoreDTO;
import org.fatmansoft.teach.dto.CourseRankDTO;
import org.fatmansoft.teach.dto.StudentScoresDTO;
import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.models.academic.CourseSelection;
import org.fatmansoft.teach.models.academic.Score;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.fatmansoft.teach.repository.academic.ScoreRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.fatmansoft.teach.service.academic.ScoreImpl;
import org.fatmansoft.teach.service.student_basic.StudentImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 16645
 */
@Service
public class IntroduceImpl {

    @Resource
    private StudentRepository studentRepository;
    @Resource
    private ScoreRepository scoreRepository;
    @Resource
    private CourseRepository courseRepository;
    @Resource
    private ScoreImpl score;

    /**
     * 获取学生对象
     *
     * @param studentId 学生Id
     * @return 返回对象
     */
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

    /**
     * 获取学生的所有成绩
     *
     * @param studentId 学生ID
     * @return 返回学生的成绩DTO列表
     */
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
                temp.setCourseId(value.getCourse().getCourseId());
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

    /**
     * 获取学生的所有成绩的平均值
     *
     * @param studentScoresDTOList 学生所有成绩DTO列表
     * @return 返回平均成绩DTO
     */
    public AverageScoreDTO getAverage(List<StudentScoresDTO> studentScoresDTOList) {
        final int BASE_SCORE = 50;

        double averageScoreForAll = 0;
        double averageScoreForMajor = 0;
        double fullCreditsForAll = 0;
        double fullCreditsForMajor = 0;

        for (StudentScoresDTO value : studentScoresDTOList) {
            fullCreditsForAll += value.getCredit();
            try {
                if ("1".equals(value.getType())) {
                    fullCreditsForMajor += value.getCredit();
                }
            } catch (Exception e) {
                SystemApplicationListener.logger.warn(e.toString());
            }
        }
        ;

        for (StudentScoresDTO value : studentScoresDTOList) {
            averageScoreForAll += value.getScore() * (value.getCredit() / fullCreditsForAll);

            try {
                if ("1".equals(value.getType())) {
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

    /**
     * 获取指定学生所有课程的排名
     *
     * @param studentId            学生Id
     * @param studentScoresDTOList 学生的成绩DTO列表
     * @return 更新后的成绩DTO列表
     */
    public List<StudentScoresDTO> getCourseRank(Integer studentId, List<StudentScoresDTO> studentScoresDTOList) {
        // 获取学生选择的课程
        List<CourseSelection> courseSelectionList = new ArrayList<>(getStudent(studentId).getCourseSelections());
        // 遍历学生选择的课程
        for (int i = 0; i < studentScoresDTOList.size(); i++) {
            // 先获取某个课程的成绩单
            Optional<Course> op = courseRepository.findById(studentScoresDTOList.get(i).getCourseId());
            if (op.isPresent()){
                List<CourseRankDTO> courseRankDTOList = score.getCourseRankList(op.get());
                // 获取这个学生的排名信息
                    studentScoresDTOList.get(i).setCourseRankDTO(score.getCourseRank(courseRankDTOList
                            , scoreRepository.findByStudent_StudentIdAndCourse_CourseId(
                                    courseSelectionList.get(i).getStudent().getStudentId()
                                    , courseSelectionList.get(i).getCourse().getCourseId()).getScore()));
            }
        }
        return studentScoresDTOList;
    }
}
