def call(String branch = 'main', String gitUrl = '', String credentialsId = '') {
    stage('Checkout') {
        steps.checkout([
            $class: 'GitSCM',
            branches: [[name: "*/${branch}"]],
            userRemoteConfigs: [[
                url: gitUrl,
                credentialsId: credentialsId
            ]]
        ])
    }
}
