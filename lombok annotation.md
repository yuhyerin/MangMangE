### lombok annotation

#### @NonNull

: @Setter와 함께 쓰며 null값이 들어오면 <u>NullPointerException</u> 처리

```
lombok.nonNull.exceptionType = IllegalArgumentException
```

> default가 NullPointerException이고 위와 같은 형태로 다른 Exception으로 바꿀 수 있다.

#### @Cleanup

: 어노테이션의 scope가 끝날 때 AutoCloseable 인터페이스 내의 close()를 호출

#### @Getter / @Setter

: Getter, Setter 메소드 생성

#### @EqualsAndHashCode

: equals()와 hashCode() 메소드 생성

#### @NoArgsConstructor

: (파라미터가 없는) 기본 생성자 생성

#### @RequiredArgsConstructor

: 초기화 되지 않은 final 필드와 @NonNull 어노테이션이 붙은 필드에 대한 생성자 생성

#### @AllArgsConstructor

: 모든 필드를 포함하는 생성자 생성

#### @ToString

: 모든 필드를 포함하는 toString() 메소드 생성

#### @Data

: @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor를 모두 포함하는 어노테이션

#### @Value

: 불변 클래스를 생성할 때 사용

#### @Builder

: builder 패턴으로 객체를 생성할 수 있음

>  ex) 객체명.builder(). param1(value).param2(value). ... .build();

#### @SneakyThrows(~~Exception.class)

: 파라미터로 입력받은 예외 발생 시 e.printStackTrace() 메소드 호출과 동일

= 예외 발생 시 Throwable 타입으로 반환

#### @Synchronized("Object")

: 파라미터로 입력받은 Object 단위로 lock을 건다.

#### @With



#### @Getter(lazy=true)

: 동기화를 이용해서 최초 한번만 getter를 호출

> 참고 : http://wonwoo.ml/index.php/post/1607

#### @Log

: log 변수 생성 -> log 출력을 도움

- 로그 종류 : @Log4j @Log4j2 @JBossLog @Slf4j @CommonsLog

> 참고 : 
>
> https://smallgiant.tistory.com/78
>
> **https://velog.io/@jayjay28/%EC%9E%90%EC%A3%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EB%8A%94-Lombok-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98**
>
> https://partnerjun.tistory.com/55
>
> http://wonwoo.ml/index.php/post/1616
>
> docs : 
>
> https://projectlombok.org/features/all

