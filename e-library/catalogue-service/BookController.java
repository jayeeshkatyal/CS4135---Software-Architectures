@RestController
@RequestMapping("/api/books")
public class BookController {
    @GetMapping("/{bookId}/availability")
    public AvailabilityResponse getAvailability(@PathVariable Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        return new AvailabilityResponse(bookId, book.getAvailabilityStatus() == AvailabilityStatus.AVAILABLE);
    }

    @PutMapping("/{bookId}/availability")
    @PreAuthorize("hasRole('SYSTEM')") // only Borrowing Service should call
    public void updateAvailability(@PathVariable Long bookId, @RequestBody AvailabilityUpdateRequest request) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        if (request.isBorrowed()) {
            book.markBorrowed();
        } else {
            book.markReturned();
        }
        bookRepository.save(book);
    }
}