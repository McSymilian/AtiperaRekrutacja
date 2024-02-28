package pl.ryder.githubapiconsumer.usersrepositories;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ryder.githubapiconsumer.exception.ExceptionAnswer;
import pl.ryder.githubapiconsumer.exception.NoSuchUserException;
import pl.ryder.githubapiconsumer.usersrepositories.githubrepository.SingleRepositoryResponse;

import java.util.List;

@RestController
@RequestMapping("repos")
public class UserRepositories {
    private final UsersRepositoriesMapService usersRepositoriesMapService;

    public UserRepositories(UsersRepositoriesMapService usersRepositoriesMapService) {
        this.usersRepositoriesMapService = usersRepositoriesMapService;
    }

    @GetMapping("{user}")
    public List<SingleRepositoryResponse> getAllReposFromUser(@PathVariable String user) {
        return usersRepositoriesMapService.getUserRepositories(user);
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ExceptionAnswer badUser(NoSuchUserException exception) {
        return new ExceptionAnswer(exception.getStatus(), exception.getMessage());
    }
}
