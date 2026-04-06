@Service
public class PaymentGatewayService {

    public PaymentStatus processPayment(Money amount) {
        if (amount == null || amount.getAmount().doubleValue() <= 0) {
            return PaymentStatus.FAILED;
        }
        // Simulated gateway — always returns COMPLETED in this implementation.
        // In production this would call a real payment provider (anticorruption layer).
        return PaymentStatus.COMPLETED;
    }
}