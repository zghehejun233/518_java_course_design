package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.CourseSelection;
import org.fatmansoft.teach.models.Student;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.CourseSelectionRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.fatmansoft.teach.util.DateTimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class CourseSelectionController {
    @Autowired
    private CourseSelectionRepository courseSelectionRepository;

    public List getCourseSelectionMapList(String numName) {
        List dataList = new ArrayList();
        List<CourseSelection> cList = courseSelectionRepository.findAll();  //数据库查询操作
        if (cList == null || cList.size() == 0)
            return dataList;
        CourseSelection c;
        Map m;
        for (int i = 0; i < cList.size(); i++) {
            c = cList.get(i);
            m = new HashMap();
            m.put("id", c.getId());
            m.put("studentNum", courseSelectionRepository.getStudentNum(c.getStudentName()));
            m.put("studentName", c.getStudentName());
            m.put("courseNum", courseSelectionRepository.getCourseNum(c.getCourseName()));
            m.put("courseName", c.getCourseName());
            m.put("studentNameParas","model=studentEdit&id="+c.getId());
            dataList.add(m);
        }
        return dataList;
    }

    @PostMapping("/courseSelectionInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseSelectionInit(@Valid @RequestBody DataRequest dataRequest) {
        List dataList = getCourseSelectionMapList("");
        return CommonMethod.getReturnData(dataList);
    }

    @PostMapping("/courseSelectionEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseSelectionEditinit(@Valid @RequestBody DataRequest dataRequest) {
        Integer id = dataRequest.getInteger("id");
        CourseSelection cs = null;
        Optional<CourseSelection> op;
        if (id != null) {
            op = courseSelectionRepository.findById(id);
            if (op.isPresent()) {
                cs = op.get();
            }
        }
        Map form = new HashMap();
        if (cs != null) {
            form.put("id", cs.getId());
            form.put("studentName", cs.getStudentName());
            form.put("courseName", cs.getCourseName());
        }
        return CommonMethod.getReturnData(form);
    }

    @PostMapping("/courseSelectionEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseSelectionEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Map form = dataRequest.getMap("form");
        Integer id = CommonMethod.getInteger(form, "id");
        String studentName = CommonMethod.getString(form, "studentName");
        String courseName = CommonMethod.getString(form, "courseName");
        CourseSelection cs = null;
        Optional<CourseSelection> op;
        if (id != null) {
            op = courseSelectionRepository.findById(id);
            if (op.isPresent()) {
                cs = op.get();
            }
        }
        if (cs == null) {
            cs = new CourseSelection();
            id = courseSelectionRepository.getMaxId();
            if (id == null) {
                id = 1;
            } else {
                id += 1;
            }
            cs.setId(id);
        }
        cs.setStudentName(studentName);
        cs.setCourseName(courseName);
        courseSelectionRepository.save(cs);
        return CommonMethod.getReturnData(cs.getId());
    }

    @PostMapping("/courseSelectionDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseSelectionDelete(@Valid @RequestBody DataRequest dataRequest) {
        Integer id = dataRequest.getInteger("id");
        CourseSelection cs = null;
        Optional<CourseSelection> op;
        if (id != null) {
            op = courseSelectionRepository.findById(id);
            if (op.isPresent()) {
                cs = op.get();
            }
        }
        if (cs != null) {
            courseSelectionRepository.delete(cs);
        }
        return CommonMethod.getReturnMessageOK();
    }
}
