package org.fatmansoft.teach.service.academic;

import org.fatmansoft.teach.SystemApplicationListener;
import org.fatmansoft.teach.models.academic.Checkout;
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
public class CheckoutService {
    @Resource
    private CheckoutImpl checkout;

    public List<Object> getAllCheckout(DataRequest dataRequest) {
        try {
            checkout.setCourseId(dataRequest.getInteger("courseId"));
        } catch (NullPointerException nullPointerException) {
            SystemApplicationListener.logger.info("courseId设置失败");
        }
        try {
            checkout.setStudentId(dataRequest.getInteger("studentId"));
        } catch (NullPointerException nullPointerException) {
            SystemApplicationListener.logger.info("courseId设置失败");
        }
        return checkout.queryAllCheckout();
    }

    public Map<String, Object> getCheckoutDetail(DataRequest dataRequest) {
        Integer checkoutId = dataRequest.getInteger("id");
        return checkout.queryCheckoutDetail(checkoutId);
    }

    public Integer saveCheckout(DataRequest dataRequest) {
        Map<String, Object> form = dataRequest.getMap("form");
        Checkout checkoutData = new Checkout();
        String courseName = CommonMethod.getString(form, "courseName");
        String studentName = CommonMethod.getString(form, "studentName");
        checkoutData.setCheckoutId(CommonMethod.getInteger(form,"id"));
        checkoutData.setMethod(CommonMethod.getString(form, "method"));
        checkoutData.setState(CommonMethod.getInteger(form, "state"));
        checkoutData.setTime(CommonMethod.getTime(form, "time"));
        return checkout.insertCheckout(checkoutData, courseName, studentName);
    }

    public void deleteCheckout(DataRequest dataRequest) {
        Integer checkoutId = dataRequest.getInteger("id");
        checkout.dropCheckout(checkoutId);
    }

}
