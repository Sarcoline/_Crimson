package com.crimson.core.dto;

import lombok.Data;

import java.util.HashMap;

@Data
public class ImageDTO {

    private HashMap<String, byte[]> pictures = new HashMap<>();
}
