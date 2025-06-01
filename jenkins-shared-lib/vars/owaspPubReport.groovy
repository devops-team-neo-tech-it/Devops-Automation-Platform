def call(String projectName) {
    stage('Publish Report') {
        publishHTML target: [
            allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: 'reports',
            reportFiles: "${projectName}-report.html",
            reportName: "OWASP Dependency-Check - ${projectName}"
        ]
    }
}
