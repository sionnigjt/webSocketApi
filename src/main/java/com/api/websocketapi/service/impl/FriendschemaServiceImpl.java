package com.api.websocketapi.service.impl;

import com.api.websocketapi.dao.FriendschemaDao;
import com.api.websocketapi.entity.Friendschema;
import com.api.websocketapi.entity.friendList;
import com.api.websocketapi.service.FriendschemaService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Friendschema)表服务实现类
 *
 * @author makejava
 * @since 2023-04-04 23:53:42
 */
@Service("friendschemaService")
public class FriendschemaServiceImpl implements FriendschemaService {
    @Resource
    private FriendschemaDao friendschemaDao;

    @Override
    public ResponseEntity<List<friendList>> getFriendList(Integer id) {
        return ResponseEntity.ok(friendschemaDao.getFriendList(id));
    }

    @Override
    public ResponseEntity<List<friendList>> getUnFriendList(Integer id) {
        List<friendList> FriendschemaList=friendschemaDao.getUnFriendList(id);
        return ResponseEntity.ok(FriendschemaList);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Friendschema queryById(Integer id) {
        return this.friendschemaDao.queryById(id);
    }

    @Override
    public Boolean addFriend(Friendschema friendschema) {
        List<friendList> FriendschemaList=friendschemaDao.getFriendList(friendschema.getUserid());
        Boolean isExit=false;
        for (friendList item:FriendschemaList){
            if (item.getUserId().equals(friendschema.getUserid()) && item.getFriendId().equals(friendschema.getFriendid())){
                isExit=true;
            }
        }
        if (!isExit){
            friendschemaDao.insert(friendschema);
            return  true;
        }
        return false;
    }

    /**
     * 新增数据
     *
     * @param friendschema 实例对象
     * @return 实例对象
     */
    @Override
    public Friendschema insert(Friendschema friendschema) {
        this.friendschemaDao.insert(friendschema);
        return friendschema;
    }

    /**
     * 修改数据
     *
     * @param friendschema 实例对象
     * @return 实例对象
     */
    @Override
    public Friendschema update(Friendschema friendschema) {
        this.friendschemaDao.update(friendschema);
        return this.queryById(friendschema.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.friendschemaDao.deleteById(id) > 0;
    }
}
