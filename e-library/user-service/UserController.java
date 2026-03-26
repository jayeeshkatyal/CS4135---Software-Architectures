@RestController
@RequestMapping("/api/users")
public class UserController {
    @PutMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRole(@PathVariable Long userId, @RequestBody RoleUpdateRequest request) {
        UserAccount user = userRepository.findById(userId).orElseThrow();
        // invariants: e.g., can't demote last admin – we'd check.
        user.updateRole(request.getRole());
        userRepository.save(user);
        // publish RoleChanged event
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/eligibility")
    public ResponseEntity<EligibilityResponse> checkEligibility(@PathVariable Long userId) {
        // Called by Borrowing Service
        UserAccount user = userRepository.findById(userId).orElseThrow();
        // Borrowing Service will need active loan count – we don't store that here.
        // We'll just return role. Borrowing Service will track active loans.
        return ResponseEntity.ok(new EligibilityResponse(userId, 0, user.getRole()));
    }
}