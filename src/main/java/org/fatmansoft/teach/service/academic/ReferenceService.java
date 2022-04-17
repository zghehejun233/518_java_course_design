package org.fatmansoft.teach.service.academic;

import org.fatmansoft.teach.models.academic.Reference;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author GuoSurui
 */
@Service
public class ReferenceService {
    @Resource
    private ReferenceImpl reference;

    public List<Object> getAllReferenceMapList(DataRequest dataRequest) {
        reference.setCourseId(dataRequest.getInteger("courseId"));
        return reference.queryReferenceMapList();
    }

    public Map<String, Object> getReferenceDetail(DataRequest dataRequest) {
        Integer referenceId = dataRequest.getInteger("id");
        return reference.queryReferenceDetail(referenceId);
    }

    public Integer saveReference(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        Reference referenceData = new Reference();
        referenceData.setReferenceId(CommonMethod.getInteger(form, "id"));
        referenceData.setType(CommonMethod.getString(form, "type"));
        referenceData.setLanguage(CommonMethod.getString(form, "language"));
        referenceData.setAuthor(CommonMethod.getString(form, "author"));
        referenceData.setDetail(CommonMethod.getString(form, "detail"));
        return reference.insertReference(referenceData);
    }

    public void deleteReference(DataRequest dataRequest) {
        Integer id =dataRequest.getInteger("id");
        reference.dropReference(id);
    }

}
