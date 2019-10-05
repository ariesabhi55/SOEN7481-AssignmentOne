package com.concordia.soen7481;

public class ResultFormat {

	private String className;
    private String ruleName;
    private String detectedSnippet;
    private int startLine;
    private int endLine;
    private String fileName;


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getDetectedSnippet() {
        return detectedSnippet;
    }

    public void setDetectedSnippet(String detectedSnippet) {
        this.detectedSnippet = detectedSnippet;
    }
    public int getStartLine() {
        return startLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getEndLine() {
        return endLine;
    }

    public void setEndLine(int endLine) {
        this.endLine = endLine;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
