package com.example.demo.model.exceptions;

public class NotFoundCategoryMoneyThresholdForThisUser extends RuntimeException {
    public NotFoundCategoryMoneyThresholdForThisUser() {
        super("There is not a category money threshold with that category for this user");
    }
}
