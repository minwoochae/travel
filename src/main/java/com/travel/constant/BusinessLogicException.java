package com.travel.constant;

import lombok.Getter;

public class BusinessLogicException extends RuntimeException{
    @Getter
    private ExceptionCode exceptionCode;

	public BusinessLogicException(ExceptionCode exceptionCode) {
		
	}


}
