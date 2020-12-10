# redis docker 실행



1. cmd 창에 redis docker 실행

```
docker run -p 6379:6379 --name redis_boot -d redis
```

2. 실행됐는 지 확인

```
docker exec -i -t redis_boot redis-cli
```

3. redis의 모든 key 조회

```
127.0.0.1:6379> keys *
```



# spring boot와 redis 연동

1. 의존성 추가

```
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-test</artifactId>
<scope>test</scope>
</dependency>

<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

> 깃플젝에서는 마지막 dependency만 추가하면 됨

