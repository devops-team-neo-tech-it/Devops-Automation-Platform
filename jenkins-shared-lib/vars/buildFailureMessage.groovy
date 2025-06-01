
def call(String reason, String buildURL) {
    switch (reason) {
        case 'unit_test_failed':
            return "Jenkins build failed: **Unit tests failed**. Please check the logs: ${buildURL}"
        case 'static_scanning_failed':
            return "Jenkins build failed: **Static code scanning failed**. Review the issues here: ${buildURL}"
        case 'quality_gate':
            return "Jenkins build failed: **SonarQube Quality Gate failed**. Review code issues here: ${buildURL}"
        case 'build_failed':
            return "Jenkins build failed: **Build step failed**. Please fix build errors: ${buildURL}"
        case 'owasp_dependency_check_failed':
            return "Jenkins build failed: **OWASP Dependency Check failed**. Please check the logs: ${buildURL}"
        default:
            return "Jenkins build failed due to an unknown error. Details: ${buildURL}"
    }
}