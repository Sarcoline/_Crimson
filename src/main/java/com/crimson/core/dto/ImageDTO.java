package com.crimson.core.dto;

import lombok.Data;

import java.util.HashMap;


public @Data class ImageDTO {

    private HashMap<String, byte[]> pictures = new HashMap<>();
}
