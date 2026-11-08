
package filtres;

import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import java.util.Date;

public final class JwtSupport {

    private static final SecretKey KEY = Jwts.SIG.HS512.key().build();

    private JwtSupport() {
    }

    public static String issueToken(String username) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + 15L * 60L * 1000L))
                .signWith(KEY, Jwts.SIG.HS512)
                .compact();
    }

    public static void validateToken(String token) {
        Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token);
    }
}
