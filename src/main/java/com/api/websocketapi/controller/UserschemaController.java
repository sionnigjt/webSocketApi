package com.api.websocketapi.controller;

import com.api.websocketapi.entity.Userschema;
import com.api.websocketapi.service.UserschemaService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * (Userschema)表控制层
 *
 * @author makejava
 * @since 2023-03-05 12:47:53
 */
@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserschemaController {
    /**
     * 服务对象
     */
    @Resource
    private UserschemaService userschemaService;



    /**
     * 通过username查询单条数据
     *
     * @param  id
     * @return String 姓名
     */
    @GetMapping("/getNameById/{id}")
    public ResponseEntity<String> getNameById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.userschemaService.getNameById(id));
    }

    /**
     * 新增数据
     *
     * @param userschema 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Userschema> add(Userschema userschema) {
        return ResponseEntity.ok(this.userschemaService.insert(userschema));
    }

    /**
     * 编辑数据
     *
     * @param userschema 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Userschema> edit(Userschema userschema) {
        return ResponseEntity.ok(this.userschemaService.update(userschema));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.userschemaService.deleteById(id));
    }

}

