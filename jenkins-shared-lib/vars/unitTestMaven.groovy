def call(String projectPath){
    echo '🧪 Running Unit Tests with Maven'
    sh "cd ${projectPath} && mvn test"
    echo '✅ Unit Tests completed successfully'
}