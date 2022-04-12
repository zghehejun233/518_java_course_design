package org.fatmansoft.teach.controllers;

import org.fatmansoft.teach.models.Course;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.repository.CourseRepository;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/teach")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    public List getCourseMapList(String numName) {
        List dataList = new ArrayList();
        List<Course> cList = courseRepository.findAll();  //数据库查询操作
        if (cList == null || cList.size() == 0)
            return dataList;
        Course c;
        Map m;
        for (int i = 0; i < cList.size(); i++) {
            c = cList.get(i);
            m = new HashMap();
            m.put("id", c.getId());
            m.put("courseNum", c.getCourseNum());
            m.put("courseName", c.getCourseName());
            m.put("teacherName", c.getTeacherName());
            m.put("classroom", c.getClassroom());
            m.put("time", c.getTime());
            dataList.add(m);
        }
        return dataList;
    }

    @PostMapping("/courseInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseInit(@Valid @RequestBody DataRequest dataRequest) {
        List dataList = getCourseMapList("");
        return CommonMethod.getReturnData(dataList);
    }

    @PostMapping("/courseEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Integer id = dataRequest.getInteger("id");
        Course c= null;
        Optional<Course> op;
        if(id != null) {
            op= courseRepository.findById(id);
            if(op.isPresent()) {
                c = op.get();
            }
        }
        Map form = new HashMap();
        if(c != null) {
            form.put("id",c.getId());
            form.put("courseNum",c.getCourseNum());
            form.put("courseName",c.getCourseName());
            form.put("teacherName",c.getTeacherName());
            form.put("classroom",c.getClassroom());
            form.put("time",c.getTime());
        }
        return CommonMethod.getReturnData(form);
    }

    @PostMapping("/courseEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Map form = dataRequest.getMap("form"); //参数获取Map对象
        Integer id = CommonMethod.getInteger(form,"id");
        String courseNum = CommonMethod.getString(form,"courseNum");
        String courseName = CommonMethod.getString(form,"courseName");
        String teacherName = CommonMethod.getString(form,"teacherName");
        String classroom = CommonMethod.getString(form,"classroom");
        Date time = CommonMethod.getDate(form,"time");
        Course c= null;
        Optional<Course> op;
        if(id != null) {
            op= courseRepository.findById(id);  //查询对应数据库中主键为id的值的实体对象
            if(op.isPresent()) {
                c = op.get();
            }
        }
        if(c == null) {
            c = new Course();   //不存在 创建实体对象
            id = courseRepository.getMaxId();  // 查询最大的id
            if(id == null) {
                id = 1;
            } else {
                id = id+1;
            }
            c.setId(id);  //设置新的id
        }
        c.setCourseNum(courseNum);
        c.setCourseName(courseName);
        c.setTeacherName(teacherName);
        c.setClassroom(classroom);
        c.setTime(time);
        courseRepository.save(c);  //新建和修改都调用save方法
        return CommonMethod.getReturnData(c.getId());
    }

    @PostMapping("/courseDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse courseDelete(@Valid @RequestBody DataRequest dataRequest){
        Integer id = dataRequest.getInteger("id");  //获取id值
        Course c= null;
        Optional<Course> op;
        if(id != null) {
            op= courseRepository.findById(id);   //查询获得实体对象
            if(op.isPresent()) {
                c = op.get();
            }
        }
        if(c != null) {
            courseRepository.delete(c);    //数据库永久删除
        }
        return CommonMethod.getReturnMessageOk();
    }
}
