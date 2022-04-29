package org.fatmansoft.teach.controllers.student_basic;

import org.fatmansoft.teach.SpringBootSecurityJwtApplication;
import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.student_basic.StudentService;
import org.fatmansoft.teach.util.CommonMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.ConstraintDeclarationException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;

/**
 * @author GuoSurui
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class StudentController {
    @Resource
    private StudentService studentService;

    @PostMapping("/studentInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentInit(@Valid @RequestBody DataRequest dataRequest) {
        SystemApplicationListener.logger.info("studentInitStart");
        List<Object> result = studentService.getAllStudent(dataRequest);
        SystemApplicationListener.logger.info("studentInitEnd");
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("studentEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = studentService.getStudentDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/studentEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        try {
            Integer result = studentService.saveStudent(dataRequest);
            return CommonMethod.getReturnData(result);
        } catch (ValidationException validationException) {
            String temp = validationException.getLocalizedMessage();
            return CommonMethod.getReturnMessage("400", temp.substring(temp.substring(0, temp.indexOf(":")).length() + 2));
        } catch (Exception e) {
            return CommonMethod.getReturnMessage("400", "Uncaught");
        }
    }

    @PostMapping("/studentDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentDelete(@Valid @RequestBody DataRequest dataRequest) {
        studentService.deleteStudent(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }

    @PostMapping("/studentQuery")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse studentQuery(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = studentService.getSelectedMapList(dataRequest);
        return CommonMethod.getReturnData(result);
    }

}
