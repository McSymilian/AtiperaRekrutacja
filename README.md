# Atipiera  Rekrutacja

### Recruitment assignment for Atipiera.

After starting the application, all you have to do is send a GET request to `localhost:8081/repos/{USERNAME}` 
with the appropriate variable in the query. 
As a response, the JSON will be returned in the format 
```
{
    "name": ${repositoryName},
    "owner_login":${ownerLogin},
    "branches":  [{
         "name": ${branchName}
         "sha": ${lastCommitSha}
    }]
}
```
or if the query contains a non-existent user 
```
{
    "status": ${responseCode}
    "message": ${whyHasItHappened}
}
```
