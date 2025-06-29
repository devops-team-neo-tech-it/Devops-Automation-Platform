@Library('my-shared-lib') _


pipeline {
    agent any
    
    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'master', description: 'Git branch to checkout')
        string(name: 'GIT_URL', defaultValue: 'https://github.com/Abdelkoddouss-Ybnelhaj/e-wallet.git', description: 'scm repo url')
        string(name: 'CRENDANTAILS_ID',description: 'Jenkins credential ID for Git') 
        choice(name: 'BUILD_TOOL', choices: ['maven', 'gradle', '.net' , 'npm'], description: 'Select build tool')
        booleanParam(name: 'RUN_OWASP_DEPENDENCY_CHECK', defaultValue: true, description: 'Scan for dependency vulnerabilities?')
        string(name: 'PROJECT_PATH', defaultValue: './backend', description: 'Specify the maven project path')
        string(name: 'PROJECT_NAME', defaultValue: 'e-wallet', description: 'project name')
        booleanParam(name: 'RUN_UNIT_TESTS', defaultValue: true, description: 'Run unit tests?')
        booleanParam(name: 'INTEGRATE_STATIC_CODE_ANALYSIS', defaultValue: true, description: 'Integrate static code analysis ?')
        string(name: 'SONARQUBE_CONNECTION',defaultValue: 'sonarqube-server', description: 'sonarqube server connection')
        string(name: 'PROJECT_KEY',defaultValue: 'e-wallet-backend', description: 'project key')
    }
    
    environment {
        NVD_KEY = 'be18959a-0b20-4330-b685-0c3325f612f5'
        ODC = 'Default'
    }
    
    tools{
        maven 'Maven-3.9.8'
    }
    
    stages {
        stage('Checkout') {
            steps {
                script {
                    checkoutRepo(params.BRANCH_NAME, params.GIT_URL, params.CRENDANTAILS_ID)
                }
            }
        }
        
        stage('Dependency Check') {
            when {
                expression { params.RUN_OWASP_DEPENDENCY_CHECK }
            }
            steps {
                owaspDepCheck(env.ODC, params.PROJECT_NAME, params.PROJECT_PATH, env.NVD_KEY)
            }
            
            post {
                always {
                    owaspPubReport(params.PROJECT_NAME)
                }
            }
        }
        
        stage('Unit Tests'){
            when {
                expression { params.RUN_UNIT_TESTS }
            }
            steps{
                script {
                    unitTest(params.PROJECT_PATH, params.BUILD_TOOL)
                }
            }
        }
        
        stage('Static code analysis') {
            when {
                expression { return params.INTEGRATE_STATIC_CODE_ANALYSIS }
            }
            steps{
                sonarQube(params.SONARQUBE_CONNECTION , params.BUILD_TOOL, params.PROJECT_PATH, params.PROJECT_KEY)
            }
            post {
                success {
                    script {
                        sonarQualityGates()
                    }
                }
            }
        }
        
    }
    
    post {
        always {
            script {
                emailNotif()
                echo "Clearing workspace....."
                deleteDir()
            }
        }
    }
}
