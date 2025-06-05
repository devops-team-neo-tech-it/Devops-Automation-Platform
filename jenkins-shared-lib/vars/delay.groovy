def call(String appUrl){
    def maxRetries = 30
    def waitTime = 5 
    def success = false
        
    echo "⏳ Waiting for application to be up at ${appUrl}..."
        
    for (int i = 1; i <= maxRetries; i++) {
        def statusCode = ''
        try {
            statusCode = sh(
                script: "curl --connect-timeout 5 -s -o /dev/null -w \"%{http_code}\" ${appUrl}",
                returnStdout: true
            ).trim()
        } catch (Exception e) {
            statusCode = '000'
        }
        
        if (statusCode == "200") {
            echo "✅ Application is up and returned HTTP 200"
            success = true
            break
        } else {
            echo "🔁 Attempt ${i}/${maxRetries} - App not ready (status: ${statusCode}), waiting ${waitTime}s..."
            sleep(waitTime)
        }
    }

    if (!success) {
        error("❌ Application did not return HTTP 200 after ${maxRetries * waitTime} seconds.")
    }
}