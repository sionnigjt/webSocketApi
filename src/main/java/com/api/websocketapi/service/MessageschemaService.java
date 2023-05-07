package com.api.websocketapi.service;
import com.api.websocketapi.entity.Messageschema;
import com.api.websocketapi.entity.chatListMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;


import java.util.List;

/**
 * (Messageschema)表服务接口
 *
 * @author makejava
 * @since 2023-04-07 09:52:26
 */
public interface MessageschemaService {
//    /**
//     * 通过ID查询单条数据
//     *
//     * @param userId
//     * @return 实例对象
//     */
//    List<MessageContent> selectUnreadListByUserId(Integer userId);
    /**
     * 获取最近的聊天消息
     *
     * @param id
     * @return ResponseEntity
     */

    ResponseEntity<List<chatListMessage>> getChatList(Integer id);
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
     * 分页查询
     *
     * @param messageschema 筛选条件
     * @param pageRequest   分页对象
     * @return 查询结果
     */
    Page<Messageschema> queryByPage(Messageschema messageschema, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param messageschema 实例对象
     * @return 实例对象
     */
    Messageschema insert(Messageschema messageschema);

    /**
     * 修改数据
     *
     * @param messageschema 实例对象
     * @return 实例对象
     */
    Messageschema update(Messageschema messageschema);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
