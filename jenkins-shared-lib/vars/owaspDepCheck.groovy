def call(String projectName, String projectPath, String nvdApiKey) {
    try {
        sh """
            dependency-check.sh \\
                --project "${projectName}" \\
                --scan "${projectPath}" \\
                --format "HTML" \\
                --out ./reports \\
                --nvdApiKey "${nvdApiKey}" && \
            mv ./reports/dependency-check-report.html ./reports/${projectName}-report.html
        """
    } catch (Exception e) {
        env.FAILURE_REASON = 'owasp_dependency_check_failed'
        error("OWASP Dependency Check failed: ${e.message}")
    }
}
