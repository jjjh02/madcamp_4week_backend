package madcamp.week4.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class InviteCodeService {

    private final RedisTemplate<String, String> stringRedisTemplate;

    private static final String INVITE_PREFIX = "invite:";

    @PostConstruct // Spring이 Bean을 생성한 직후에 자동으로 실행되는 초기화 메서드
    public void testRedisConnection() {
        stringRedisTemplate.opsForValue().set("ping", "pong");
        String result = stringRedisTemplate.opsForValue().get("ping");
        System.out.println("Redis 연결 테스트 결과: " + result);
    }

    // 초대코드 생성 (10분 TTL)
    public String generateAndStoreInviteCode(Long organizationId) {
        String inviteCode = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        String key = INVITE_PREFIX + inviteCode;
        stringRedisTemplate.opsForValue().set(key, String.valueOf(organizationId), 10, TimeUnit.MINUTES);
        return inviteCode;
    }

    // 초대코드로 조직 ID 조회
    public Long getOrganizationIdByInviteCode(String inviteCode) {
        String key = INVITE_PREFIX + inviteCode;
        String organizationId = stringRedisTemplate.opsForValue().get(key);
        return organizationId != null ? Long.parseLong(organizationId) : null;
    }

    // 초대코드 사용 후 삭제
    public void removeInviteCode(String inviteCode) {
        String key = INVITE_PREFIX + inviteCode;
        stringRedisTemplate.delete(key);
    }
}
