def call(String DIRECTORY,String FILE_NAME, String REPORT_NAME) {
    try {
        echo "📂 Publishing file: ${FILE_NAME} from directory: ${DIRECTORY}"
        if (!fileExists("${DIRECTORY}/${FILE_NAME}")) {
            error("No ${FILE_NAME} found in the specified directory: ${DIRECTORY}")
        }
        
        publishHTML target: [
            allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: DIRECTORY,
            reportFiles: FILE_NAME,
            reportName: REPORT_NAME
        ]
    } catch (Exception e) {
        error("File publishing failed: ${e.message}")
    }
}