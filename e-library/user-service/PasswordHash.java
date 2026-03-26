@Embeddable
public class PasswordHash implements ValueObject {
    private String hash;

    protected PasswordHash() {}

    public PasswordHash(String rawPassword) {
        this.hash = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public boolean verify(String rawPassword) {
        return BCrypt.checkpw(rawPassword, hash);
    }

    // getter
}