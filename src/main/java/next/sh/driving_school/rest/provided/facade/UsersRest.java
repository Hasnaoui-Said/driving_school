package next.sh.driving_school.rest.provided.facade;

import next.sh.driving_school.exception.BadRequestException;
import next.sh.driving_school.models.domain.ResponseObject;
import next.sh.driving_school.models.entity.Eleve;
import next.sh.driving_school.models.entity.User;
import next.sh.driving_school.rest.converter.UserConverter;
import next.sh.driving_school.rest.provided.vo.EleveVo;
import next.sh.driving_school.rest.provided.vo.UserVo;
import next.sh.driving_school.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("${api.endpoint}/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UsersRest {
    @Autowired
    UserConverter userConverter;
    @Autowired
    UserDetailsServiceImpl usersService;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity<ResponseObject<?>> findAll() {
        List<User> all = this.usersService.findAll();
        ResponseObject<List<UserVo>> responseObject = new ResponseObject<>(true,
                "Find all!!", this.userConverter.toVos(all));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public ResponseEntity<ResponseObject<?>> findAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "2") int size) {
        Page<User> all = this.usersService.findAll(PageRequest.of(page, size));
        ResponseObject<Page<UserVo>> responseObject = new ResponseObject<>(true,
                "Find all!!", this.userConverter.toVos(all));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<ResponseObject<?>> save(@RequestBody @Valid UserVo user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            ResponseObject<Map<String, String>> responseObject = new ResponseObject<>(false,
                    "User not valid!!", errors);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
        try {
            User userSave = usersService.save(this.userConverter.toBean(user));
            ResponseObject<UserVo> responseObject = new ResponseObject<>(true,
                    "User saved successfully", this.userConverter.toVo(userSave));
            return new ResponseEntity<>(responseObject, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            ResponseObject<UserVo> responseObject = new ResponseObject<>(false,
                    e.getMessage(), user);
            return new ResponseEntity<>(responseObject, HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(method = RequestMethod.GET, value = "/uuid/{uuid}")
    public ResponseEntity<ResponseObject<?>> findByUuid(@PathVariable String uuid) {
        try {
            UUID uuid1 = UUID.fromString(uuid);
            User user = this.usersService.findByUuid(uuid1);
            ResponseObject<UserVo> responseObject = new ResponseObject<>(true,
                    "Find by uuid user!!", this.userConverter.toVo(user));
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
            int result = this.usersService.deleteByUuid(uuid1);
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
            int result = this.usersService.deleteByEmail(email);
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
        User byUsername = this.usersService.findByUsername(username).orElse(null);
        ResponseObject<UserVo> responseObject = new ResponseObject<>(true,
                "Find user by username!!", this.userConverter.toVo(byUsername));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/email/{email}")
    public ResponseEntity<ResponseObject<?>> findByEmail(@PathVariable String email) {
        User user = this.usersService.findByEmail(email);
        ResponseObject<UserVo> responseObject = new ResponseObject<>(true,
                "Find user by email!!", this.userConverter.toVo(user));
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }

}
