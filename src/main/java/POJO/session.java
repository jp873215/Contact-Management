package POJO;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class session {
    private String sessionId;           
    private int uid;
    private LocalDateTime lastActiveTime;
    private int timeoutPeriod;
    private LocalDateTime createdTime;
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public LocalDateTime getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(LocalDateTime lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }
    
    public void setCreationTime(LocalDateTime createdTime) {
    	this.createdTime = createdTime;
    }
    
    public LocalDateTime getCreationTime() {
    	return createdTime;
    }

    public int getTimeoutPeriod() {
        return timeoutPeriod;
    }

    public void setTimeoutPeriod(int timeoutPeriod) {
        this.timeoutPeriod = timeoutPeriod;
    }
}
