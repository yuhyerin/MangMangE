package com.example.auth.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth.Domain.Account;
import com.example.auth.Domain.Token;
import com.example.auth.Repository.AccountRepository;
import com.example.auth.jwt.JwtTokenUtil;
import com.example.auth.service.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@CrossOrigin("*")
@RequestMapping // This means URL's start with /demo (after Application path)
@Api(value = "MAIN")
public class MainController {
    private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager am;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @GetMapping(path="/home")
    public void home() {
    	System.out.println("home");
    	return;
    }

    @Transactional
    @ApiOperation(value="유저삭제")
    @PostMapping(path="/admin/deleteuser")
    public void deleteUser (@RequestBody Map<String, String> m) {
        logger.info("delete user: " + m.get("username"));
        Long result = accountRepository.deleteByUsername(m.get("username"));
        logger.info("delete result: " + result);
    }

    @GetMapping(path="/admin/getusers")
    @ApiOperation(value="전체유저찾기")
    public Iterable<Account> getAllUsers() {
        return accountRepository.findAll();
    }

    @GetMapping(path="/user/normal")
    @ApiOperation("?")
    public ResponseEntity<?> onlyNormal() {
        return new ResponseEntity(HttpStatus.OK);
    }


    @PostMapping(path="/newuser/out")
    @ApiOperation("로그아웃")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> m) {
        String username = null;
        String accessToken = m.get("accessToken");
        try {
            username = jwtTokenUtil.getUsernameFromToken(accessToken);
        } catch (IllegalArgumentException e) {} catch (ExpiredJwtException e) { //expire됐을 때
            username = e.getClaims().getSubject();
            logger.info("username from expired access token: " + username);
        }

        try {
            if (redisTemplate.opsForValue().get(username) != null) {
                //delete refresh token
                redisTemplate.delete(username);
            }
        } catch (IllegalArgumentException e) {
            logger.warn("user does not exist");
        }

        //cache logout token for 10 minutes!
        logger.info(" logout ing : " + accessToken);
        redisTemplate.opsForValue().set(accessToken, true);
        redisTemplate.expire(accessToken, 10*6*1000, TimeUnit.MILLISECONDS);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path="/newuser/check")
    @ApiOperation("토큰체크")
    public Map<String, Object> checker(@RequestBody Map<String, String> m) {
        String username = null;
        Map<String, Object> map = new HashMap<>();
        try {
            username = jwtTokenUtil.getUsernameFromToken(m.get("accessToken"));
        } catch (IllegalArgumentException e) {
            logger.warn("Unable to get JWT Token");
        }
        catch (ExpiredJwtException e) {
        }

        if (username != null) {
            map.put("success", true);
            map.put("username", username);
        } else {
            map.put("success", false);
        }
        return map;
    }
    @ApiOperation(value="로그인", notes = "로그인해봅시다.")
    @PostMapping(path = "/newuser/login")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid input:…"),
            @ApiResponse(code = 401, message = "unAuthorized"),
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "sql error")
            })  
//    @ApiImplicitParams({
//        @ApiImplicitParam(name = "username", value="유저네임", dataType = "String", paramType="query"),
//        @ApiImplicitParam(name = "password", value="패스워드", dataType = "String", paramType="query")
//    })
//    @GetMapping(path="/newuser/login/{username}/{password}")
    public Map<String, Object> login(@RequestBody Map<String, String> m) throws Exception {
//    public Map<String, Object> login(@PathVariable String username, @PathVariable String password){
        final String username = m.get("username");
        System.out.println("username : "+username);
//        logger.info("test input username: " + username);
        try {
            am.authenticate(new UsernamePasswordAuthenticationToken(username, m.get("password")));
        } catch (Exception e){
        	System.out.println("authenticate 에러랍니다.");
            throw e;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(username);

        Token retok = new Token();
        retok.setUsername(username);
        retok.setRefreshToken(refreshToken);

        //generate Token and save in redis
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set(username, retok);

        logger.info("generated access token: " + accessToken);
        logger.info("generated refresh token: " + refreshToken);
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        return map;
    }


    @PostMapping(path="/newuser/add") // Map ONLY POST Requests
    @ApiOperation("회원가입")
    public Map<String, Object> addNewUser (@RequestBody Account account) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        String un = account.getUsername();
        Map<String, Object> map = new HashMap<>();
        System.out.println("회원가입요청 아이디: "+un + "비번: " + account.getPassword());
        if (accountRepository.findByUsername(un) == null) {
            account.setUsername(un);
            account.setEmail(account.getEmail());
            if (un.equals("admin")) {
                account.setRole("ROLE_ADMIN");
            } else {
                account.setRole("ROLE_USER");
            }

            account.setPassword(bcryptEncoder.encode(account.getPassword()));
            map.put("success", true);
            accountRepository.save(account);
            return map;
        } else {
            map.put("success", false);
            map.put("message", "duplicated username");
        }
        return map;
    }



    @PostMapping(path="/newuser/checkemail")
    @ApiOperation("이메일중복검사")
    public boolean checkEmail (@RequestBody Map<String, String> m) {
        System.out.println("이메일체크 요청 이메일: " + m.get("email"));
        if (accountRepository.findByEmail(m.get("email")) == null) return true;
        else return false;
    }

    @PostMapping(path="/newuser/refresh")
    @ApiOperation("refresh토큰 발행")
    public Map<String, Object>  requestForNewAccessToken(@RequestBody Map<String, String> m) {
        String accessToken = null;
        String refreshToken = null;
        String refreshTokenFromDb = null;
        String username = null;
        Map<String, Object> map = new HashMap<>();
        try {
            accessToken = m.get("accessToken");
            refreshToken = m.get("refreshToken");
            logger.info("access token in rnat: " + accessToken);
            try {
                username = jwtTokenUtil.getUsernameFromToken(accessToken);
            } catch (IllegalArgumentException e) {

            } catch (ExpiredJwtException e) { //expire됐을 때
                username = e.getClaims().getSubject();
                logger.info("username from expired access token: " + username);
            }

            if (refreshToken != null) { //refresh를 같이 보냈으면.
                try {
                    ValueOperations<String, Object> vop = redisTemplate.opsForValue();
                    Token result = (Token) vop.get(username);
                    refreshTokenFromDb = result.getRefreshToken();
                    logger.info("rtfrom db: " + refreshTokenFromDb);
                } catch (IllegalArgumentException e) {
                    logger.warn("illegal argument!!");
                }
                //둘이 일치하고 만료도 안됐으면 재발급 해주기.
                if (refreshToken.equals(refreshTokenFromDb) && !jwtTokenUtil.isTokenExpired(refreshToken)) {
                    final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    String newtok =  jwtTokenUtil.generateAccessToken(userDetails);
                    map.put("success", true);
                    map.put("accessToken", newtok);
                } else {
                    map.put("success", false);
                    map.put("msg", "refresh token is expired.");
                }
            } else { //refresh token이 없으면
                map.put("success", false);
                map.put("msg", "your refresh token does not exist.");
            }

        } catch (Exception e) {
            throw e;
        }
        logger.info("m: " + m);
        return map;
    }

}