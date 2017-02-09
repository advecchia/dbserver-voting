package com.dbserver.voting.util;

public class VotingErrorType {
	private String errorMessage;

    public VotingErrorType(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
