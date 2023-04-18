package com.api.websocketapi.service;

import com.api.websocketapi.entity.Userschema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * (Userschema)表服务接口
 *
 * @author makejava
 * @since 2023-03-05 12:47:54
 */
@Service
public interface UserschemaService  {
    /**
     * 通过username查询单条数据
     *
     * @param  id
     * @return String url
     */
    String getImgUrlById(Integer id);
    /**
     * 通过username查询单条数据
     *
     * @param  id
     * @return String 姓名
     */
    String getNameById(Integer id);
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Userschema queryById(Integer id);

    /**
     * 分页查询
     *
     * @param userschema  筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    Page<Userschema> queryByPage(Userschema userschema, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param userschema 实例对象
     * @return 实例对象
     */
    Userschema insert(Userschema userschema);

    /**
     * 修改数据
     *
     * @param userschema 实例对象
     * @return 实例对象
     */
    Userschema update(Userschema userschema);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);
    /**
     * 通过主键删除数据
     *
     * @param  name 姓名
     * @param  password 密码
     * @return 是否成功
     */
    ResponseEntity<String> login(String name, String password);


}
