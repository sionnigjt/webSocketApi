package com.api.websocketapi.service.impl;

import com.api.websocketapi.entity.Userschema;
import com.api.websocketapi.dao.UserschemaDao;
import com.api.websocketapi.service.UserschemaService;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;


/**
 * (Userschema)表服务实现类
 *
 * @author makejava
 * @since 2023-03-05 12:47:54
 */
@Service("userschemaService")
public class UserschemaServiceImpl implements UserschemaService {
    @Resource
    private UserschemaDao userschemaDao;

    /**
     * 通过username查询单条数据
     *
     * @param  id
     * @return String 姓名
     */
    @Override
    public String getNameById(Integer id) {
        return userschemaDao.getNameById(id);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Userschema queryById(Integer id) {
        return this.userschemaDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param userschema  筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @Override
    public Page<Userschema> queryByPage(Userschema userschema, PageRequest pageRequest) {
        long total = this.userschemaDao.count(userschema);
        return new PageImpl<>(this.userschemaDao.queryAllByLimit(userschema, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param userschema 实例对象
     * @return 实例对象
     */
    @Override
    public Userschema insert(Userschema userschema) {
        //进行加密操作
        userschema.setPassword(new BCryptPasswordEncoder().encode(userschema.getPassword()));
        this.userschemaDao.insert(userschema);
        return userschema;
    }

    /**
     * 修改数据
     *
     * @param userschema 实例对象
     * @return 实例对象
     */
    @Override
    public Userschema update(Userschema userschema) {
        this.userschemaDao.update(userschema);
        return this.queryById(userschema.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userschemaDao.deleteById(id) > 0;
    }

    @Override
    public ResponseEntity<String>  login(String name,String password) {
            Userschema userschema=userschemaDao.selectByUsername(name);
            if (userschema==null){
                return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
            }
           else{
                String encodedPassword=userschema.getPassword();
                if (new BCryptPasswordEncoder().matches(password,encodedPassword)){
                    return ResponseEntity.ok(userschema.getId()+"") ;
                }

            }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");


    }
}
