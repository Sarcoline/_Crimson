package com.crimson.core.dto;

import lombok.Data;

import java.util.HashMap;

public @Data class ImageDTO {

    private HashMap<String, byte[]> pictures = new HashMap<>();

    @Override
    public String toString()
    {
        return String.format("ImageDTO[images_size%d]", pictures.size());
    }
}
