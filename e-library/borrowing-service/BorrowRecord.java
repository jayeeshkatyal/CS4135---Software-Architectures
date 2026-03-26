import java.time.LocalDate;

@Entity
public class BorrowRecord implements AggregateRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long borrowId;

    private Long userId;
    private Long bookId;
    private LocalDate borrowDate;
    private LocalDate dueDate; // could be embedded DueDate VO

    @Enumerated(EnumType.STRING)
    private BorrowStatus status;

    @Version
    private Long version;

    // domain methods
    public static BorrowRecord issue(Long userId, Long bookId, LocalDate borrowDate, LocalDate dueDate) {
        BorrowRecord record = new BorrowRecord();
        record.userId = userId;
        record.bookId = bookId;
        record.borrowDate = borrowDate;
        record.dueDate = dueDate;
        record.status = BorrowStatus.ACTIVE;
        return record;
    }

    public void returnBook() {
        this.status = BorrowStatus.RETURNED;
    }

    public void markOverdue() {
        this.status = BorrowStatus.OVERDUE;
    }
}