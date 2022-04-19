package org.fatmansoft.teach.controllers.academic;

import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.payload.response.DataResponse;
import org.fatmansoft.teach.service.academic.CheckoutService;
import org.fatmansoft.teach.util.CommonMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author GuoSurui
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/teach")
public class CheckoutController {
    @Resource
    private CheckoutService checkoutService;

    @PostMapping("/checkoutInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse checkoutInit(@Valid @RequestBody DataRequest dataRequest) {
        List<Object> result = checkoutService.getAllCheckout(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/checkoutEditInit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse checkoutEditInit(@Valid @RequestBody DataRequest dataRequest) {
        Map<String, Object> result = checkoutService.getCheckoutDetail(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/checkoutEditSubmit")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse checkoutEditSubmit(@Valid @RequestBody DataRequest dataRequest) {
        Integer result = checkoutService.saveCheckout(dataRequest);
        return CommonMethod.getReturnData(result);
    }

    @PostMapping("/checkoutDelete")
    @PreAuthorize("hasRole('ADMIN')")
    public DataResponse checkoutDelete(@Valid @RequestBody DataRequest dataRequest) {
        checkoutService.deleteCheckout(dataRequest);
        return CommonMethod.getReturnMessageOk();
    }
}
