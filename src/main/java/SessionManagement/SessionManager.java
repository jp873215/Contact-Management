package SessionManagement;

import java.util.concurrent.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAOs.userDAO;
import POJO.session;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;

public class SessionManager {
    private ConcurrentHashMap<String, LocalDateTime> sessionInteractions = new ConcurrentHashMap<>();
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final int EXPIRATION_TIME_MINUTES = 30;
    userDAO uDAO = new userDAO();
    session sObj = new session();
    public SessionManager() {
        scheduler.scheduleAtFixedRate(() -> {
			try {
				sendDataToDB(null, null);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}, 1, 1, TimeUnit.MINUTES);
    }

	public void updateInteraction(String sessionId) {
        sessionInteractions.put(sessionId, LocalDateTime.now());
    }
    
    public void sendDataToDB(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException {
        LocalDateTime now = LocalDateTime.now();
        Iterator<Map.Entry<String, LocalDateTime>> iterator = sessionInteractions.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, LocalDateTime> entry = iterator.next();
            String sessionId = entry.getKey();
            LocalDateTime lastInteraction = entry.getValue();

            // Calculate inactive time
            long inactiveMinutes = java.time.Duration.between(lastInteraction, now).toMinutes();
            sObj.setSessionId(sessionId);
            if (inactiveMinutes > EXPIRATION_TIME_MINUTES) {
            	boolean res = uDAO.clearCookie(sObj, request, response);
                if (res) {
                	System.out.println("Session cleared successfully at LocalDateTime.now(): " + sessionId);
                	iterator.remove(); 
                }
                else {
                	System.out.println("Failed to clear session from DB: " + sessionId);
                }
            } else {
                // Code to send sessionId and lastInteraction to the DB
            	
            	java.sql.Timestamp lastInteractionTimestamp = java.sql.Timestamp.valueOf(lastInteraction);
                sObj.setLastActiveTime(lastInteractionTimestamp.toLocalDateTime()); // Convert back if needed

            	
            	System.out.println("Before Updating the value of SID is: " + sObj.getSessionId());
            	boolean res = uDAO.updateLastInteractionTime(sObj);
            	System.out.println("Sending to DB: " + sessionId + " last interaction: " + lastInteraction);
            	if (res) {
            		System.out.println("updated successfully");
            	}
            	else {
            		System.out.println("updated failed");
            	}
            }
        }
    }


    public void shutdown() {
        scheduler.shutdown();
    }
    

}
