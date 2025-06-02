def call(String projectPath, String buildTool){
    try{
        switch(buildTool) {
            case "maven":
                unitTestMaven(projectPath)
                break
            default:
                error("Unsupported build tool: ${buildTool}")
        }
    }catch(Exception e){
        FAILURE_REASON = 'unit_test_failed'
        error("Unit Tests failed: ${e.message}")
    }
}