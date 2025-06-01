def call(String sonarQubeConnection, String buildTool, String projectPath, String projectKey){
    try{
        switch(buildTool){
            case "maven":
                SonarScannerMaven(projectPath, sonarQubeConnection, projectKey)
                break;
            default:
                error "Unknown build tool: ${buildTool}"

        }
    } catch (Exception e) {
        env.FAILURE_REASON = 'sonarqube_analysis_failed'
        error("SonarQube analysis failed: ${e.message}")
    }
}