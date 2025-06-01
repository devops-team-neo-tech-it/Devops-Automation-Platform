def call(String projectPath, String sonarQubeConnection, String projectKey){
    stage('SonarQube Analysis') {
        steps {
            script {
                try {
                    withSonarQubeEnv(sonarQubeConnection) {
                        sh "cd ${projectPath} && mvn sonar:sonar -Dsonar.projectKey=${projectKey} -Dsonar.projectName=${projectKey}"
                    }
                } catch (Exception e) {
                    FAILURE_REASON = 'static_scanning_failed'
                    error("Static code scanning failed: ${e.message}")
                }
            }
        }
    }
}