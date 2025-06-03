def call(String projectPath){
    echo '🧪 Running Unit Tests with Maven'
    sh "mvn -o test"
    echo '✅ Unit Tests completed successfully'
}