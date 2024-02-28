package pl.ryder.githubapiconsumer.usersrepositories.githubrepository;

import pl.ryder.githubapiconsumer.usersrepositories.githubrepository.branch.BranchResponse;

import java.util.List;

public record SingleRepositoryResponse(
        String name,
        String owner_login,
        List<BranchResponse> branches
) {}
