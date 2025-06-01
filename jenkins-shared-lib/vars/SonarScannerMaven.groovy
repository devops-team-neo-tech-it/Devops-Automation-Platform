def call(String projectPath, String sonarQubeConnection, String projectKey){
    withSonarQubeEnv(sonarQubeConnection) {
        sh "cd ${projectPath} && mvn clean verify sonar:sonar -Dsonar.projectKey=${projectKey} -Dsonar.projectName=${projectKey}"
    }
}