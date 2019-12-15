package com.hasan.PollApp.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

public class Operation<T> {
    private String statusMessage;
    private Boolean wasSuccessful;
    private HttpStatus httpStatus;
    private T operationObject;

    public Operation() {
    }

    public Operation(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Operation(String statusMessage, Boolean wasSuccessful, T operationObject) {
        this.statusMessage = statusMessage;
        this.wasSuccessful = wasSuccessful;
        this.operationObject = operationObject;
    }

    public Operation(OperationStatus operationStatus) {
        setOperation(operationStatus);
        this.operationObject = null;
    }

    public Operation(OperationStatus operationStatus, T operationObject) {
        setOperation(operationStatus);
        this.operationObject = operationObject;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Boolean getWasSuccessful() {
        return wasSuccessful;
    }

    public void setWasSuccessful(Boolean wasSuccessful) {
        this.wasSuccessful = wasSuccessful;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public T getOperationObject() {
        return operationObject;
    }

    public void setOperationObject(T operationObject) {
        this.operationObject = operationObject;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "statusMessage='" + statusMessage + '\'' +
                ", wasSuccessful=" + wasSuccessful +
                ", httpStatus=" + httpStatus +
                ", operationObject=" + operationObject +
                '}';
    }

    public static ResponseEntity<?> getOperationResult(Operation operation){
        if(operation.getWasSuccessful()){
            return new ResponseEntity<>(operation, operation.getHttpStatus());
        } else{
            return new ResponseEntity<>(operation, operation.getHttpStatus());
        }
    }

    private void setOperation(OperationStatus operationStatus){
        switch (operationStatus){
            case COMPANY_NOT_FOUND:
                statusMessage = "Company(s) not found!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case COMPANY_FOUND:
                statusMessage = "Company(s) found!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case COMPANY_UPDATED:
                statusMessage = "Company updated!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case COMPANY_SAVED:
                statusMessage = "Company saved!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case COMPANY_DELETED:
                statusMessage = "Company deleted!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case COMPANY_EXISTS:
                statusMessage = "Company exists!";
                wasSuccessful = false;
                httpStatus = HttpStatus.CONFLICT;
                break;
            case COMPANY_USERS_FOUND:
                statusMessage = "Company user(s) found!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case USER_NOT_FOUND:
                statusMessage = "User(s) not found!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case USER_FOUND:
                statusMessage = "User(s) found!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case USER_NOT_ADDED:
                statusMessage = "User not added!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case USER_ADDED:
                statusMessage = "User added!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case USER_DELETED:
                statusMessage = "User deleted!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case TITLE_NOT_FOUND:
                statusMessage = "Title(s) not found!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case TITLE_FOUND:
                statusMessage = "Title(s) found!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case TITLE_ADDED:
                statusMessage = "Title added!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case TITLE_NOT_DELETED:
                statusMessage = "Title not deleted!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case TITLE_DELETED:
                statusMessage = "Title deleted!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case TITLE_EXIST:
                statusMessage = "User title exist!";
                wasSuccessful = false;
                httpStatus = HttpStatus.CONFLICT;
                break;
            case USER_TITLE_ADDED:
                statusMessage = "User title added!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case USER_TITLE_NOT_DELETED:
                statusMessage = "User title not deleted!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case USER_TITLE_DELETED:
                statusMessage = "User title deleted!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case POLL_NOT_FOUND:
                statusMessage = "poll(s) not found!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case POLL_FOUND:
                statusMessage = "poll(s) found!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case POLL_ADDED:
                statusMessage = "poll added!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case POLL_UPDATED:
                statusMessage = "Poll updated!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case POLL_DELETED:
                statusMessage = "Poll deleted!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case OPTION_NOT_FOUND:
                statusMessage = "poll option(s) not found!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case NOT_VOTED:
                statusMessage = "Vote failed!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case VOTED:
                statusMessage = "Vote success!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case ROLE_NOT_FOUND:
                statusMessage = "Role(s) not found!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case ROLE_ADDED:
                statusMessage = "Role added!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case ROLE_NOT_DELETED:
                statusMessage = "Role not deleted!";
                wasSuccessful = false;
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
            case ROLE_DELETED:
                statusMessage = "Role deleted!";
                wasSuccessful = true;
                httpStatus = HttpStatus.OK;
                break;
            case ROLE_EXIST:
                statusMessage = "Role exists!";
                wasSuccessful = false;
                httpStatus = HttpStatus.CONFLICT;
                break;
        }
    }
}
