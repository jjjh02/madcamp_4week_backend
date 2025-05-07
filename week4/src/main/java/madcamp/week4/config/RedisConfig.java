package madcamp.week4.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@RequiredArgsConstructor
@Configuration
public class RedisConfig {

    // Redis 서버와의 연결을 생성하는 Spring 기본 인터페이스
    private final RedisConnectionFactory connectionFactory;

    // redisTemplate 커스텀
    // SpringBoot는 기본적으로 RedisTemplate<Object, Object> + JdkSerializationRedisSerializer로 사용
    // 모든 객체를 저장할 수 있도록 Object 타입, JDK 기본 직렬화 방식 (Serializable) 을 통해 바이트 형태로 저장
    // 직렬화된 바이트라서 CLI나 RedisInsight 같은 툴에서 봐도 해석이 안됨
    @Bean
    public RedisTemplate<String, String> redisTemplate() {

        // RedisTemplate<K, V>는 Redis에서 Key-Value 저장소로 데이터를 다룰 수 있는 핵심 도구
        // 여기선 String 타입의 Key와 Value를 저장하기 위해 RedisTemplate<String, String>으로 선언하고 커스터마이징
        RedisTemplate<String, String> template = new RedisTemplate<>();

        // 주입받은 RedisConnectionFactory로 연결 설정
        template.setConnectionFactory(connectionFactory);

        // Redis는 바이트 배열로 데이터를 저장 -> 객체를 직렬화/역직렬화하는 방식이 중요
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }

}