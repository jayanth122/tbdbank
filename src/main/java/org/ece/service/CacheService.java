package org.ece.service;

import org.apache.commons.lang3.StringUtils;
import org.ece.dto.AccessType;
import org.ece.dto.SessionData;
import org.ece.util.SecurityUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class CacheService {
    private static final int TIME_TO_LIVE_FOR_SESSION_DATA_IN_MINUTES = 5;

    private final RedisTemplate<String, SessionData> redisTemplate;
    private static final String SESSION_DATA_PREFIX = "TBD651:";

    public CacheService(final RedisTemplate<String, SessionData> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void createSession(final String uniqueSessionId, final SessionData sessionData) {
        redisTemplate.opsForValue().set(SESSION_DATA_PREFIX + uniqueSessionId, sessionData,
                TIME_TO_LIVE_FOR_SESSION_DATA_IN_MINUTES,
                TimeUnit.MINUTES);
    }

    public SessionData validateSession(final String uniqueSessionId) {
        return redisTemplate.hasKey(SESSION_DATA_PREFIX + uniqueSessionId)
                ? redisTemplate.opsForValue().get(SESSION_DATA_PREFIX + uniqueSessionId) : null;
    }

    public void killSession(final String uniqueSessionId) {
        redisTemplate.delete(SESSION_DATA_PREFIX + uniqueSessionId);
    }

    public boolean doesUserSessionExist(final AccessType accessType, final String userId) {
        Set<String> keys = redisTemplate.keys(SESSION_DATA_PREFIX + "*");
        return keys.stream().map(key -> redisTemplate.opsForValue().get(key))
                .filter(sessionData -> sessionData.getAccessType().equals(accessType))
                .map(sessionData -> StringUtils.equals(sessionData.getUserId(), userId))
                .findFirst().isPresent();
    }

    public String killAndCreateSession(final String sessionId) {
        SessionData sessionData = validateSession(sessionId);
        killSession(sessionId);
        String newSessionId = SecurityUtils.generateSessionUUID();
        createSession(newSessionId, sessionData);
        return newSessionId;
    }
}
