package org.fatmansoft.teach.service.academic_activity;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.academic_activity.Lecture;
import org.fatmansoft.teach.models.academic_activity.Practice;
import org.fatmansoft.teach.models.student_basic.Student;
import org.fatmansoft.teach.repository.academic_activity.LectureRepository;
import org.fatmansoft.teach.repository.academic_activity.PracticeRepository;
import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.fatmansoft.teach.util.DateTimeTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Setter
@Getter
public class LectureImpl {
    @Resource
    private LectureRepository lectureRepository;
    @Resource
    private StudentRepository studentRepository;

    private Integer studentId;

    List<Object> queryAllLecture() {
        List<Object> result = new ArrayList<>();
        List<Lecture> lectureList = lectureRepository.findLecturesByStudent_StudentId(studentId);
        if (lectureList.size() == 0) {
            return result;
        }
        Lecture lecture;
        Map<String, Object> tempMap;
        for (Lecture value : lectureList) {
            lecture = value;
            tempMap = new HashMap<>();
            tempMap.put("id", lecture.getLectureId());
            tempMap.put("theme", lecture.getTheme());
            tempMap.put("studentName",lecture.getStudent().getStudentName());
            tempMap.put("presenter", lecture.getPresenter());
            tempMap.put("content", lecture.getContent());
            tempMap.put("location", lecture.getLocation());
            tempMap.put("time", DateTimeTool.parseDateTime(lecture.getTime(),"yyyy-MM-dd"));
            result.add(tempMap);
        }
        return result;
    }

    Map<String, Object> queryLectureDetail(Integer lectureId) {
        Lecture lecture = getLecture(lectureId);
        Map<String, Object> resultMap = new HashMap<>();
        if (lecture != null) {
            resultMap.put("id", lecture.getLectureId());
            resultMap.put("theme", lecture.getTheme());
            resultMap.put("studentName",lecture.getStudent().getStudentName());
            resultMap.put("presenter", lecture.getPresenter());
            resultMap.put("content", lecture.getContent());
            resultMap.put("location", lecture.getLocation());
            resultMap.put("time", lecture.getTime());
        }else {
            Optional<Student> op = studentRepository.findById(studentId);
            op.ifPresent(student -> resultMap.put("studentName",student.getStudentName()));
        }
        return resultMap;
    }

    public Integer insertLecture(Lecture lectureData) {
        Lecture lecture = getLecture(lectureData.getLectureId());
        Integer maxLectureId = null;
        if (lecture == null) {
            lecture = new Lecture();
            maxLectureId = lectureRepository.getMaxId();
            if (maxLectureId == null) {
                maxLectureId = 1;
            } else {
                maxLectureId += 1;
            }
            lecture.setLectureId(maxLectureId);
        }
        lecture.setTheme(lectureData.getTheme());
        lecture.setPresenter(lectureData.getPresenter());
        lecture.setContent(lectureData.getContent());
        lecture.setLocation(lectureData.getLocation());
        lecture.setTime(lectureData.getTime());

        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            lecture.setStudent(relatedStudent);
        }
        lectureRepository.save(lecture);
        return maxLectureId;
    }

    public void dropLecture(Integer lectureId) {
        Lecture lecture = getLecture(lectureId);
        Student relatedStudent;
        Optional<Student> opStudent = studentRepository.findById(studentId);
        if (opStudent.isPresent()) {
            relatedStudent = opStudent.get();
            relatedStudent.getLectures().remove(lecture);
        }
        lectureRepository.delete(lecture);
    }

    private Lecture getLecture(Integer lectureId) {
        Lecture lecture = null;
        Optional<Lecture> op;
        if (lectureId != null) {
            op = lectureRepository.findById(lectureId);
            if (op.isPresent()) {
                lecture = op.get();
            }
        }
        return lecture;
    }


}
