package com.test.abc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	
	@Value("${spring.redis.host}")
    private String redisHost;
 
    @Value("${spring.redis.port}")
    private int redisPort;
	
	@Bean
    public RedisConnectionFactory redisConnectionFactory() {
		System.out.println("RedisConfig");
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        System.out.println("redisHost : "+redisHost+"\t redisPort : "+redisPort);
		redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
        return lettuceConnectionFactory;
    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //객체를 json 형태로 깨지지 않고 받기 위한 직렬화 작업
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

}