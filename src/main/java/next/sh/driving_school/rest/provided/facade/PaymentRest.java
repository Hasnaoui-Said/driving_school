package next.sh.driving_school.rest.provided.facade;

import next.sh.driving_school.exception.BadRequestException;
import next.sh.driving_school.models.domain.ResponseObject;
import next.sh.driving_school.models.entity.Payment;
import next.sh.driving_school.rest.converter.PaymentConverter;
import next.sh.driving_school.rest.provided.vo.PaymentVo;
import next.sh.driving_school.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("${api.endpoint}/payiement")
public class PaymentRest {


    @Autowired
    private PaymentConverter payiementConverter;
    @Autowired
    private PaymentService paymentService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String hello() {
        return "micro service payiement works!!";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<ResponseObject<?>> save(@RequestBody @Valid PaymentVo payment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            ResponseObject<Map<String, String>> responseObject = new ResponseObject<>(false,
                    "Payiement not valid!!", errors);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
        try {
            Payment userSave = paymentService.save(this.payiementConverter.toBean(payment));
            ResponseObject<PaymentVo> responseObject = new ResponseObject<>(true,
                    "Payiement saved successfully", this.payiementConverter.toVo(userSave));
            return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            ResponseObject<PaymentVo> responseObject = new ResponseObject<>(false,
                    e.getMessage(), payment);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<ResponseObject<?>> getPayiement(Principal principal) {
            Payment user = this.paymentService.findByEleveUsername(principal.getName()).orElse(null);
            ResponseObject<PaymentVo> responseObject = new ResponseObject<>(true,
                    "Find by principal !!", this.payiementConverter.toVo(user));
            return new ResponseEntity<>(responseObject, HttpStatus.OK);

    }

}
