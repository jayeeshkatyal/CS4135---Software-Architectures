@Entity
public class Fine implements AggregateRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fineId;

    private Long userId;
    private Long borrowId;

    @Embedded
    private Money amount;

    private LocalDate calculatedDate;

    @Enumerated(EnumType.STRING)
    private FineStatus status = FineStatus.UNPAID;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    // Required by JPA
    protected Fine() {}

    // Constructor called by FineEventListener
    public Fine(Long userId, Long borrowId, Money amount, LocalDate calculatedDate) {
        this.userId = userId;
        this.borrowId = borrowId;
        this.amount = amount;
        this.calculatedDate = calculatedDate;
        this.status = FineStatus.UNPAID;
    }

    public void pay(PaymentGatewayService gateway) {
        // Invariant: cannot pay twice
        if (this.status == FineStatus.PAID) {
            throw new IllegalStateException("Fine has already been paid.");
        }
        // Invariant: cannot pay a waived fine
        if (this.status == FineStatus.WAIVED) {
            throw new IllegalStateException("Fine has been waived and cannot be paid.");
        }
        PaymentStatus result = gateway.processPayment(amount);
        if (result == PaymentStatus.COMPLETED) {
            this.payment = new Payment(amount, LocalDateTime.now(), result);
            this.status = FineStatus.PAID;
        } else {
            throw new PaymentFailedException();
        }
    }

    public void waive() {
        if (this.status == FineStatus.PAID) {
            throw new IllegalStateException("Cannot waive a fine that has already been paid.");
        }
        this.status = FineStatus.WAIVED;
    }

    // Getters
    public Long getFineId() { return fineId; }
    public Long getUserId() { return userId; }
    public Long getBorrowId() { return borrowId; }
    public Money getAmount() { return amount; }
    public LocalDate getCalculatedDate() { return calculatedDate; }
    public FineStatus getStatus() { return status; }
    public Payment getPayment() { return payment; }
}