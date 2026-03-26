@Entity
public class Fine implements AggregateRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fineId;

    private Long userId;
    private Long borrowId;

    @Embedded
    private Money amount; // Value Object

    private LocalDate calculatedDate;

    @Enumerated(EnumType.STRING)
    private FineStatus status = FineStatus.UNPAID;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment; // Entity

    // domain methods
    public void pay(PaymentGatewayService gateway) {
        // delegate to gateway, which returns PaymentStatus
        PaymentStatus result = gateway.processPayment(amount);
        if (result == PaymentStatus.COMPLETED) {
            this.payment = new Payment(amount, LocalDateTime.now(), result);
            this.status = FineStatus.PAID;
        } else {
            throw new PaymentFailedException();
        }
    }

    public void waive() {
        this.status = FineStatus.WAIVED;
    }
}