package com.bootai.boot.service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
//import java.util.Base64.Encoder;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bootai.boot.entity.UserInfo;
import com.bootai.boot.repository.UserInfoRepository;

@Service
public class UserInfoService implements UserDetailsService{
    
    
    private final UserInfoRepository userRepository;

    private final PasswordEncoder encoder;

    
    public UserInfoService(UserInfoRepository repository,@Lazy PasswordEncoder encoder) {
        this.userRepository = repository;
        this.encoder = encoder;
    }
    public UserInfo addNewUser(UserInfo userInfo)
    {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));//encoding password before saving it
        return userRepository.save(userInfo);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("In UserInfoService -- loadUserByUsername() -- loading details of : "+username);
        Optional<UserInfo> userInfo=userRepository.findByEmail(username);
        if(userInfo.isEmpty())
            throw new UsernameNotFoundException("Invalid User");

        UserInfo user=userInfo.get();

        List<GrantedAuthority> authorities=Arrays.stream(user.getRoles().split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
        Iterator<GrantedAuthority> itr=authorities.iterator();
        System.out.println("Authorities of the user: "+username);
        while(itr.hasNext())
            System.out.println(itr.next().toString());

        return new User(user.getEmail(),user.getPassword(),authorities);

    }

}
