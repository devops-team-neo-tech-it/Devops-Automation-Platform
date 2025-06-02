def call(String FAILURE_REASON){
    stage('SonarQube Quality Gates') {
        def qualityGate = waitForQualityGate()
        echo "SonarQube Status: ${qualityGate.status}"

        if (qualityGate.status != 'OK') {
            FAILURE_REASON = 'quality_gate'
            error "Quality Gate failed"
        }
    }
}