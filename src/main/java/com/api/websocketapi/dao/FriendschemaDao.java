package com.api.websocketapi.dao;

import com.api.websocketapi.entity.Friendschema;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (Friendschema)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-04 23:53:42
 */
@Mapper
public interface FriendschemaDao {
    /**
     * 通过ID查询好友表
     *
     * @param id 主键
     * @return 实例对象
     */
    List<Friendschema>  getFriendList(Integer id);
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
     * @return 影响行数
     */
    int insert(Friendschema friendschema);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Friendschema> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Friendschema> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Friendschema> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Friendschema> entities);

    /**
     * 修改数据
     *
     * @param friendschema 实例对象
     * @return 影响行数
     */
    int update(Friendschema friendschema);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

