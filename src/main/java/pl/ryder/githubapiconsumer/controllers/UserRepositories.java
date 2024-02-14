package pl.ryder.githubapiconsumer.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.ryder.githubapiconsumer.branch.BranchResponse;
import pl.ryder.githubapiconsumer.exception.ExceptionAnswer;
import pl.ryder.githubapiconsumer.exception.ExceptionResponse;
import pl.ryder.githubapiconsumer.exception.NoSuchUserException;
import pl.ryder.githubapiconsumer.repository.SingleRepositoryData;
import pl.ryder.githubapiconsumer.repository.SingleRepositoryResponse;
import pl.ryder.githubapiconsumer.branch.BranchList;
import pl.ryder.githubapiconsumer.repository.RepositoryDataList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("repos")
public class UserRepositories {

    public static final String URL = "https://api.github.com/users/{USERNAME}/repos?type={type}";
    public final RestTemplate restTemplate;

    public UserRepositories(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("{user}")
    public List<?> getAllReposFromUser(@PathVariable String user) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            Map<String, String> params = new HashMap<>();
            params.put("USERNAME", user);
            params.put("type", "all");


            return restTemplate.exchange(URL, HttpMethod.GET, entity, RepositoryDataList.class, params)
                    .getBody()
                    .stream()
                    .filter(it -> !it.fork())
                    .map(it -> new SingleRepositoryData(it.name(), it.owner(), it.branches_url().replace("{/branch}", ""), it.fork()))
                    .map(it -> {
                        List<BranchResponse> branches = restTemplate.exchange(it.branches_url(), HttpMethod.GET, entity, BranchList.class)
                                .getBody()
                                .stream()
                                .map(branch -> new BranchResponse(branch.name(), branch.commit().sha()))
                                .toList();

                        return new SingleRepositoryResponse(it.name(), it.owner().login(), branches);
                    })
                    .toList();

        } catch (HttpClientErrorException exception) {
            throw new NoSuchUserException(
                    Objects.requireNonNullElse(
                            exception.getResponseBodyAs(ExceptionResponse.class).message(),
                            ""
                    ),
                    exception.getStatusCode().value()
            );
        }

    }

    @ExceptionHandler(NoSuchUserException.class)
    public ExceptionAnswer badUser(NoSuchUserException exception) {
        return new ExceptionAnswer(exception.getStatus(), exception.getMessage());
    }
}
