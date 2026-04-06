@Service
public class FineCalculationService {

    private static final double FINE_RATE_PER_DAY = 0.50;

    public Money computeFine(int daysOverdue) {
        if (daysOverdue <= 0) {
            throw new IllegalArgumentException("Days overdue must be greater than zero");
        }
        return Money.of(FINE_RATE_PER_DAY * daysOverdue);
    }
}