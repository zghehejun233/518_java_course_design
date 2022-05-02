package org.fatmansoft.teach.service;

import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.dto.*;
import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.models.academic.CourseSelection;
import org.fatmansoft.teach.models.academic.Score;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.GlobalScoreRepository;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
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
    private StudentRepository studentRepository;
    @Resource
    private ScoreImpl score;


    /**
     * 获取学生的所有成绩的平均值
     *
     * @param courseScoresDTOList 学生所有成绩DTO列表
     * @return 返回平均成绩DTO
     */
    public AverageScoreDTO getAverage(List<CourseScoresDTO> courseScoresDTOList) {
        final int BASE_SCORE = 50;

        double averageScoreForAll = 0;
        double averageScoreForMajor = 0;
        double fullCreditsForAll = 0;
        double fullCreditsForMajor = 0;

        for (CourseScoresDTO value : courseScoresDTOList) {
            fullCreditsForAll += value.getCredit();
            try {
                if ("1".equals(value.getType())) {
                    fullCreditsForMajor += value.getCredit();
                }
            } catch (Exception e) {
                SystemApplicationListener.logger.warn(e.toString());
            }
        }

        for (CourseScoresDTO value : courseScoresDTOList) {
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
    public List<CourseScoresDTO> getScoreData(Student student) {
        List<CourseScoresDTO> resultList = new ArrayList<>();
        if (student != null) {
            for (Score value : student.getScores()) {
                CourseScoresDTO temp = new CourseScoresDTO();
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
     * @param courseScoresDTOList 学生的成绩DTO列表
     * @return 更新后的成绩DTO列表
     */
    public List<CourseScoresDTO> getCourseRank(List<CourseScoresDTO> courseScoresDTOList) {
        // 遍历学生的所有成绩
        for (CourseScoresDTO courseScoresDTO : courseScoresDTOList) {
            // 先获取某个课程的成绩单
            Optional<Course> op = courseRepository.findById(courseScoresDTO.getCourseId());
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
                CourseRankDTO courseRankDTO = score.getCourseRank(courseRankDTOList, courseScoresDTO.getScore());
                SystemApplicationListener.logger.info("打印学生的排名信息");
                SystemApplicationListener.logger.info(courseRankDTO.toString());
                courseScoresDTO.setCourseRankDTO(courseRankDTO);
            }
        }
        return courseScoresDTOList;
    }

    public List<TotalScoreDTO> getAllStudentsTotalScore() {
        List<TotalScoreDTO> totalScoreDTOList = new ArrayList<>();
        List<Student> studentList = studentRepository.findAll();
        for (Student value : studentList) {
            // 获取学生的所有成绩，排名为空
            List<CourseScoresDTO> scoresDTOList = getScoreData(value);
            // 获得包含排名的成绩，填充组合的对象
            // 基于所有成绩计算平均成绩
            AverageScoreDTO averageScoreDTO = getAverage(getCourseRank(scoresDTOList));
            TotalScoreDTO totalScoreDTO = new TotalScoreDTO();
            totalScoreDTO.setAverageScore(averageScoreDTO.getAverageScoreForAll());
            totalScoreDTO.setAverageGPA(averageScoreDTO.getAverageGPAForAll());
            totalScoreDTOList.add(totalScoreDTO);
        }
        return totalScoreDTOList;
    }

    public List<TotalScoreDTO> getTotalRank(List<TotalScoreDTO> totalScoreDTOList) {
        totalScoreDTOList.sort(new TotalScoreComparator());
        int size = totalScoreDTOList.size();
        int sameScoreNum = 1;
        for (int i = 0; i < totalScoreDTOList.size(); i++) {
            TotalRankDTO totalRankDTO = new TotalRankDTO();
            totalRankDTO.setRank(i + 1);
            totalRankDTO.setPercent((i + 1) / (double) size);
            totalRankDTO.setAverageScore(totalRankDTO.getAverageScore());
            if (i > 0 && (totalScoreDTOList.get(i).getAverageScore().equals(totalScoreDTOList.get(i - 1).getAverageScore()))) {
                sameScoreNum += 1;
            } else {
                sameScoreNum = 1;
            }
            totalRankDTO.setSameScoreNum(sameScoreNum);
            totalScoreDTOList.get(i).setTotalRankDTO(totalRankDTO);
        }
        try {
            globalScoreRepository.drop("totalRank");
            SystemApplicationListener.logger.info("清空原有全部排名");
        } catch (Exception e) {
            SystemApplicationListener.logger.info(e.toString());
        }
        List<String> totalRankDTOList = new ArrayList<>();
        for (TotalScoreDTO value : totalScoreDTOList) {
            totalRankDTOList.add(TotalRankSerializator.serializeTotalRank(value.getTotalRankDTO()));
        }
        globalScoreRepository.leftPushStringList("totalRank", totalRankDTOList);
        return totalScoreDTOList;
    }

    public TotalRankDTO getStudentTotalRank(Integer score) {
        List<TotalRankDTO> totalRankDTOList = new ArrayList<>();
        List<String> serializedTotalRank = globalScoreRepository.getAllRange("totalRank");
        for (String str : serializedTotalRank) {
            totalRankDTOList.add(TotalRankSerializator.deserializeTotalRank(str));
        }
        for (TotalRankDTO value : totalRankDTOList) {
            if (value.getAverageScore().equals(score)) {
                return value;
            }
        }
        return new TotalRankDTO();
    }
}
