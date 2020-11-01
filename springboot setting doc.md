# springBoot maven

## mariaDB - springBoot 연동

1. pom.xml 에 dependency 추가

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
	<groupId>org.mariadb.jdbc</groupId>
	<artifactId>mariadb-java-client</artifactId>
</dependency>
```

2. application.properties 에 mariaDB 설정 추가

```
spring.datasource.driverClassName=org.mariadb.jdbc.Driver

spring.datasource.url=jdbc:mariadb://localhost:3306/db이름?autoReconnect=true&useUnicode=true&characterEncoding=utf8

spring.datasource.username=root
spring.datasource.password=패스워드
```

> 참고: https://www.leafcats.com/14



## redis - springBoot 연동

1. pom.xml에 dependency 추가

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

2. cmd창에서 docker에 redis 설치 및 실행

```
# redis 설치
docker run -p 6379:6379 --name redis_boot -d redis
# redis 실행
docker exec -i -t redis_boot redis-cli
```

> 참고: https://velog.io/@max9106/Spring-Boot-Redis

3. application.properties에 설정 추가

```
spring.redis.lettuce.pool.max-active=10
spring.redis.lettuce.pool.max-idle=10
spring.redis.lettuce.pool.min-idle=2
spring.redis.port=6379
spring.redis.host=127.0.0.1
```



## lombok - springBoot 연동

1. lombok.jar 다운로드

```
https://projectlombok.org/download
```

2.  sts와 eclipse 경로로 설치

```
[cmd]
cd download
java -jar lombok.jar

[download folder]
lombok.jar 실행
```

3. pom.xml에 dependency 추가

```
<dependency>
	<groupId>org.projectlombok</groupId>
	<artifactId>lombok</artifactId>
	<version>1.18.16</version>
	<scope>provided</scope>
</dependency>
```

> version은 설치된 lombok.jar의 버전



## springBoot swagger-ui

1. pom.xml에 dependency 추가

```
<!-- springfox-swagger2 -->
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger-ui</artifactId>
	<version>2.9.2</version>
</dependency>

<!-- springfox-swagger-ui -->
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger2</artifactId>
	<version>2.9.2</version>
</dependency>
```

2. SwaggerConfig class 생성

```
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private ApiInfo apiInfo(){
		return new ApiInfoBuilder()
			.title("title")
			.description("API description")
			.build();
	}
	
	@Bean
	public Docket commonApi(){
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName("apiName")
			.apiInfo(this.apiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("controller패키지"))
			.paths(PathSelectors.any())
			.build();
	}
}
```

> .paths(PathSelectors.ant("/api/**")) : controller패키지 내의 controller class에서 requestMapping("/api/")로 시작하는 class를 swagger로 만듬

> localhost:8080/swagger-ui.html



## jpa - springBoot

1. application.properties에 속성 추가

```
spring.jpa.hibernate.ddl-auto=update
```

> none : 아무것도 실행하지 않음(default)
>
> create-drop: 
>
> SessionFactory가 시작될 때 drop 및 create 실행 & SessionFactory가 종료될 때 drop
>
> (in-memory DB에서 default)
>
> create : SessionFactory가 시작될 때 database drop 후 생성된 ddl 실행
>
> update: 변경된 스키마 적용
>
> validate: 변경된 스키마가 있다면 변경점 출력 후 애플리케이션 종료

> 참고 : https://pravusid.kr/java/2018/10/10/spring-database-initialization.html



## jwt secret key 사용

1. application.properties에 속성 추가

```
jwt.secret=비밀키로 쓰고 싶은 단어
```

2. 사용

```
@Value("${jwt.secret}")
private String secret;
```



## spring security 참고

https://m.blog.naver.com/kimnx9006/220638156019

