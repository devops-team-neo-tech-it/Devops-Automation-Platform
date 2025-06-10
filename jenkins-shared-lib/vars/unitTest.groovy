def call(String projectPath, String buildTool){
    try{
        dir(projectPath) {
            echo "🔍 Checking for project files in ${projectPath}"
            switch(buildTool) {
                case "maven":
                    unitTestMaven()
                    break
                default:
                    error("Unsupported build tool: ${buildTool}")
            }
        }
        
    }catch(Exception e){
        error("Unit Tests failed: ${e.message}")
    }
}

private def unitTestMaven() {
    
    if (!fileExists("./pom.xml")) {
        error("No pom.xml found in the specified project path.")
    }
    
    echo '🧪 Running Unit Tests with Maven'
    sh "mvn test"
    echo '✅ Unit Tests completed successfully'
}
