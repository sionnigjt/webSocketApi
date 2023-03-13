package com.api.websocketapi.config.component;

import com.api.websocketapi.dao.UserschemaDao;
import com.api.websocketapi.entity.Userschema;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author sion
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserschemaDao userMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
        // 根据用户名查询数据库中的用户表
        Userschema user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 返回一个User对象，包含用户名、密码和权限信息
        return new User(username, user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
    }


}
