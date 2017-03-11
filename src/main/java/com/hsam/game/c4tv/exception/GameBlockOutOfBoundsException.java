package com.hsam.game.c4tv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ResponseStatus(UNPROCESSABLE_ENTITY)
public class GameBlockOutOfBoundsException extends RuntimeException {
}
