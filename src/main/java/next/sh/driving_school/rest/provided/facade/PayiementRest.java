package next.sh.driving_school.rest.provided.facade;

import next.sh.driving_school.exception.BadRequestException;
import next.sh.driving_school.models.domain.ResponseObject;
import next.sh.driving_school.models.entity.Eleve;
import next.sh.driving_school.models.entity.Payiement;
import next.sh.driving_school.rest.converter.EleveConverter;
import next.sh.driving_school.rest.converter.PayiementConverter;
import next.sh.driving_school.rest.provided.vo.EleveVo;
import next.sh.driving_school.rest.provided.vo.PayiementVo;
import next.sh.driving_school.service.EleveService;
import next.sh.driving_school.service.PayiementService;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("${api.endpoint}/payiement")
public class PayiementRest {


    @Autowired
    private PayiementConverter payiementConverter;
    @Autowired
    private PayiementService payiementService;

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String hello() {
        return "micro service payiement works!!";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<ResponseObject<?>> save(@RequestBody @Valid PayiementVo payiement, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            ResponseObject<Map<String, String>> responseObject = new ResponseObject<>(false,
                    "Payiement not valid!!", errors);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
        try {
            Payiement userSave = payiementService.save(this.payiementConverter.toBean(payiement));
            ResponseObject<PayiementVo> responseObject = new ResponseObject<>(true,
                    "Payiement saved successfully", this.payiementConverter.toVo(userSave));
            return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            ResponseObject<PayiementVo> responseObject = new ResponseObject<>(false,
                    e.getMessage(), payiement);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<ResponseObject<?>> getPayiement(Principal principal) {
            Payiement user = this.payiementService.findByEleveUsername(principal.getName()).orElse(null);
            ResponseObject<PayiementVo> responseObject = new ResponseObject<>(true,
                    "Find by principal !!", this.payiementConverter.toVo(user));
            return new ResponseEntity<>(responseObject, HttpStatus.OK);

    }

}
