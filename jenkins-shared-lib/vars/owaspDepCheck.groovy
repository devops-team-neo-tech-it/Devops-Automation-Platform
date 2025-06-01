def call(String projectName , String projectPath , String nvdApiKey){
    stage('OWASP Dependency Check'){
        steps{
            script{
                try{
                    sh """
                        /var/jenkins_home/dependency-check/bin/dependency-check.sh \\
                        --project "${projectName}" \\
                        --scan "${projectPath}" \\
                        --format "HTML" \\
                        --out ./reports \\
                        --nvdApiKey "${nvdApiKey}"

                        mv ./reports/dependency-check-report.html ./reports/${projectName}-report.html
                        """
                }catch(Exception e){
                    FAILURE_REASON = 'owasp_dependency_check_failed'
                    error("OWASP Dependency Check failed: ${e.message}")
                }
            }
        }
    }
}