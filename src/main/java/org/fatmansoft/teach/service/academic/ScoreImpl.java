package org.fatmansoft.teach.service.academic;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.dto.CourseRankComparator;
import org.fatmansoft.teach.dto.CourseRankDTO;
import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.models.academic.CourseSelection;
import org.fatmansoft.teach.models.academic.HomeWork;
import org.fatmansoft.teach.models.academic.Score;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.fatmansoft.teach.repository.academic.ScoreRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.fatmansoft.teach.service.GlobalScoreService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author GuoSurui
 */
@Service
@Setter
@Getter
public class ScoreImpl {
    @Resource
    private ScoreRepository scoreRepository;
    @Resource
    private CourseRepository courseRepository;
    @Resource
    private StudentRepository studentRepository;

    private Integer courseId;
    private Integer studentId;

    public List<Object> queryAllScore() {
        List<Object> result = new ArrayList<>();
        List<Score> scoreList = scoreRepository.findAllByStudent_StudentId(studentId);
        if (scoreList.size() == 0) {
            return result;
        }
        Score score;
        Map<String, Object> tempMap;
        for (Score value : scoreList) {
            score = value;
            tempMap = new HashMap<>();
            tempMap.put("id", score.getScoreId());
            tempMap.put("method", score.getMethod());
            tempMap.put("dailyScore", score.getDailyScore());
            tempMap.put("examScore", score.getExamScore());
            result.add(tempMap);
        }
        return result;
    }

    public Map<String, Object> queryScoreDetail(Integer scoreId) {
        Score score = null;
        Optional<Score> op;
        if (scoreId != null) {
            op = scoreRepository.findById(scoreId);
            if (op.isPresent()) {
                score = op.get();
            }
        }

        Map<String, Object> ressultMap = new HashMap<>();
        if (score != null) {
            ressultMap.put("id", score.getScoreId());
            ressultMap.put("method", score.getMethod());
            ressultMap.put("dailyScore", score.getDailyScore());
            ressultMap.put("examScore", score.getExamScore());
        }
        return ressultMap;
    }


    public Integer insertScore(Score scoreData, String courseName, String studentName) {
        Score score = null;
        Optional<Score> op;
        if (scoreData.getScoreId() != null) {
            op = scoreRepository.findById(scoreData.getScoreId());
            if (op.isPresent()) {
                score = op.get();
            }
        }

        Integer maxScoreId = null;
        if (score == null) {
            score = new Score();
            maxScoreId = scoreRepository.getMaxId();
            if (maxScoreId == null) {
                maxScoreId = 1;
            } else {
                maxScoreId += 1;
            }
            score.setScoreId(maxScoreId);
        }


        Course course = null;
        Optional<Course> opCourse;
        Student student = null;
        Optional<Student> opStudent;
        if ((courseId == null && courseName != null)) {
            course = courseRepository.findFirstByName(courseName);
            SystemApplicationListener.logger.info("[Score]" + "找到关联的课程信息");
        } else {
            try {
                opCourse = courseRepository.findById(courseId);
                if (opCourse.isPresent()) {
                    course = opCourse.get();
                    SystemApplicationListener.logger.info("[Score]" + "找到关联的课程信息");
                }
            } catch (Exception e) {
                SystemApplicationListener.logger.warn(e.toString());
                SystemApplicationListener.logger.error("[Score]" + "没有找到关联的课程信息");
                return 1;
            }
        }
        if (studentId == null && studentName != null) {
            student = studentRepository.findFirstByStudentNameOrStudentNum(studentName, studentName);
            SystemApplicationListener.logger.info("[Score]" + "找到关联的学生信息");

        } else {
            try {
                opStudent = studentRepository.findById(studentId);
                if (opStudent.isPresent()) {
                    student = opStudent.get();
                    SystemApplicationListener.logger.info("[Score]" + "找到关联的学生信息");
                }
            } catch (Exception e) {
                SystemApplicationListener.logger.warn(e.toString());
                SystemApplicationListener.logger.error("[Score]" + "没有找到关联的学生信息");
                return 1;
            }
        }
        score.setCourse(course);
        score.setStudent(student);
        score.setScore(scoreData.getScore());
        score.setMethod(scoreData.getMethod());
        score.setDailyScore(scoreData.getDailyScore());
        score.setExamScore(scoreData.getExamScore());
        scoreRepository.save(score);
        SystemApplicationListener.logger.info("[Score]" + "成功保存成绩信息！");
        return maxScoreId;

    }

    public void dropScore(Integer scoreId) {
        Score score;
        Optional<Score> op;
        if (scoreId != null) {
            op = scoreRepository.findById(scoreId);
            if (op.isPresent()) {
                score = op.get();
                SystemApplicationListener.logger.info("[Score]: 找到要删除的成绩记录" + score);

                Course relatedCourse;
                Optional<Course> opCourse = courseRepository.findById(score.getCourse().getCourseId());
                if (opCourse.isPresent()) {
                    relatedCourse = opCourse.get();
                    relatedCourse.getCheckouts().remove(score);
                    SystemApplicationListener.logger.info("[Score]:找到关联的课程信息" + relatedCourse);

                }

                Student relatedStudent;
                Optional<Student> opStudent = studentRepository.findById(score.getStudent().getStudentId());
                if (opStudent.isPresent()) {
                    relatedStudent = opStudent.get();
                    relatedStudent.getCheckouts().remove(score);
                    SystemApplicationListener.logger.info("[Score]:找到关联的学生信息" + relatedStudent);
                }
                scoreRepository.delete(score);
                SystemApplicationListener.logger.info("[Score]:删除成绩信息记录成功！");
            }
        }
    }

    public List<CourseRankDTO> getCourseRankList(Course course) {
        // 获取这么课程下的所有Score对象
        List<Score> scoreList = new ArrayList<>(course.getScores());
        // 初始化
        List<CourseRankDTO> courseRankDTOList = new ArrayList<>();
        for (Score value : scoreList) {
            courseRankDTOList.add(new CourseRankDTO(0, 0.0, value.getScore(), 0));
        }
        // 排序
        courseRankDTOList.sort(new CourseRankComparator());
        // 存储成绩相同的人数和名单长度
        Integer sameScoreNum = 1;
        int size = courseRankDTOList.size();

        for (int i = 0; i < size; i++) {
            // 排名
            courseRankDTOList.get(i).setRank(i + 1);
            // 从第二个开始，如果与前者相同则自增，否则归一
            if ((i > 1) && courseRankDTOList.get(i).getScore().equals(courseRankDTOList.get(i - 1).getScore())) {
                sameScoreNum += 1;
            } else {
                sameScoreNum = 1;
            }
            courseRankDTOList.get(i).setSameScoreNum(i);
            // 百分比
            courseRankDTOList.get(i).setPercent((courseRankDTOList.get(i).getRank() + 1 - sameScoreNum + 1) / (double) size);
            SystemApplicationListener.logger.debug("这一门课下的成绩【" + i + "】：" + courseRankDTOList.get(i).toString());
        }
        return courseRankDTOList;
    }

    public CourseRankDTO getCourseRank(List<CourseRankDTO> courseRankDTOList, Integer score) {
        for (CourseRankDTO value : courseRankDTOList) {
            if (score.equals(value.getScore())) {
                return value;
            }
        }
        return new CourseRankDTO();
    }
}
