public class FinePaidEvent {

    private final Long fineId;
    private final Long userId;
    private final Long paymentId;
    private final Money paidAmount;
    private final LocalDateTime timestamp;

    public FinePaidEvent(Fine fine) {
        this.fineId = fine.getFineId();
        this.userId = fine.getUserId();
        this.paymentId = fine.getPayment().getPaymentId();
        this.paidAmount = fine.getPayment().getAmount();
        this.timestamp = fine.getPayment().getTimestamp();
    }

    public Long getFineId() { return fineId; }
    public Long getUserId() { return userId; }
    public Long getPaymentId() { return paymentId; }
    public Money getPaidAmount() { return paidAmount; }
    public LocalDateTime getTimestamp() { return timestamp; }
}