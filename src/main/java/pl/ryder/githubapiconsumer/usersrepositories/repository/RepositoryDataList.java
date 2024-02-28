package pl.ryder.githubapiconsumer.usersrepositories.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryDataList extends ArrayList<SingleRepositoryData> {
}
