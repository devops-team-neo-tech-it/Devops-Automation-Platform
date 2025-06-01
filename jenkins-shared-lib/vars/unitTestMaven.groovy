def call(String projectPath){
    try {
        echo '🧪 Running Unit Tests with Maven'
        sh "cd ${projectPath} && mvn test"
        echo '✅ Unit Tests completed successfully'
    } catch (Exception e) {
        env.FAILURE_REASON = 'unit_tests_failed'
        error("Unit tests failed: ${e.message}")
    }
}