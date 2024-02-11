package com.carlosarroyoam.user.service.constants;

public class AppMessages {

	public static final String STANDARD_ILLEGAL_ACCESS_EXCEPTION_MESSAGE_UTILITY_CLASS = "Illegal access to utility class";

	public static final String USER_NOT_FOUND_EXCEPTION = "User not found";
	public static final String EMAIL_ALREADY_EXISTS_EXCEPTION = "Email already exists";
	public static final String USERNAME_ALREADY_EXISTS_EXCEPTION = "Username already exists";

	private AppMessages() {
		throw new IllegalAccessError(STANDARD_ILLEGAL_ACCESS_EXCEPTION_MESSAGE_UTILITY_CLASS);
	}

}
