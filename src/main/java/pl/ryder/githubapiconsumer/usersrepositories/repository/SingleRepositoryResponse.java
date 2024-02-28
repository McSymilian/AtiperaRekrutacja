package pl.ryder.githubapiconsumer.usersrepositories.repository;

import pl.ryder.githubapiconsumer.usersrepositories.repository.branch.BranchResponse;

import java.util.List;

public record SingleRepositoryResponse(
        String name,
        String owner_login,
        List<BranchResponse> branches
) {}
