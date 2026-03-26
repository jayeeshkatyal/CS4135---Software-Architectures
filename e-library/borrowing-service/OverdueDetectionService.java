import java.time.LocalDate;
import java.util.List;

@Component
public class OverdueDetectionService {
    @Scheduled(cron = "0 0 0 * * ?") // every day at midnight
    @Transactional
    public void detectOverdue() {
        List<BorrowRecord> active = borrowRepo.findByStatus(BorrowStatus.ACTIVE);
        LocalDate today = LocalDate.now();
        for (BorrowRecord record : active) {
            if (record.getDueDate().isBefore(today)) {
                record.markOverdue();
                borrowRepo.save(record);
                // Raise BookOverdue event
                eventPublisher.publishEvent(new BookOverdueEvent(
                    record.getBorrowId(), record.getUserId(), record.getBookId(),
                    (int) ChronoUnit.DAYS.between(record.getDueDate(), today)
                ));
            }
        }
    }
}