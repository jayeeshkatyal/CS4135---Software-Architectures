@Service
public class BorrowingEligibilityService {
    @Autowired
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "userService", fallbackMethod = "eligibilityFallback")
    public EligibilityResponse checkUserEligibility(Long userId) {
        ResponseEntity<EligibilityResponse> resp = restTemplate.getForEntity(
                "http://user-service/api/users/{userId}/eligibility", EligibilityResponse.class, userId);
        return resp.getBody();
    }

    @CircuitBreaker(name = "catalogueService", fallbackMethod = "availabilityFallback")
    public AvailabilityResponse checkBookAvailability(Long bookId) {
        ResponseEntity<AvailabilityResponse> resp = restTemplate.getForEntity(
                "http://catalogue-service/api/books/{bookId}/availability", AvailabilityResponse.class, bookId);
        return resp.getBody();
    }

    public BorrowResult eligibilityFallback(Long userId, Throwable t) {
        return new BorrowResult(false, "User service unavailable");
    }

    public BorrowResult availabilityFallback(Long bookId, Throwable t) {
        return new BorrowResult(false, "Catalogue service unavailable");
    }
}