def call(String projectPath) {
    stage('Build Maven') {
        steps {
            script {
                echo '🔧 Building with Maven'
                sh "cd ${projectPath} && mvn clean install -DskipTests"
            }
        }
    }
}


