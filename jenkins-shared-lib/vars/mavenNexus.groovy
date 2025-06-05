def pushMavenToNexus(String PROJECT_PATH, String NEXUS_URL, String REPOSITORY) {

    echo "🚀 Pushing to Nexus Repository ${NEXUS_URL}"
    if (!fileExists("${PROJECT_PATH}/pom.xml")) {
        error("No pom.xml found in the specified project path: ${PROJECT_PATH}")
    }
    
    dir(PROJECT_PATH) {
        sh """
              mvn clean deploy -DaltDeploymentRepository=nexus::default::${NEXUS_URL}/repository/${REPOSITORY}/
        """
    }
    echo "✅ Successfully pushed to Nexus Repository ${NEXUS_URL}"
}
