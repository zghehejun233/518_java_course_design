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
        Student student = getStudent(studentId);
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

                resultList.add(temp);
            }
        }
        SystemApplicationListener.logger.warn("最后找到列表的长度为：" + resultList.size());
        return resultList;
    }

    /**
     * 获取指定学生所有课程的排名
     *
     * @param studentId            学生Id
     * @param studentScoresDTOList 学生的成绩DTO列表
     * @return 更新后的成绩DTO列表
     */
    public List<StudentScoresDTO> getCourseRank(Integer studentId, List<StudentScoresDTO> studentScoresDTOList) {

        // 遍历学生的所有成绩
        for (int i = 0; i < studentScoresDTOList.size(); i++) {
            // 先获取某个课程的成绩单
            StudentScoresDTO studentScoresDTO = studentScoresDTOList.get(i);
            Optional<Course> op = courseRepository.findById(studentScoresDTO.getCourseId());
            if (op.isPresent()) {
                Course course = op.get();
                // 保存本门课程下的所有成绩
                List<CourseRankDTO> courseRankDTOList = score.getCourseRankList(course);

                CourseRankDTO courseRankDTO = score.getCourseRank(courseRankDTOList, studentScoresDTOList.get(i).getScore());
                SystemApplicationListener.logger.warn("打印学生的排名信息");
                SystemApplicationListener.logger.warn(courseRankDTO.toString());
                studentScoresDTOList.get(i).setCourseRankDTO(courseRankDTO);
            }
        }
        return studentScoresDTOList;
    }
}
