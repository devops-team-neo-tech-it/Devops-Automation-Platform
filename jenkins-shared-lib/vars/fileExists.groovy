def fileExists(String filePath) {
     if (!fileExists(filePath)) {
        error("No ${filePath} found in the specified project path")
    }
}