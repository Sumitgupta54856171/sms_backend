package com.example.schoolsystem.service;


import com.example.schoolsystem.entity.User;
import com.example.schoolsystem.repository.Userrepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final Userrepo userrepo;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


            User user = userrepo.findByEmail(email);

            if(user == null){
                throw new UsernameNotFoundException("User not found with email: " + email);
            }


            String roleName = "ROLE_" + user.getRole();


            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(roleName))
            );

    }
}
