package com.daeng.nyang.service.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.daeng.nyang.dto.Account;
import com.daeng.nyang.dto.Role;
import com.daeng.nyang.repo.AccountRepo;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private AccountRepo accountRepo;

	@Override
	public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
		/** [ 로직 순서 ]
		 * 1. DB에서 user_id를 통해 Account 정보를 가져온다.
		 * 2. Set<GrantedAuthority>를 통해 권한을 부여한다.
		 * 3. user_id가 'admin'인 경우엔 ADMIN 권한을 주고, 아닐경우엔 USER 권한을 줌.
		 * 4. 마지막에는 Security UserDetails에 정의된 User인스턴스에 가져온  Account정보를 담아 반환해준다.
		 * 5. 해당 반환값에 정상적인 UserDetails가 반환되면 인증이 성공하고, 정상적이지 않은  값이 반환되면 인증에 실패한다. */
		
		Account user = accountRepo.findByUserid(user_id);
		if(user==null) {
			throw new UsernameNotFoundException("User not found with userid: " + user_id);
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		if(user_id.equals("admin")) { // 관리자 계정이라면 
			grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
		}else {
			grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
			System.out.println(user.getUser_id()+" " +user.getUser_password()+ " "+ grantedAuthorities);
		}
        return new User(user.getUser_id(), user.getUser_password(), grantedAuthorities);
	}

}
