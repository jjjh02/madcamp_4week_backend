package madcamp.week4.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

/**
 * implements Serializable interface 사용 이유:
 * Redis는 내부적으로 객체를 저장할 때 JdkSerializationRedisSerializer/Jackson2JsonRedisSerializer 사용
 * JdkSerializationRedisSerializer는 Serializable 인터페이스가 필요
 * Key: refresh_token:1
 * Value: {userId: 1, token: "...", issuedAt: ...}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RedisHash("refresh_token") // @RedisHash("prefix") -> Redis 키 이름을 prefix:{id} 형식으로 저장
public class RefreshToken implements Serializable {

    private Long userId;
    @Id
    private String token;
    // 처음 로그인할 때 한 번만 저장하고 이후엔 갱신 X
    private Long issuedAt;
    @TimeToLive
    private Long ttl;

}
