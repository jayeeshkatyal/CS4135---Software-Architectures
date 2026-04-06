import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {

    // Find all fines for a specific user
    List<Fine> findByUserId(Long userId);

    // Find all fines with a specific status
    List<Fine> findByStatus(FineStatus status);

    // Find all unpaid fines for a specific user
    List<Fine> findByUserIdAndStatus(Long userId, FineStatus status);

    default List<Fine> findAllUnpaidByUser(Long userId) {
        return findByUserIdAndStatus(userId, FineStatus.UNPAID);
    }
}