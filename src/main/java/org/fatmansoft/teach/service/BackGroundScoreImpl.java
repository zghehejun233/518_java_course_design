package org.fatmansoft.teach.service;

import org.fatmansoft.teach.repository.student_basic.StudentRepository;
import org.fatmansoft.teach.service.IntroduceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author GuoSurui
 */
@Service
public class BackGroundScoreImpl {
    @Resource
    private IntroduceImpl introduce;
    @Resource
    private StudentRepository studentRepository;

}
