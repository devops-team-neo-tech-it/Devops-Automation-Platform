def call(){
    stage('SonarQube Quality Gates') {
        steps {
            script {
                try {
                    timeout(time: 10, unit: 'MINUTES') {
                        waitForQualityGate abortPipeline: true
                    }
                } catch (Exception e) {
                    FAILURE_REASON = 'sonarqube_quality_gate_failed'
                    error("SonarQube quality gate failed: ${e.message}")
                }
            }
        }
    }
}