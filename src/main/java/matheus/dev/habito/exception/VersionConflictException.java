package matheus.dev.habito.exception;

public class VersionConflictException extends RuntimeException {
    public VersionConflictException(String message) { super(message); }
}
