def call(String sonarQubeConnection, String buildTool, String projectPath, String projectKey){
    try{
        dir(projectPath) {
            echo "🔍 Checking for project files in ${projectPath}"
            switch(buildTool) {
                case "maven":
                    sonarScannerMaven(sonarQubeConnection, projectKey)
                    break
                default:
                    error("Unsupported build tool: ${buildTool}")
            }
        }
    } catch (Exception e) {
        error("SonarQube analysis failed: ${e.message}")
    }
}

private def sonarScannerMaven(String sonarQubeConnection, String projectKey) {
    if (!fileExists("./pom.xml")) {
        error("No pom.xml found in the specified project path")
    }

    echo "🔍 Running SonarQube analysis with Maven"
    withSonarQubeEnv(sonarQubeConnection) {
        sh """
            mvn clean verify sonar:sonar \
                -Dsonar.projectKey=${projectKey} \
                -Dsonar.projectName=${projectKey}
        """
    }
    
    echo "✅ SonarQube analysis completed successfully"    
}