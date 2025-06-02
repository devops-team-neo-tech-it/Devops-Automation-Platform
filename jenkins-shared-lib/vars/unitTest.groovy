def call(String projectPath, String buildTool){
    try{
        switch(buildTool) {
            case "maven":
                UnitTestMaven(projectPath, buildTool)
            break
        }
    }catch(Exception e){
        FAILURE_REASON = 'unit_test_failed'
        error("Unit Tests failed: ${e.message}")
    }
}