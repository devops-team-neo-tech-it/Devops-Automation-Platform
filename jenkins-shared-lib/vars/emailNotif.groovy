def call(){

    def authorEmail = bat(script: "@git log -1 --pretty=format:\"%%ae\"", returnStdout: true).trim()
    // Optional: Sanitize if git sometimes wraps email in < > or quotes
    authorEmail = authorEmail.replaceAll(/[<>']/, "") 

    def status = currentBuild.currentResult
    def statusClass = status.toLowerCase()
    def statusIcon = status == 'SUCCESS' ? '✅' : (status == 'FAILURE' ? '❌' : '⚠️')
    def reasonMsg = FAILURE_REASON ? buildFailureMessage(FAILURE_REASON) : "Build completed with status: ${status}"

    def template = libraryResource('jenkins-email-template.html')
                    .replace('${BUILD_STATUS}', status)
                    .replace('${BUILD_STATUS_CLASS}', statusClass ?: 'N/A')
                    .replace('${STATUS_ICON}', statusIcon)
                    .replace('${reasonMsg}', reasonMsg)
                    .replace('${JOB_NAME}', env.JOB_NAME ?: 'N/A')
                    .replace('${BUILD_NUMBER}', env.BUILD_NUMBER ?: '0')
                    .replace('${GIT_BRANCH}', env.GIT_BRANCH ?: 'Development')
                    .replace('${BUILD_USER}', env.BUILD_USER ?: 'Auto Triggered')
                    .replace('${BUILD_DURATION}', currentBuild.durationString ?: 'N/A')
                    .replace('${BUILD_TIMESTAMP}', new Date().toString() ?: 'N/A')
                    .replace('${BUILD_URL}', env.BUILD_URL ?: 'N/A')
                    .replace('${JENKINS_URL}', env.JENKINS_URL ?: '#')
                    .replace('${JOB_URL}', env.JOB_URL ?: '#')
                
    if (authorEmail) {
        emailext(
            subject: "🔔 Jenkins Build ${status} - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
            body: template,
            to: authorEmail,
            from: 'Jenkins <noreply@gmail.com>',
            mimeType: 'text/html'
        )
    } else {
        echo "⚠️ Email de l’auteur non extrait — email non envoyé"
    }

}