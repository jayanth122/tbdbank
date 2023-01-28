package org.ece.service;

import org.ece.configuration.DataSouceConfig;
import org.ece.dto.SessionData;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class SessionService {

    private DataSouceConfig dataSouceConfig;


    public SessionService(final DataSouceConfig dataSouceConfig) {
        this.dataSouceConfig = dataSouceConfig;
    }


    public boolean validateSession(final String sessionId) {
        return dataSouceConfig.getSession().containsKey(sessionId);
    }

    public String maintainUserSession(final String sessionId) {
        Map<String, SessionData> sessionMap = dataSouceConfig.getSession();
        final String updatedSessionId = generateUUID();

        sessionMap.put(generateUUID(), sessionMap.get(sessionId));
        sessionMap.remove(sessionId);
        return updatedSessionId;
    }

    public String createSession(final SessionData sessionData) {
        final String sessionId = generateUUID();
        dataSouceConfig.getSession().put(sessionId, sessionData);
        return sessionId;
    }


    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

}
