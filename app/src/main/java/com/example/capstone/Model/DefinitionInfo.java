package com.example.capstone.Model;

public class DefinitionInfo {
    private String DefinitionName;
    private String DefinitionDescription;
    private String DefinitionImageURL;

    public DefinitionInfo(){

    }

    public String getDefinitionImageURL() {
        return DefinitionImageURL;
    }

    public void setDefinitionImageURL(String definitionImageURL) {
        DefinitionImageURL = definitionImageURL;
    }

    public DefinitionInfo(String definitionName, String definitionDescription) {
        DefinitionName = definitionName;
        DefinitionDescription = definitionDescription;
    }

    public String getDefinitionDescription() {
        return DefinitionDescription;
    }

    public void setDefinitionDescription(String definitionDescription) {
        DefinitionDescription = definitionDescription;
    }

    public String getDefinitionName() {
        return DefinitionName;
    }

    public void setDefinitionName(String definitionName) {
        DefinitionName = definitionName;
    }
}
