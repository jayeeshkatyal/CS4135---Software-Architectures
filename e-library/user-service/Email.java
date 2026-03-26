@Embeddable
public class Email implements ValueObject {
    @Column(unique = true, nullable = false)
    private String address;

    protected Email() {}

    public Email(String address) {
        if (!isValid(address)) throw new IllegalArgumentException("Invalid email");
        this.address = address.toLowerCase();
    }

    private boolean isValid(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    // getter
}