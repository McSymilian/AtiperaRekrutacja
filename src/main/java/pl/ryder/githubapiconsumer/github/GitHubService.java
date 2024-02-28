package pl.ryder.githubapiconsumer.github;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import pl.ryder.githubapiconsumer.usersrepositories.repository.branch.Branch;
import pl.ryder.githubapiconsumer.exception.ExceptionResponse;
import pl.ryder.githubapiconsumer.exception.NoSuchUserException;
import pl.ryder.githubapiconsumer.usersrepositories.repository.SingleRepositoryData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GitHubService {

    private final RestClient restClient;

    public GitHubService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<SingleRepositoryData> getRepositories(String user) {
        Map<String, String> params = new HashMap<>();
        params.put("USERNAME", user);
        params.put("TYPE", "all");

        var response = restClient.get()
                .uri("https://api.github.com/users/{USERNAME}/repos?type={TYPE}", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

        var bodilessEntity = response.toBodilessEntity();

        if (bodilessEntity.getStatusCode().isError())
            throw new NoSuchUserException(
                    response.body(ExceptionResponse.class).message(),
                    bodilessEntity.getStatusCode().value());

        return List.of(response.body(SingleRepositoryData[].class));
    }

    public List<Branch> getBranchesOf(String user, String repository) {
        Map<String, String> params = new HashMap<>();
        params.put("USERNAME", user);
        params.put("REPOSITORY", repository);

        var response = restClient.get().uri("https://api.github.com/repos/{USERNAME}/{REPOSITORY}/branches", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

        return List.of(response.body(Branch[].class));
    }

}
