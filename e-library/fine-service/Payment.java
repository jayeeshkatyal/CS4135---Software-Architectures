@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Embedded
    private Money amount;

    private LocalDateTime timestamp;

    private String gatewayReference;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    // JPA requires a no-arg constructor
    protected Payment() {}

    public Payment(Money amount, LocalDateTime timestamp, PaymentStatus status) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.status = status;
        this.gatewayReference = "SIM-" + System.currentTimeMillis();
    }

    public Long getPaymentId() { return paymentId; }
    public Money getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public PaymentStatus getStatus() { return status; }
    public String getGatewayReference() { return gatewayReference; }
}