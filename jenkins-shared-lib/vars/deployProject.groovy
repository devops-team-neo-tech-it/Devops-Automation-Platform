def call(String BUILD_TOOL, String NEXUS_URL, String GROUP_PATH, String SNAPSHOT_VERSION, String SSH_KEY, String REPOSITORY, 
            String PROJECT_NAME, String REMOTE_HOST, String REMOTE_USER, String TARGET_DIR
){
    try{
        switch(BUILD_TOOL) {
            case 'maven':
                // Maven specific deployment steps
                deployMavenProject(NEXUS_URL, GROUP_PATH, SNAPSHOT_VERSION, SSH_KEY, REPOSITORY, PROJECT_NAME, REMOTE_HOST, REMOTE_USER, TARGET_DIR)
                break
            case 'gradle':
                // Gradle specific deployment steps
                break
            default:
                error "Unsupported build tool: ${BUILD_TOOL}"
        }
    } catch (Exception e) {
        error("Failed to deploy project: ${e.message}")
    }
    echo "✅ Deployment completed successfully for ${PROJECT_NAME} on ${REMOTE_HOST}"

}