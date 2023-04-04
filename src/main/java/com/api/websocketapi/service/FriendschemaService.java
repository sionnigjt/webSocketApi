package com.api.websocketapi.service;

import com.api.websocketapi.entity.Friendschema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * (Friendschema)表服务接口
 *
 * @author makejava
 * @since 2023-04-04 23:53:42
 */
public interface FriendschemaService {
    /**
     * 通过ID查询单用户表
     *
     * @param id 主键
     * @return 实例对象
     */
    ResponseEntity<List<Friendschema>> getFriendList(Integer id);
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Friendschema queryById(Integer id);


    /**
     * 新增数据
     *
     * @param friendschema 实例对象
     * @return 实例对象
     */
    Friendschema insert(Friendschema friendschema);

    /**
     * 修改数据
     *
     * @param friendschema 实例对象
     * @return 实例对象
     */
    Friendschema update(Friendschema friendschema);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
