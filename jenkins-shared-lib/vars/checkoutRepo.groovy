def call(String branch, String gitUrl, String credentialsId) {
    checkout([
        $class: 'GitSCM',
        branches: [[name: "*/${branch}"]],
        userRemoteConfigs: [[
            url: gitUrl,
            credentialsId: credentialsId
        ]]
    ])
}
