package com.api.websocketapi.dao;

import com.api.websocketapi.entity.Userschema;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Userschema)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-05 12:47:53
 */
@Mapper
public interface UserschemaDao {
    /**
    * 通过username查询单条数据
    *
    * @param username 姓名
     * @return User实例对象
    */
    Userschema selectByUsername(String username);
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Userschema queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param userschema 查询条件
     * @param pageable   分页对象
     * @return 对象列表
     */
    List<Userschema> queryAllByLimit(Userschema userschema, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param userschema 查询条件
     * @return 总行数
     */
    long count(Userschema userschema);

    /**
     * 新增数据
     *
     * @param userschema 实例对象
     * @return 影响行数
     */
    int insert(Userschema userschema);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Userschema> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Userschema> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Userschema> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Userschema> entities);

    /**
     * 修改数据
     *
     * @param userschema 实例对象
     * @return 影响行数
     */
    int update(Userschema userschema);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

