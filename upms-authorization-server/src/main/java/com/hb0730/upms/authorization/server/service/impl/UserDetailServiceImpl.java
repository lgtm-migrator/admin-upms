package com.hb0730.upms.authorization.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.assertj.core.util.Lists;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * 用户信息
 *
 * @author bing_huang
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetails> userDetails = userList();
        Optional<UserDetails> detailsOptional = userDetails
                .stream()
                .filter(details -> details.getUsername().equals(username)).findFirst();
        if (detailsOptional.isPresent()) {
            return detailsOptional.get();
        }
        throw new UsernameNotFoundException("用户不存在");
    }

    public List<UserDetails> userList() {
        List<UserDetails> users = Lists.newArrayList();
        users.add(User.withUsername("Administrator")
                .password(passwordEncoder.encode("123456"))
                .roles("admin")
                .build());
        users.add(
                User.withUsername("admin")
                        .password(passwordEncoder.encode("123456"))
                        .roles("admin").build()
        );

        return users;
    }
}
