package com.daeng.nyang.service.user;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.daeng.nyang.dto.Account;
import com.daeng.nyang.dto.Role;
import com.daeng.nyang.repo.AccountRepo;

@Service
public class JwtUserDetailService implements UserDetailsService {
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
	private AccountRepo accountRepo;
	
	/** JwtUserDetailService의 첫번째 역할 - user_id로 Account를 찾아서 결과로 User객체를 반환해주는 역할 */
	@Override
    public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
        Account user = accountRepo.findByUserid(user_id);
        if(user==null) {
        	throw new UsernameNotFoundException("User not found with userid: " + user_id);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        if (user_id.equals("admin")) {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        }

        return new User(user.getUser_id(), user.getUser_password(), grantedAuthorities);
    }

	/** JwtUserDetailService의 두번째 역할 - 컨트롤러에서 넘어온 user_id와 user_password 값이 DB에 저장된 비밀번호와
	 * 일치하는지 검사한다. */
    public Account authenticateByEmailAndPassword(String user_id, String user_password) {
    	Account user = accountRepo.findByUserid(user_id);
        if(user==null) {
        	throw new UsernameNotFoundException("User not found with userid: " + user_id);
        }

        if(!passwordEncoder.matches(user_password, user.getUser_password())) {
            throw new BadCredentialsException("Password not matched");
        }

        return user;
    }

//	public UserDetails loadUserByUsername(String user_id) throws UsernameNotFoundException {
//		/** [ 로직 순서 ]
//		 * 1. DB에서 user_id를 통해 Account 정보를 가져온다.
//		 * 2. Set<GrantedAuthority>를 통해 권한을 부여한다.
//		 * 3. user_id가 'admin'인 경우엔 ADMIN 권한을 주고, 아닐경우엔 USER 권한을 줌.
//		 * 4. 마지막에는 Security UserDetails에 정의된 User인스턴스에 가져온  Account정보를 담아 반환해준다.
//		 * 5. 해당 반환값에 정상적인 UserDetails가 반환되면 인증이 성공하고, 정상적이지 않은  값이 반환되면 인증에 실패한다. */
//		
////		Account user = accountRepo.findByUserid(user_id);
////		if(user==null) {
////			throw new UsernameNotFoundException("User not found with userid: " + user_id);
////		}
////		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
////		if(user_id.equals("admin")) { // 관리자 계정이라면 
////			grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
////		}else {
////			grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
////			System.out.println(user.getUser_id()+" " +user.getUser_password()+ " "+ grantedAuthorities);
////		}
////        return new User(user.getUser_id(), user.getUser_password(), grantedAuthorities);
//		
//		/** [ 인증 과정 ] - 로그인 시(한번) DB 접근 */
//		Account user = accountRepo.findByUserid(user_id);
//		List<GrantedAuthority> roles = new ArrayList<>();
//
//        if (user == null) { //해당 유저가 없으면
//            throw new UsernameNotFoundException("User not found with user_id : " + user_id);
//        }
//        System.out.println("여기는 JwtUserDetail - loadUserByUsername / user의 ROLE은? "+ user.getRole());
//        if ((user.getRole()).equals("ROLE_ADMIN")) {
//        	System.out.println("이 유저는 ADMIN ");
//            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        } else {
//        	System.out.println("이 유저는 USER ");
//            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
//        }
//        return new User(user.getUser_id(), user.getUser_password(), roles);
//	}

}
