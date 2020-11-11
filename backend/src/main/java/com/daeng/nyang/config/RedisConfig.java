package com.daeng.nyang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.daeng.nyang.dto.TotToken;

@Configuration
@EnableWebMvc
public class RedisConfig extends WebMvcConfigurerAdapter {

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory();
		return lettuceConnectionFactory;
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());

		// 객체를 json 형태로 깨지지 않고 받기 위한 직렬화 작업
		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(TotToken.class));
		return redisTemplate;
	}

	@Bean
	public MultipartResolver multipartResolver() {
       StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
//		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//		commonsMultipartResolver.setMaxUploadSize(Integer.MAX_VALUE);
//		commonsMultipartResolver.setDefaultEncoding("UTF-8");
       return multipartResolver;
//		return commonsMultipartResolver;
	}
}
