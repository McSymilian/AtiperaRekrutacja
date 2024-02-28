package pl.ryder.githubapiconsumer.usersrepositories.githubrepository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Owner(
        String login
) {}
