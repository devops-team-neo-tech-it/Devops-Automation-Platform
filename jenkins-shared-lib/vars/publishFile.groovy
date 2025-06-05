def call(String FILE_NAME){
    publishHTML(target: [
        reportDir: '.', 
        reportFiles: FILE_NAME,
        reportName: 'Newman API Test Report',
        alwaysLinkToLastBuild: true,
        keepAll: true
    ])
}