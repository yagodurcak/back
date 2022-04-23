package ar.com.indumet.workload.controllers;

import ar.com.indumet.workload.models.vos.UserDataVO;
import ar.com.indumet.workload.services.UsersService;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
@CrossOrigin
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<UserDataVO> findAll() {
        return usersService.findAll();
    }

    @RequestMapping(value = "/{uid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserDataVO find(@PathVariable("uid") String uid) throws FirebaseAuthException {
        return usersService.find(uid);
    }

    @RequestMapping(value = "/claims/{uid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserDataVO find(@PathVariable(name = "uid") String uid,@RequestBody Map<String, Object> claims) throws FirebaseAuthException {
        return usersService.setClaims(uid, claims);
    }
}
