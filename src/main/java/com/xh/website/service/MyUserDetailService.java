package com.xh.website.service;

import com.xh.website.module.user.entity.Role;
import com.xh.website.module.user.entity.User;
import com.xh.website.module.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author xinghao
 * @descreption 返回用户名密码和权限
 * @date 2018/12/18
 */

@Service
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByUserName(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("用户未注册");
        }
        User user = userOptional.get();
        if (user.getRole() == Role.USER) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (user.getRole() == Role.ADMIN) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassWord(),authorities);
    }

    public boolean addUser(User user) throws Exception{
        Optional<User> userOptional = userRepository.findByUserName(user.getUserName());
        if (userOptional.isPresent()) {
            throw new Exception("用户名已经注册");
        }

        user.setPassWord(new BCryptPasswordEncoder().encode(user.getPassWord()));
        userRepository.save(user);
        return true;
    }

}
