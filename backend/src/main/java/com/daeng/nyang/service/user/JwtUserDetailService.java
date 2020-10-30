package com.daeng.nyang.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.daeng.nyang.dto.Account;
import com.daeng.nyang.repo.AccountRepo;

@Service
public class JwtUserDetailService implements UserDetailsService {
	
	@Autowired
	private AccountRepo accountRepo;

	@Override
	public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
		Account user = accountRepo.findByUserid(user_id);
		List<GrantedAuthority> roles = new ArrayList<>();

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + user_id);
        }
        if ((user.getRole()).equals("ROLE_ADMIN")) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new User(user.getUser_id(), user.getUser_password(), roles);
	}

}
