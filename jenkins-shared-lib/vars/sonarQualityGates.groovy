def call() {
    def qualityGate = waitForQualityGate()
    echo "SonarQube Status: ${qualityGate.status}"

    if (qualityGate.status != 'OK') {
        error "Quality Gate failed"
    }
}
