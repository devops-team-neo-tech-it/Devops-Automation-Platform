def call(String odc, String projectName, String projectPath, String nvdApiKey) {
    try {
        
        dependencyCheck odcInstallation: odc,
                        additionalArguments: "--scan ${projectPath} --format HTML --out ./reports --nvdApiKey ${nvdApiKey}"

        sh """
            mv ./reports/dependency-check-report.html ./reports/${projectName}-report.html
        """
    } catch (Exception e) {
        error("OWASP Dependency Check failed: ${e.message}")
    }
}
