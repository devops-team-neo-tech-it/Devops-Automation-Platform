def call(String COLLECTION_PATH) {
    echo "🔍 Starting API tests using Newman with collection: ${COLLECTION_PATH}"
    try {
        sh """
            newman run "${COLLECTION_PATH}" \
            -r html \
            --reporter-html-export "newman-report.html" 
        """
    } catch (err) {
        echo "❌ API tests failed: ${err}"
        throw err
    }
    echo "✅ API tests completed successfully using Newman."
}
