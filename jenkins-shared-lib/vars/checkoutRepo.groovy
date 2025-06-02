def call(String branch, String gitUrl, String credentialsId) {
    try {
        echo "🔍 Checking out branch: ${branch} from ${gitUrl}"

        checkout([
            $class: 'GitSCM',
            branches: [[name: branch]],
            userRemoteConfigs: [[
                url: gitUrl,
                credentialsId: credentialsId
            ]]
        ])

        echo "✅ Successfully checked out branch: ${branch}"
    } catch (Exception e) {
        error("Checkout failed: ${e.message}")
    }
}
