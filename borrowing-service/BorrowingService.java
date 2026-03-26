@Service
public class BorrowingService {
    private final BorrowingEligibilityService eligibilityService;
    private final BorrowRecordRepository borrowRepo;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public BorrowRecord borrowBook(Long userId, Long bookId) {
        // 1. Check user eligibility (including active loan count)
        EligibilityResponse user = eligibilityService.checkUserEligibility(userId);
        if (!user.isEligible()) {
            throw new BusinessException("User not eligible");
        }

        // 2. Check book availability
        AvailabilityResponse book = eligibilityService.checkBookAvailability(bookId);
        if (!book.isAvailable()) {
            throw new BusinessException("Book not available");
        }

        // 3. Create borrow record
        BorrowRecord record = BorrowRecord.issue(userId, bookId, LocalDate.now(), LocalDate.now().plusDays(14));
        borrowRepo.save(record);

        // 4. Update book status in Catalogue (REST call)
        updateBookAvailability(bookId, true); // use RestTemplate

        // 5. Publish event
        eventPublisher.publishEvent(new BookIssuedEvent(record.getBorrowId(), userId, bookId, record.getDueDate()));

        return record;
    }

    private void updateBookAvailability(Long bookId, boolean borrowed) {
        restTemplate.put("http://catalogue-service/api/books/{bookId}/availability",
                new AvailabilityUpdateRequest(borrowed), Map.of("bookId", bookId));
    }
}