package com.sachith.book_me_server.model.dto;

public record ApiResponse<T>(boolean success, String message, T data) { }
