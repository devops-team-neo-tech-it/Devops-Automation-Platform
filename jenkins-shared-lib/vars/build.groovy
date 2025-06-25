def call(String projectPath, String buildTool){
    try{
        dir(projectPath) {
            echo "🔍 Starting build process in ${projectPath} using ${buildTool}"
            
            switch(buildTool) {
                case "maven":
                    mavenBuild()
                    break
                default:
                    error("Unsupported build tool: ${buildTool}")
            }
        }
        
    }catch(Exception e){
        error("Unit Tests failed: ${e.message}")
    }
}

private def mavenBuild() {

    if (!fileExists("./pom.xml")) {
        error("No pom.xml found in the specified project path.")
    }

    echo '🔧 Building with Maven'
    sh "mvn clean package -DskipTests"
    echo '✅ Maven build completed successfully'
}
