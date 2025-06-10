def call(String BUILD_TOOL, String PROJECT_PATH, String NEXUS_URL, String REPOSITORY) {
    
    try {
        dir(PROJECT_PATH) {
            echo "🔍 Checking for project files in ${PROJECT_PATH}"
            switch(BUILD_TOOL) {
                case "maven":
                    pushMavenToNexus(NEXUS_URL, REPOSITORY)
                    break
                default:
                    error("Unsupported build tool: ${BUILD_TOOL}")
            }
        }
        
        echo "✅ Successfully pushed to Nexus Repository ${NEXUS_URL}"
    } catch (Exception e) {
        error("Failed to push to Nexus: ${e.message}")
    }
}

private def pushMavenToNexus(String NEXUS_URL, String REPOSITORY) {

    if (!fileExists("./pom.xml")) {
        error("No pom.xml found in the specified project path")
    }
    
    sh """
        mvn clean deploy \
         -DaltDeploymentRepository=nexus::default::${NEXUS_URL}/repository/${REPOSITORY}/
    """
}
