@Entity
@Table(name = "user_account")
public class UserAccount implements AggregateRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Embedded
    private Email email;

    @Embedded
    private PasswordHash passwordHash;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Embedded
    private UserProfile profile;

    private boolean locked = false;

    // Domain events (use Spring's ApplicationEventPublisher in service layer)
    // We'll publish events in the service, not inside the entity.

    protected UserAccount() {}

    public UserAccount(Email email, String rawPassword, UserRole role, UserProfile profile) {
        this.email = email;
        this.passwordHash = new PasswordHash(rawPassword);
        this.role = role;
        this.profile = profile;
    }

    public void updateRole(UserRole newRole) {
        // invariants: only ADMIN can change roles, etc. Checked in service.
        this.role = newRole;
    }

    public void updateProfile(UserProfile newProfile) {
        this.profile = newProfile;
    }

    public void lock() { this.locked = true; }
    public void unlock() { this.locked = false; }

    public boolean isLocked() { return locked; }

    // getters
}