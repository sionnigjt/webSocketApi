package com.api.websocketapi.controller;

import com.api.websocketapi.entity.Messageschema;
import com.api.websocketapi.service.MessageschemaService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * (Messageschema)表控制层
 *
 * @author makejava
 * @since 2023-04-07 09:52:25
 */
@CrossOrigin
@RestController
@RequestMapping("/api/message")
public class MessageschemaController {
    /**
     * 服务对象
     */
    @Resource
    private MessageschemaService messageschemaService;

    /**
     * 分页查询
     *
     * @param messageschema 筛选条件
     * @param pageRequest   分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Messageschema>> queryByPage(Messageschema messageschema, PageRequest pageRequest) {
        return ResponseEntity.ok(this.messageschemaService.queryByPage(messageschema, pageRequest));
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id
     * @return 实例对象
     */
    @GetMapping("{id}")
    public ResponseEntity<List<Messageschema>> selectUnreadListByUserId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.messageschemaService.selectUnreadListByUserId(id));
    }

    /**
     * 新增数据
     *
     * @param messageschema 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Messageschema> add(Messageschema messageschema) {
        return ResponseEntity.ok(this.messageschemaService.insert(messageschema));
    }

    /**
     * 编辑数据
     *
     * @param messageschema 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Messageschema> edit(Messageschema messageschema) {
        return ResponseEntity.ok(this.messageschemaService.update(messageschema));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.messageschemaService.deleteById(id));
    }

}

