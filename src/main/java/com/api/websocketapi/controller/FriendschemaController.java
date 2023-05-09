package com.api.websocketapi.controller;

import com.api.websocketapi.entity.Friendschema;
import com.api.websocketapi.entity.friendList;
import com.api.websocketapi.service.FriendschemaService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * (Friendschema)表控制层
 *
 * @author makejava
 * @since 2023-04-04 23:53:42
 */
@RestController
@CrossOrigin
@RequestMapping("/api/friend")
public class FriendschemaController {
    /**
     * 服务对象
     */
    @Resource
    private FriendschemaService friendschemaService;
    /**
     * 通过主键查询用户表
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/getFriendList/{id}")
    public ResponseEntity<List<friendList>> getFriendList(@PathVariable("id") Integer id) {
        return this.friendschemaService.getFriendList(id);
    }
    /**
     * 通过主键查询用户表
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/getUnFriendList/{id}")
    public ResponseEntity<List<friendList>> getUnFriendList(@PathVariable("id") Integer id) {
        return this.friendschemaService.getUnFriendList(id);
    }
    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/queryById/{id}")
    public ResponseEntity<Friendschema> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.friendschemaService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param friendschema 实体
     * @return 新增结果
     */

    @PostMapping("/addFriend")
    public ResponseEntity<Boolean> add(@RequestBody Friendschema friendschema) {
        return ResponseEntity.ok(this.friendschemaService.addFriend(friendschema));
    }
    @PostMapping("/agreeFriend")
    public ResponseEntity<Friendschema> agreeFriend(@RequestBody Friendschema friendschema) {
        return ResponseEntity.ok(this.friendschemaService.update(friendschema));
    }
    /**
     * 编辑数据
     *
     * @param friendschema 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Friendschema> edit(Friendschema friendschema) {
        return ResponseEntity.ok(this.friendschemaService.update(friendschema));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.friendschemaService.deleteById(id));
    }

}

