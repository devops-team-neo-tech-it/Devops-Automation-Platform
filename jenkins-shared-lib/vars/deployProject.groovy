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

private def deployMavenProject(String NEXUS_URL, String GROUP_PATH, String SNAPSHOT_VERSION, String SSH_KEY, String REPOSITORY, 
            String PROJECT_NAME, String REMOTE_HOST, String REMOTE_USER, String TARGET_DIR
    ){
    try {
        sshagent([SSH_KEY]) {
            script {
                try {
                    // Construire l'URL de metadata
                    def metadataUrl = "${NEXUS_URL}/repository/${REPOSITORY}/${GROUP_PATH}/${SNAPSHOT_VERSION}/maven-metadata.xml"
                    def baseSnapshotUrl = "${NEXUS_URL}/repository/${REPOSITORY}/${GROUP_PATH}/${SNAPSHOT_VERSION}"

                    // Télécharger le fichier metadata
                    sh "curl -s -o metadata.xml ${metadataUrl}"
                    // Lire le contenu du fichier
                    def metadata = readFile('metadata.xml')

                    // Extraire directement la valeur du dernier snapshot (évite les erreurs de sérialisation)
                    def matcher = (metadata =~ /<value>(0\.0\.1-[^<]+)<\/value>/)
                    if (!matcher) {
                        error "❌ Unable to extract latest snapshot version from metadata.xml"
                    }
                    def latestSnapshot = matcher[0][1]

                    def artifactName = "${PROJECT_NAME}.jar"
                    def artifactUrl = "${baseSnapshotUrl}/${artifactName}"

                    echo "✅ Latest artifact: ${artifactUrl}"

                    // Déploiement sur le serveur distant
                    withCredentials([
                        string(credentialsId: 'env-vars', variable: 'ENV_VARS'),
                    ]) {
                        sh """
                            ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} \\
                            'set -e && \\
                                mkdir -p ${TARGET_DIR}/app && \\
                                echo "${ENV_VARS}" | sed "s/\\s\\+/\\n/g" > ${TARGET_DIR}/.env && \\
                                
                                echo "📦 Downloading artifact..." && \\
                                curl -f -L -o ${TARGET_DIR}/app/${artifactName} ${artifactUrl} && \\

                                echo "🛑 Stopping service..." && \\
                                sudo systemctl stop ${PROJECT_NAME} || true && \\

                                echo "🚀 Starting service..." && \\
                                sudo systemctl start ${PROJECT_NAME} && \\
                                sudo systemctl status ${PROJECT_NAME} --no-pager'
                        """
                    }
                } catch (err) {
                    error "❌ Error during artifact retrieval or preparation: ${err}"
                }
            }
        }
    } catch (err) {
        error("❌ SSH agent or pipeline failure: ${err}")
    }
}