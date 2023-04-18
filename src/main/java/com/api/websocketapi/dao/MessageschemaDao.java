package com.api.websocketapi.dao;

import com.api.websocketapi.entity.MessageContent;
import com.api.websocketapi.entity.Messageschema;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (Messageschema)表数据库访问层
 *
 * @author makejava
 * @since 2023-04-07 09:52:25
 */
@Mapper
public interface MessageschemaDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userId
     * @return 实例对象
     */
    List<MessageContent>  selectUnreadListByUserId(Integer userId);
    /**
     * 修改数据
     *
     * @param userId
     * @return 影响行数
     */
    int changeSateToRead(Integer userId);
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Messageschema queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param messageschema 查询条件
     * @param pageable      分页对象
     * @return 对象列表
     */
    List<Messageschema> queryAllByLimit(Messageschema messageschema, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param messageschema 查询条件
     * @return 总行数
     */
    long count(Messageschema messageschema);

    /**
     * 新增数据
     *
     * @param messageschema 实例对象
     * @return 影响行数
     */
    int insert(Messageschema messageschema);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Messageschema> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Messageschema> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Messageschema> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Messageschema> entities);

    /**
     * 修改数据
     *
     * @param messageschema 实例对象
     * @return 影响行数
     */
    int update(Messageschema messageschema);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

