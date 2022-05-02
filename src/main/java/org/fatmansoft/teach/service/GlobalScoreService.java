package org.fatmansoft.teach.service;

import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.dto.AverageScoreDTO;
import org.fatmansoft.teach.dto.CourseRankDTO;
import org.fatmansoft.teach.dto.CourseRankDTOSerializator;
import org.fatmansoft.teach.dto.StudentScoresDTO;
import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.models.academic.CourseSelection;
import org.fatmansoft.teach.models.academic.Score;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.GlobalScoreRepository;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.fatmansoft.teach.service.academic.ScoreImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author GuoSurui
 */
@Service
public class GlobalScoreService {
    @Resource
    private CourseRepository courseRepository;
    @Resource
    private GlobalScoreRepository globalScoreRepository;
    @Resource
    private ScoreImpl score;


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
     * 获取学生的所有成绩
     *
     * @param student 学生
     * @return 返回学生的成绩DTO列表
     */
    public List<StudentScoresDTO> getScoreData(Student student) {
        List<StudentScoresDTO> resultList = new ArrayList<>();
        if (student != null) {
            for (Score value : student.getScores()) {
                StudentScoresDTO temp = new StudentScoresDTO();
                temp.setScore(value.getScore());
                temp.setCourse(value.getCourse().getName());
                temp.setCourseId(value.getCourse().getCourseId());
                temp.setCredit(value.getCourse().getCredit());

                Set<CourseSelection> tempRelatedCourseSelection = value.getCourse().getCourseSelections();
                for (CourseSelection relatedCourse : tempRelatedCourseSelection) {
                    if (student.getStudentId().equals(relatedCourse.getStudent().getStudentId())) {
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
        for (StudentScoresDTO studentScoresDTO : studentScoresDTOList) {
            // 先获取某个课程的成绩单
            Optional<Course> op = courseRepository.findById(studentScoresDTO.getCourseId());
            if (op.isPresent()) {
                Course course = op.get();
                // 保存本门课程下的所有成绩
                // List<CourseRankDTO> courseRankDTOList = score.getCourseRankList(course);
                // 先建立一个空列表，用来保存这一门课程下的成绩
                List<CourseRankDTO> courseRankDTOList = new ArrayList<>();
                try {
                    // 尝试取出数据
                    List<String> tempList = globalScoreRepository.getAllRange(String.valueOf(course.getCourseId() - 1));
                    // 解码
                    for (String value : tempList) {
                        courseRankDTOList.add(CourseRankDTOSerializator.deserializeCourseRankDTO(value));
                    }
                } catch (Exception e) {
                    SystemApplicationListener.logger.warn("取出缓存失败！");
                    SystemApplicationListener.logger.warn(String.valueOf(e));
                }
                // 通过比对获取该分数对应的排名
                CourseRankDTO courseRankDTO = score.getCourseRank(courseRankDTOList, studentScoresDTO.getScore());
                SystemApplicationListener.logger.info("打印学生的排名信息");
                SystemApplicationListener.logger.info(courseRankDTO.toString());
                studentScoresDTO.setCourseRankDTO(courseRankDTO);
            }
        }
        return studentScoresDTOList;
    }


}
