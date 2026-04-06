@Component
public class FineEventListener {
    @Autowired
    private FineCalculationService fineCalculator;
    @Autowired
    private FineRepository fineRepo;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @EventListener
    @Transactional
    public void handleBookOverdue(BookOverdueEvent event) {
        Money amount = fineCalculator.computeFine(event.getDaysOverdue());
        Fine fine = new Fine(event.getUserId(), event.getBorrowId(), amount, LocalDate.now());
        fineRepo.save(fine);
        eventPublisher.publishEvent(new FineCreatedEvent(fine));
    }
}