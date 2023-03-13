package com.api.websocketapi.controller;

import com.api.websocketapi.entity.Userschema;
import com.api.websocketapi.service.UserschemaService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sion
 */
@RestController
public class UserLoginAndRegister {
    /**
     * 服务对象
     */
    @Resource
    private UserschemaService userschemaService;

    /**
     *注册功能
     * @return
     */
    @RequestMapping("/register")
    public ResponseEntity<Userschema> register(Userschema userschema) {
        return ResponseEntity.ok(this.userschemaService.insert(userschema));
    }

}
