@Service
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final UserAccountRepository userRepository;

    public AuthenticationService(JwtUtil jwtUtil, UserAccountRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public String generateJWT(UserAccount user) {
        return jwtUtil.generateToken(user.getUserId(), user.getRole().name());
    }

    public Optional<Long> validateJWT(String token) {
        return jwtUtil.validateToken(token);
    }
}