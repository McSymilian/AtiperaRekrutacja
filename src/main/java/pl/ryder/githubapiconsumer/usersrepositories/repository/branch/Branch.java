package pl.ryder.githubapiconsumer.usersrepositories.repository.branch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Branch (
        String name,
        Commit commit
) {}
