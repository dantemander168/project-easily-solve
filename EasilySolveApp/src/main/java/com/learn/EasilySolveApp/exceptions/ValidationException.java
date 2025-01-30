package com.learn.EasilySolveApp.exceptions;

import com.learn.EasilySolveApp.constants.ExceptionCodes;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
  private final ExceptionCodes code;

  public ValidationException(ExceptionCodes code) {
    super(code.getMessage());
    this.code = code;
  }
}
