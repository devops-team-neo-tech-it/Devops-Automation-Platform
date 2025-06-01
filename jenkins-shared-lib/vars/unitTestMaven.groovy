def call(String projectPath){
    stage('Maven Unit Tests'){
        steps{
            sh "cd ${projectPath} && mvn test"
        }
    }
}