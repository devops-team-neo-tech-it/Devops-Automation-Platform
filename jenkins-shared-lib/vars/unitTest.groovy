def call(String projectPath, String buildTool, def env){
    try{
        switch(buildTool) {
            case "maven":
                unitTestMaven(projectPath)
                break
            default:
                error("Unsupported build tool: ${buildTool}")
        }
    }catch(Exception e){
        env.FAILURE_REASON = 'unit_test_failed'
        error("Unit Tests failed: ${e.message}")
    }
}