@Entity
public class Book implements AggregateRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String title;
    private String author;

    @Embedded
    private ISBN isbn;

    @Embedded
    private CoverImageUrl coverImageUrl;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus = AvailabilityStatus.AVAILABLE;

    @ManyToOne
    private Category category;

    @Version
    private Long version; // optimistic lock

    // domain methods
    public void markBorrowed() {
        if (availabilityStatus != AvailabilityStatus.AVAILABLE) {
            throw new IllegalStateException("Book not available");
        }
        this.availabilityStatus = AvailabilityStatus.BORROWED;
    }

    public void markReturned() {
        this.availabilityStatus = AvailabilityStatus.AVAILABLE;
    }

    // ...
}