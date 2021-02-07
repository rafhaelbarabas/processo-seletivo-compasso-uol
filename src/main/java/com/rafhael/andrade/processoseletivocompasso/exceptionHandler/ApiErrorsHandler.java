package com.rafhael.andrade.processoseletivocompasso.exceptionHandler;

import java.util.Arrays;
import java.util.List;

public class ApiErrorsHandler {
    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    public ApiErrorsHandler(List<String> errors) {
        this.errors = errors;
    }

    public ApiErrorsHandler(String message){
        this.errors = Arrays.asList(message);
    }
}
