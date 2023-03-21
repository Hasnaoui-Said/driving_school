package next.sh.driving_school.rest.provided.facade;

import next.sh.driving_school.exception.BadRequestException;
import next.sh.driving_school.models.domain.ResponseObject;
import next.sh.driving_school.models.entity.Eleve;
import next.sh.driving_school.rest.converter.EleveConverter;
import next.sh.driving_school.rest.provided.vo.EleveVo;
import next.sh.driving_school.service.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("${api.endpoint}/eleve")
public class EleveRest {
    @Autowired
    EleveConverter eleveConverter;
    @Autowired
    EleveService eleveService;


    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String hello() {
        return "micro service users works!!";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<ResponseObject<?>> save(@RequestBody @Valid EleveVo user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            ResponseObject<Map<String, String>> responseObject = new ResponseObject<>(false,
                    "Eleve not valid!!", errors);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
        try {
            Eleve userSave = eleveService.save(this.eleveConverter.toBean(user));
            ResponseObject<EleveVo> responseObject = new ResponseObject<>(true,
                    "Eleve saved successfully", this.eleveConverter.toVo(userSave));
            return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            ResponseObject<EleveVo> responseObject = new ResponseObject<>(false,
                    e.getMessage(), user);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<ResponseObject<?>> findAll() {
        List<Eleve> all = this.eleveService.findAll();
        ResponseObject<List<EleveVo>> responseObject = new ResponseObject<>(false,
                "Find all!!", this.eleveConverter.toVos(all));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/uuid/{uuid}")
    public ResponseEntity<ResponseObject<?>> findByUuid(@PathVariable String uuid) {
        try {
            UUID uuid1 = UUID.fromString(uuid);
            Eleve user = this.eleveService.findByUuid(uuid1);
            ResponseObject<EleveVo> responseObject = new ResponseObject<>(true,
                    "Find by uuid user!!", this.eleveConverter.toVo(user));
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (IllegalArgumentException | NullPointerException e) {
            ResponseObject<String> responseObject = new ResponseObject<>(false,
                    e.getMessage(), uuid);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/uuid/{uuid}")
    public ResponseEntity<ResponseObject<?>> deleteByUuid(@PathVariable String uuid) {
        try {
            UUID uuid1 = UUID.fromString(uuid);
            int result = this.eleveService.deleteByUuid(uuid1);
            if (result != 1)
                throw new BadRequestException(String.format("User with this uuid ' %s ' not found", uuid));

            ResponseObject<?> responseObject = new ResponseObject<>(true,
                    "User deleted successfully", result);
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (IllegalArgumentException | NullPointerException | BadRequestException e) {
            ResponseObject<String> responseObject = new ResponseObject<>(false,
                    e.getMessage(), uuid);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/email/{email}")
    public ResponseEntity<ResponseObject<?>> deleteByEmail(@PathVariable String email) {
        try {
            int result = this.eleveService.deleteByEmail(email);
            if (result != 1)
                throw new BadRequestException(String.format("User with this email ' %s ' not found", email));

            ResponseObject<?> responseObject = new ResponseObject<>(true,
                    "User deleted successfully", result);
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (BadRequestException e) {
            ResponseObject<String> responseObject = new ResponseObject<>(false,
                    e.getMessage(), email);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/username/{username}")
    public ResponseEntity<ResponseObject<?>> findByUsername(@PathVariable String username) {
        Eleve byUsername = this.eleveService.findByUsername(username).orElse(null);
        ResponseObject<EleveVo> responseObject = new ResponseObject<>(true,
                "Find user by username!!", this.eleveConverter.toVo(byUsername));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/email/{email}")
    public ResponseEntity<ResponseObject<?>> findByEmail(@PathVariable String email) {
        Eleve user = this.eleveService.findByEmail(email);
        ResponseObject<EleveVo> responseObject = new ResponseObject<>(true,
                "Find user by email!!", this.eleveConverter.toVo(user));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

}
