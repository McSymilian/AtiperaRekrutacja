package pl.ryder.githubapiconsumer.repository;

import pl.ryder.githubapiconsumer.branch.BranchResponse;

import java.util.List;

public record SingleRepositoryResponse(
        String name,
        String owner_login,
        List<BranchResponse> branches
) {}
