def call(String projectPath, String buildTool){
    try{
        dir(projectPath) {
            echo "🔍 Starting Integration Tests in ${projectPath} using ${buildTool}"
            
            switch(buildTool) {
                case "maven":
                    integrationTestMaven()
                    break
                default:
                    error("Unsupported build tool: ${buildTool}")
            }
        }
        
    }catch(Exception e){
        error("Integration Tests failed: ${e.message}")
    }
}

private def integrationTestMaven() {
    
    if (!fileExists("./pom.xml")) {
        error("No pom.xml found in the specified project path.")
    }
    
    echo '🧪 Running Integration Tests with Maven'
    sh "mvn failsafe:integration-test failsafe:verify"
    echo '✅ Integration Tests completed successfully'
}