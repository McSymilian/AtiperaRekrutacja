package pl.ryder.githubapiconsumer.branch;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Commit(
        String sha,
        String url
) {}
