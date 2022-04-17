package org.fatmansoft.teach.service.academic;

import org.fatmansoft.teach.payload.request.DataRequest;
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

    public List<Object> getAllReferenceMapList(DataRequest dataRequest){
        //TODO

    }

    public Map<String,Object> getReferenceDetail(DataRequest dataRequest) {
        //TODO
    }

    public Integer saveReference(DataRequest dataRequest){
        //TODO
    }

    public void deleteReference(DataRequest dataRequest) {
        //TODO
    }

}
