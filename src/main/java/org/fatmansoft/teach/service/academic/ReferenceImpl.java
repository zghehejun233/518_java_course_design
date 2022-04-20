package org.fatmansoft.teach.service.academic;

import lombok.Getter;
import lombok.Setter;
import org.fatmansoft.teach.models.academic.Course;
import org.fatmansoft.teach.models.academic.Reference;
import org.fatmansoft.teach.models.student_basic.Family;
import org.fatmansoft.teach.repository.academic.CourseRepository;
import org.fatmansoft.teach.repository.academic.ReferenceRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author GuoSurui
 */
@Service
@Setter
@Getter
public class ReferenceImpl {
    @Resource
    private ReferenceRepository referenceRepository;
    @Resource
    private CourseRepository courseRepository;

    private Integer courseId;

    public List<Object> queryReferenceMapList() {
        List<Object> result = new ArrayList<>();
        List<Reference> referenceList = referenceRepository.findReferencesByCourse_CourseId(courseId);
        if (referenceList.size() == 0) {
            return result;
        }
        Reference reference;
        Map<String, Object> tempMap;
        for (Reference value : referenceList) {
            reference = value;
            tempMap = new HashMap<>();
            tempMap.put("id", reference.getReferenceId());
            tempMap.put("type", reference.getType());
            tempMap.put("language", reference.getLanguage());
            tempMap.put("author", reference.getDetail());
            tempMap.put("detail", reference.getDetail());
            result.add(tempMap);
        }
        return result;
    }

    public Map<String, Object> queryReferenceDetail(Integer id) {
        Reference reference = getReference(id);
        Map<String, Object> resultMap = new HashMap<>();
        if (reference != null) {
            resultMap.put("id", reference.getReferenceId());
            resultMap.put("type", reference.getType());
            resultMap.put("language", reference.getLanguage());
            resultMap.put("author", reference.getDetail());
            resultMap.put("detail", reference.getDetail());
        }
        return resultMap;
    }

    public Integer insertReference(Reference referenceData) {
        Reference reference = getReference(referenceData.getReferenceId());
        Integer maxReferenceId = null;
        if (reference == null) {
            reference =new Reference();
            maxReferenceId = referenceRepository.getMaxId();
            if (maxReferenceId == null) {
                maxReferenceId = 1;
            } else {
                maxReferenceId += 1;
            }
            reference.setReferenceId(maxReferenceId);
        }
        reference.setType(referenceData.getType());
        reference.setLanguage(referenceData.getLanguage());
        reference.setAuthor(referenceData.getAuthor());
        reference.setDetail(referenceData.getDetail());

        Course relatedCourse;
        Optional<Course> opCourse = courseRepository.findById(courseId);
        if (opCourse.isPresent()) {
            relatedCourse = opCourse.get();
            reference.setCourse(relatedCourse);
        }
        referenceRepository.save(reference);
        return maxReferenceId;
    }

    public void dropReference(Integer id) {
        Reference reference = getReference(id);
        Course relatedCourse;
        Optional<Course> opCourse = courseRepository.findById(courseId);
        if (opCourse.isPresent()) {
            relatedCourse = opCourse.get();
            relatedCourse.getReferences().remove(reference);
        }
        referenceRepository.delete(reference);
    }

    private Reference getReference(Integer referenceId) {
        Reference reference = null;
        Optional<Reference> op;
        if (referenceId != null) {
            op = referenceRepository.findById(referenceId);
            if (op.isPresent()) {
                reference = op.get();
            }
        }
        return reference;
    }
}
