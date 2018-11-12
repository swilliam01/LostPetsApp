package com.example.demo;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class SSUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    public SSUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            User appUser = userRepository.findByUsername(username);
            if(appUser == null){
                System.out.println("User not found with the provided username" + appUser.toString());
                return null;
            }
            System.out.println("User from username" + appUser.toString());
            return new CustomUserDetails(appUser, getAuthorities(appUser));
        } catch (Exception e){
            throw new UsernameNotFoundException("User not found");
        }
    }


//    public User getCurrentUser(Principal principal) {
//
//        return ((User) SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getPrincipal());
//    }
    private Set<GrantedAuthority> getAuthorities(User appUser){
        Set<GrantedAuthority> authorities= new HashSet<GrantedAuthority>();
        for (Role role : appUser.getRoles()){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }
        System.out.println("User authorities are" + authorities.toString());
        return authorities;
    }

}

