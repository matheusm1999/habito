package matheus.dev.habito.controller;

import matheus.dev.habito.dto.HabitRequest;
import matheus.dev.habito.dto.HabitResponse;
import matheus.dev.habito.service.HabitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
public class HabitController {
    private final HabitService service;

    public HabitController(HabitService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody HabitRequest req, Authentication auth) {
        try {
            if (auth == null || auth.getName() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Unauthorized"));
            }
            HabitResponse res = service.createHabit(req, auth.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Erro ao criar hábito"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody matheus.dev.habito.dto.HabitRequest req, Authentication auth) {
        try {
            if (auth == null || auth.getName() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Unauthorized"));
            }
            HabitResponse res = service.updateHabit(id, req, auth.getName());
            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (java.util.NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Forbidden"));
        } catch (matheus.dev.habito.exception.VersionConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
        } catch (jakarta.persistence.OptimisticLockException | org.springframework.orm.ObjectOptimisticLockingFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Versão conflitante, recarregue e tente novamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("Erro ao atualizar hábito"));
        }
    }

    @GetMapping
    public ResponseEntity<?> list(Authentication auth) {
        if (auth == null || auth.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Unauthorized"));
        }
        List<HabitResponse> list = service.listByUser(auth.getName());
        return ResponseEntity.ok(list);
    }

    static class ErrorResponse {
        public String message;
        public ErrorResponse(String message) { this.message = message; }
    }
}