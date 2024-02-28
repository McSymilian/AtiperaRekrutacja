package pl.ryder.githubapiconsumer.usersrepositories;


import org.springframework.stereotype.Service;
import pl.ryder.githubapiconsumer.usersrepositories.githubrepository.branch.BranchResponse;
import pl.ryder.githubapiconsumer.github.GitHubService;
import pl.ryder.githubapiconsumer.usersrepositories.githubrepository.SingleRepositoryResponse;

import java.util.List;

@Service
public class UsersRepositoriesMapService {
    private final GitHubService gitHubService;

    public UsersRepositoriesMapService(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    public List<SingleRepositoryResponse> getUserRepositories(String user) {
        var userRepos = gitHubService.getRepositories(user);

        return userRepos.stream()
                .filter(it -> !it.fork())
                .map(it -> {
                    var branches = gitHubService.getBranchesOf(user, it.name())
                            .stream()
                            .map(branch -> new BranchResponse(branch.name(), branch.commit().sha()))
                            .toList();

                    return new SingleRepositoryResponse(it.name(), it.owner().login(), branches);
                })
                .toList();
    }
}
