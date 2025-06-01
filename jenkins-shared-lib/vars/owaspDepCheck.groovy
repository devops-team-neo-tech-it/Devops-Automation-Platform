def call(String odc, String projectName, String projectPath, String nvdApiKey) {
    try {
        dependencyCheck odcInstallation: odc,
                        additionalArguments: "--scan ${projectPath} --format HTML --out ./reports --nvdApiKey ${nvdApiKey}"

        sh """
            mkdir -p reports
            mv dependency-check-report.html reports/${projectName}-report.html
        """
    } catch (Exception e) {
        env.FAILURE_REASON = 'owasp_dependency_check_failed'
        error("OWASP Dependency Check failed: ${e.message}")
    }
}
