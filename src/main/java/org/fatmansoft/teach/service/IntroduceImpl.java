package org.fatmansoft.teach.service;

import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.dto.ChartInformationDTO;
import org.fatmansoft.teach.dto.CourseScoresDTO;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.fatmansoft.teach.repository.academic.ScoreRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.fatmansoft.teach.service.academic.ScoreImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.util.*;
import java.util.List;

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

    final static java.lang.String[] COLORS = {"#409EFF", "#67C23A", "#E6A23C", "#F56C6C", "#909399",
            "#c6e2ff", "#b3e19d", "#f3d19e", "#fab6b6", "#c8c9cc"};
    final static Integer COLORS_SIZE = COLORS.length/2-1;
    final Random random = new Random();

    /**
     * 获取学生对象
     *
     * @param studentId 学生Id
     * @return 返回对象
     */
    public Student getStudent(Integer studentId) {
        SystemApplicationListener.logger.warn("getStudent:" +studentId.toString());
        Student student = null;
        Optional<Student> op;
        op = studentRepository.findById(studentId);
        if (op.isPresent()) {
            student = op.get();
        }
        return student;
    }

    /**
     * 获取学生的所有成绩信息
     *
     * @param student             学生对象
     * @param courseScoresDTOList 学生的所有成绩列表
     * @return 图表数据DTO
     */
    public ChartInformationDTO getStudyChartInformation(Student student, List<CourseScoresDTO> courseScoresDTOList) {
        ChartInformationDTO chartInformationDTO = new ChartInformationDTO();
        List<String> studyChartLabels = new ArrayList<>();
        List<Object> studyChartDatasets = new ArrayList<>();
        Map<String, Object> datasetItem = new HashMap<>();
        List<Integer> datasetItemData = new ArrayList<>();
        for (CourseScoresDTO value : courseScoresDTOList) {
            studyChartLabels.add(value.getCourse());
            datasetItemData.add(value.getScore());
        }
        datasetItem.put("data", datasetItemData);
        datasetItem.put("label", student.getStudentName());
        datasetItem.put("fill", true);
        datasetItem.put("backgroundColor", "#c6e2ff");
        datasetItem.put("borderColor", "#79bbff");
        studyChartDatasets.add(datasetItem);
        chartInformationDTO.setLabels(studyChartLabels);
        chartInformationDTO.setDatasets(studyChartDatasets);
        return chartInformationDTO;
    }

    /**
     * 获取一个学生各项事务的数量
     *
     * @param student 学生对象
     * @return 图表数据DTO
     */
    public ChartInformationDTO getLifeChartInformation(Student student) {
        ChartInformationDTO chartInformationDTO = new ChartInformationDTO();
        List<String> lifeChartLabels = new ArrayList<>();
        List<Object> lifeChartDatasets = new ArrayList<>();
        List<String> borderColor = new ArrayList<>();
        List<String> backgroundColor = new ArrayList<>();

        Map<String, Object> datasetItem = new HashMap<>();
        List<Object> datasetItemData = new ArrayList<>();

        lifeChartLabels.add("选课数量");
        datasetItemData.add(student.getCourseSelections().size());
        Integer temp = random.nextInt(COLORS_SIZE);
        borderColor.add(COLORS[temp]);
        backgroundColor.add(COLORS[temp + 5]);

        lifeChartLabels.add("作业与考勤次数");
        datasetItemData.add(student.getHomeWork().size() + student.getCheckouts().size());
        temp = random.nextInt(COLORS_SIZE);
        borderColor.add(COLORS[temp]);
        backgroundColor.add(COLORS[temp + 5]);

        lifeChartLabels.add("教研活动");
        datasetItemData.add(student.getPractices().size() + student.getCompetitions().size()
                + student.getInnovationProjects().size() + student.getInternships().size()
                + student.getLectures().size() + student.getScientificResults().size());
        temp = random.nextInt(COLORS_SIZE);
        borderColor.add(COLORS[temp]);
        backgroundColor.add(COLORS[temp + 5]);

        lifeChartLabels.add("课外活动");
        datasetItemData.add(student.getActivities().size()
                + student.getOutings().size() + student.getCosts().size()
                + student.getAchievements().size());
        temp = random.nextInt(COLORS_SIZE);
        borderColor.add(COLORS[temp]);
        backgroundColor.add(COLORS[temp + 5]);

        datasetItem.put("data", datasetItemData);
        datasetItem.put("label", student.getStudentName());
        lifeChartDatasets.add(datasetItem);

        datasetItem.put("borderColor", borderColor);
        datasetItem.put("backgroundColor", backgroundColor);

        chartInformationDTO.setLabels(lifeChartLabels);
        chartInformationDTO.setDatasets(lifeChartDatasets);
        chartInformationDTO.setColors(borderColor);
        return chartInformationDTO;
    }
}
