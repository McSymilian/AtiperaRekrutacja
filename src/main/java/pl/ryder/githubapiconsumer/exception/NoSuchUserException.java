package pl.ryder.githubapiconsumer.exception;

import lombok.Getter;

@Getter
public class NoSuchUserException extends RuntimeException {
    private final Integer status;

    public NoSuchUserException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
