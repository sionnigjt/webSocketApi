package com.api.websocketapi.service.impl;

import com.api.websocketapi.entity.Friendschema;
import com.api.websocketapi.entity.MessageContent;
import com.api.websocketapi.entity.Messageschema;
import com.api.websocketapi.dao.MessageschemaDao;
import com.api.websocketapi.entity.chatListMessage;
import com.api.websocketapi.service.MessageschemaService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;


/**
 * (Messageschema)表服务实现类
 *
 * @author makejava
 * @since 2023-04-07 09:52:26
 */
@Service("messageschemaService")
public class MessageschemaServiceImpl implements MessageschemaService {
    @Resource
    private MessageschemaDao messageschemaDao;

//    /**
//     * 通过ID查询单条数据
//     *
//     * @param userId
//     * @return 实例对象
//     */
//    @Override
//    public List<MessageContent> selectUnreadListByUserId(Integer userId) {
//        return messageschemaDao.selectUnreadListByUserId(userId);
//    }

    @Override
    public ResponseEntity<List<chatListMessage>> getChatList(Integer id) {
        List<chatListMessage> chatListMessageList= messageschemaDao.getChatList(id);
        for(chatListMessage item:chatListMessageList){
            item.setUnread(messageschemaDao.getUnreadById(item.getSendId()));
        }
        return ResponseEntity.ok(chatListMessageList);
    }

    @Override
    public int changeSateToRead(Integer userId) {
        return messageschemaDao.changeSateToRead(userId);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Messageschema queryById(Integer id) {
        return this.messageschemaDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param messageschema 筛选条件
     * @param pageRequest   分页对象
     * @return 查询结果
     */
    @Override
    public Page<Messageschema> queryByPage(Messageschema messageschema, PageRequest pageRequest) {
        long total = this.messageschemaDao.count(messageschema);
        return new PageImpl<>(this.messageschemaDao.queryAllByLimit(messageschema, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param messageschema 实例对象
     * @return 实例对象
     */
    @Override
    public Messageschema insert(Messageschema messageschema) {
        this.messageschemaDao.insert(messageschema);
        return messageschema;
    }

    /**
     * 修改数据
     *
     * @param messageschema 实例对象
     * @return 实例对象
     */
    @Override
    public Messageschema update(Messageschema messageschema) {
        this.messageschemaDao.update(messageschema);
        return this.queryById(messageschema.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.messageschemaDao.deleteById(id) > 0;
    }
}
