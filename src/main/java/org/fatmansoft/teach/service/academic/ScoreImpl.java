package org.fatmansoft.teach.service.academic;

import lombok.Getter;
import lombok.Setter;
import org.assertj.core.util.DateUtil;
import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.dto.CourseRankComparator;
import org.fatmansoft.teach.dto.CourseRankDTO;
import org.fatmansoft.teach.dto.CourseRankDTOSerializator;
import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.models.academic.CourseSelection;
import org.fatmansoft.teach.models.academic.HomeWork;
import org.fatmansoft.teach.models.academic.Score;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.GlobalScoreRepository;
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
    @Resource
    private GlobalScoreRepository globalScoreRepository;

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
            tempMap.put("method", parseMethod(score.getMethod()));
            tempMap.put("dailyScore", score.getDailyScore());
            tempMap.put("examScore", score.getExamScore());
            tempMap.put("score", score.getScore());
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

        Map<String, Object> resultMap = new HashMap<>();
        if (score != null) {
            resultMap.put("id", score.getScoreId());
            resultMap.put("method", score.getMethod());
            resultMap.put("dailyScore", score.getDailyScore());
            resultMap.put("examScore", score.getExamScore());
            resultMap.put("score", score.getScore());

            List studentIdList = new ArrayList<>();
            List<Student> studentList = studentRepository.findAll();
            Map map;
            for (Student value : studentList) {
                map = new HashMap<>();
                map.put("label", value.getStudentName());
                map.put("value", value.getStudentId());
                studentIdList.add(map);
            }
            resultMap.put("studentId", "");
            resultMap.put("studentIdList", studentIdList);

            List courseIdList = new ArrayList();
            List<Course> courseList = courseRepository.findAll();
            for (Course value : courseList) {
                map = new HashMap<>();
                map.put("label", value.getName());
                map.put("value", value.getCourseId());
                courseIdList.add(map);
            }
            resultMap.put("courseId", "");
            resultMap.put("courseIdList", courseIdList);
        } else {
            List studentIdList = new ArrayList<>();
            if (studentId != null) {
                Optional<Student> optionalStudent = studentRepository.findById(studentId);
                if (optionalStudent.isPresent()) {
                    Student student = optionalStudent.get();
                    Map map = new HashMap<>();
                    map.put("label", student.getStudentName());
                    map.put("value", student.getStudentId());
                    studentIdList.add(map);
                    resultMap.put("studentId", "");
                    resultMap.put("studentIdList", studentIdList);
                }
            } else {
                List<Student> studentList = studentRepository.findAll();
                Map map;
                for (Student value : studentList) {
                    map = new HashMap<>();
                    map.put("label", value.getStudentName());
                    map.put("value", value.getStudentId());
                    studentIdList.add(map);
                }
                resultMap.put("studentId", "");
                resultMap.put("studentIdList", studentIdList);
            }

            // ????????????????????????????????????
            List<Object> courseIdList = new ArrayList<>();
            if (courseId != null) {
                // ?????????????????????????????????
                Course course;
                Optional<Course> optionalCourse = courseRepository.findById(courseId);
                if (optionalCourse.isPresent()) {
                    course = optionalCourse.get();
                    Map map = new HashMap<>();
                    map.put("label", course.getName());
                    map.put("value", course.getCourseId());
                    courseIdList.add(map);
                    resultMap.put("courseId", course.getName());
                    resultMap.put("courseIdList", courseIdList);
                }
            } else {
                // ???????????????????????????????????????????????????????????????????????????
                List<Course> courseList = courseRepository.findAll();
                Map map;
                for (Course value : courseList) {
                    map = new HashMap<>();
                    map.put("label", value.getName());
                    map.put("value", value.getCourseId());
                    courseIdList.add(map);
                }
                resultMap.put("courseId", "");
                resultMap.put("courseIdList", courseIdList);
            }
        }
        return resultMap;
    }


    public Integer insertScore(Score scoreData, Integer courseIdData, Integer studentIdData) {
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


        Course course;
        Optional<Course> opCourse = courseRepository.findById(courseIdData);
        if (opCourse.isPresent()) {
            course = opCourse.get();
            score.setCourse(course);
        }
        Student student;
        Optional<Student> opStudent = studentRepository.findById(studentIdData);
        if (opStudent.isPresent()) {
            student = opStudent.get();
            score.setStudent(student);
        }
        score.setScore(scoreData.getScore());
        score.setMethod(scoreData.getMethod());
        score.setDailyScore(scoreData.getDailyScore());
        score.setExamScore(scoreData.getExamScore());
        scoreRepository.save(score);
        return maxScoreId;

    }

    public void dropScore(Integer scoreId) {
        Score score;
        Optional<Score> op;
        if (scoreId != null) {
            op = scoreRepository.findById(scoreId);
            if (op.isPresent()) {
                score = op.get();
                SystemApplicationListener.logger.info("[Score]: ??????????????????????????????" + score);

                Course relatedCourse;
                Optional<Course> opCourse = courseRepository.findById(score.getCourse().getCourseId());
                if (opCourse.isPresent()) {
                    relatedCourse = opCourse.get();
                    relatedCourse.getCheckouts().remove(score);
                    SystemApplicationListener.logger.info("[Score]:???????????????????????????" + relatedCourse);

                }

                Student relatedStudent;
                Optional<Student> opStudent = studentRepository.findById(score.getStudent().getStudentId());
                if (opStudent.isPresent()) {
                    relatedStudent = opStudent.get();
                    relatedStudent.getCheckouts().remove(score);
                    SystemApplicationListener.logger.info("[Score]:???????????????????????????" + relatedStudent);
                }
                scoreRepository.delete(score);
                SystemApplicationListener.logger.info("[Score]:?????????????????????????????????");
            }
        }
    }

    /**
     * ???????????????????????????????????????????????????????????????
     *
     * @param course ??????
     * @return CourseRankDTO????????????????????????????????????
     */
    public List<CourseRankDTO>  getCourseRankList(Course course) {
        // ??????????????????????????????Score??????
        List<Score> scoreList = new ArrayList<>(course.getScores());
        // ?????????
        List<CourseRankDTO> courseRankDTOList = new ArrayList<>();
        for (Score value : scoreList) {
            courseRankDTOList.add(new CourseRankDTO(0, 0.0, value.getScore(), 0));
        }
        // ??????
        courseRankDTOList.sort(new CourseRankComparator());
        // ??????????????????????????????????????????
        int sameScoreNum = 1;
        int size = courseRankDTOList.size();
        // ?????????????????????????????????????????????????????????????????????????????????
        for (int i = 0; i < size; i++) {
            // ??????
            courseRankDTOList.get(i).setRank(i + 1);
            // ??????????????????????????????????????????????????????????????????
            if ((i > 1) && courseRankDTOList.get(i).getScore().equals(courseRankDTOList.get(i - 1).getScore())) {
                sameScoreNum += 1;
            } else {
                sameScoreNum = 1;
            }
            courseRankDTOList.get(i).setSameScoreNum(i);
            // ?????????
            courseRankDTOList.get(i).setPercent((courseRankDTOList.get(i).getRank() + 1 - sameScoreNum + 1) / (double) size);
        }
        // ?????????????????????Redis??????
        // ?????????????????????
        List<String> serializedCourseRankDTO = new ArrayList<>();
        for (CourseRankDTO value : courseRankDTOList) {
            serializedCourseRankDTO.add(CourseRankDTOSerializator.serializeCourseRankDTO(value));
        }
        // ???course???id???key??????
        try {
            globalScoreRepository.drop("course:" + (course.getCourseId() - 1));
            SystemApplicationListener.logger.info("????????????key???value");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        globalScoreRepository.leftPushStringList("course:" + (course.getCourseId() - 1), serializedCourseRankDTO);
        SystemApplicationListener.logger.info("??????????????????????????????");
        // ??????????????????
        try {
            globalScoreRepository.drop("courseTime:" + (course.getCourseId() - 1));
            SystemApplicationListener.logger.info("????????????key???value");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        globalScoreRepository.setString("courseTime:" + (course.getCourseId() - 1), DateUtil.now().toString());
        SystemApplicationListener.logger.info("??????????????????????????????");
        return courseRankDTOList;
    }

    /**
     * ?????????????????????????????????????????????????????????
     *
     * @param courseRankDTOList ????????????????????????
     * @param score             ?????????????????????
     * @return ???????????????CourseRankDTO
     */
    public CourseRankDTO getCourseRank(List<CourseRankDTO> courseRankDTOList, Integer score) {
        for (CourseRankDTO value : courseRankDTOList) {
            if (score.equals(value.getScore())) {
                return value;
            }
        }
        return new CourseRankDTO();
    }

    String parseMethod(String methodCode) {
        String method;
        switch (methodCode) {
            case "1": {
                method = "?????????";
                break;
            }
            case "2": {
                method = "?????????";
                break;
            }
            case "3": {
                method = "?????????";
                break;
            }
            default:
                method = "??????";
        }
        return method;
    }
}