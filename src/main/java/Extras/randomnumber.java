package Extras;
import java.security.SecureRandom;
public class randomnumber {
    private static final SecureRandom random = new SecureRandom();

    public static String generateUUIDv4() {

    	long mostSignificantBits = random.nextLong();
        long leastSignificantBits = random.nextLong();

        mostSignificantBits &= ~(0xF000L);  
        mostSignificantBits |= 0x4000L;     
        leastSignificantBits &= ~(0xC000000000000000L);  
        leastSignificantBits |= 0x8000000000000000L;     
      
        return String.format("%08x-%04x-%04x-%04x-%012x",
                (mostSignificantBits >> 32) & 0xFFFFFFFFL,
                (mostSignificantBits >> 16) & 0xFFFFL,
                mostSignificantBits & 0xFFFFL,
                (leastSignificantBits >> 48) & 0xFFFFL,
                leastSignificantBits & 0xFFFFFFFFFFFFL);
    }
}
