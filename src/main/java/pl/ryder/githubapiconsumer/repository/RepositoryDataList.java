package pl.ryder.githubapiconsumer.repository;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryDataList extends ArrayList<SingleRepositoryData> {
}
