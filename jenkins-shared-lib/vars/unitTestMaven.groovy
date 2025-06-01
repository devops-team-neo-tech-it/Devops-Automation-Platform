def call(String projectPath){
    sh "cd ${projectPath} && mvn test"
}