@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserAccountRepository userRepository;
    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(new Email(request.getEmail())).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        UserAccount user = new UserAccount(
                new Email(request.getEmail()),
                request.getPassword(),
                UserRole.STUDENT, // default
                new UserProfile(request.getName(), request.getContactInfo(), null)
        );
        userRepository.save(user);
        // publish domain event (e.g., ApplicationEventPublisher)
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<UserAccount> opt = userRepository.findByEmail(new Email(request.getEmail()));
        if (opt.isEmpty() || !opt.get().getPasswordHash().verify(request.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        UserAccount user = opt.get();
        if (user.isLocked()) {
            return ResponseEntity.status(403).body("Account locked");
        }
        String token = authService.generateJWT(user);
        return ResponseEntity.ok(new JwtResponse(token, user.getUserId(), user.getRole()));
    }
}