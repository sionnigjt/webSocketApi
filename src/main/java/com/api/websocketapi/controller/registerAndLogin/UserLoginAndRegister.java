package com.api.websocketapi.controller.registerAndLogin;

import com.api.websocketapi.entity.Userschema;
import com.api.websocketapi.service.UserschemaService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sion
 */
@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserLoginAndRegister {
    /**
     * 服务对象
     */
    @Resource
    private UserschemaService userschemaService;

    /**
     *注册功能
     */
    @RequestMapping("/register")
    public ResponseEntity<Userschema> register(@RequestBody Userschema userschema) {
        System.out.println(userschema.getName()+userschema.getPassword());
        return ResponseEntity.ok(this.userschemaService.insert(userschema));
    }

    @RequestMapping("/login")
    public ResponseEntity<String> login(@RequestBody Userschema user) {
        return this.userschemaService.login(user.getName(),user.getPassword());
    }

}
