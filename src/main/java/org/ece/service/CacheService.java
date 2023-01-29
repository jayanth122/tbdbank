package org.ece.service;

import org.ece.dto.SessionData;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CacheService {
    private static final int TIME_TO_LIVE_FOR_SESSION_DATA_IN_MINUTES = 5;

    private final RedisTemplate<String, SessionData> redisTemplate;

    public CacheService(final RedisTemplate<String, SessionData> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void createSession(final String uniqueSessionId, final SessionData sessionData) {
        redisTemplate.opsForValue().set(uniqueSessionId, sessionData, TIME_TO_LIVE_FOR_SESSION_DATA_IN_MINUTES,
                TimeUnit.MINUTES);
    }

    public SessionData validateSession(final String uniqueSessionId) {
       return redisTemplate.hasKey(uniqueSessionId) ? redisTemplate.opsForValue().get(uniqueSessionId) : null;
    }

    public void killSession(final String uniqueSessionId) {
        redisTemplate.delete(uniqueSessionId);
    }
}
