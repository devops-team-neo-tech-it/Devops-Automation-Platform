def call() {
    stage('SonarQube Quality Gate') {
        timeout(time: 10, unit: 'MINUTES') {
            def qualityGate = waitForQualityGate()
            echo "SonarQube Quality Gate status: ${qualityGate.status}"
            if (qualityGate.status != 'OK') {
                error "Pipeline aborted due to SonarQube Quality Gate failure"
            }
        }
    }
}
