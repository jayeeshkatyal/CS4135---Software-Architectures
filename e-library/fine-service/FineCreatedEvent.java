public class FineCreatedEvent {

    private final Long fineId;
    private final Long userId;
    private final Long borrowId;
    private final Money amount;
    private final LocalDate calculatedDate;

    public FineCreatedEvent(Fine fine) {
        this.fineId = fine.getFineId();
        this.userId = fine.getUserId();
        this.borrowId = fine.getBorrowId();
        this.amount = fine.getAmount();
        this.calculatedDate = fine.getCalculatedDate();
    }

    public Long getFineId() { return fineId; }
    public Long getUserId() { return userId; }
    public Long getBorrowId() { return borrowId; }
    public Money getAmount() { return amount; }
    public LocalDate getCalculatedDate() { return calculatedDate; }
}