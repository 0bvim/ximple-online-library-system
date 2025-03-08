package com.ximple.library.dto;

import java.util.UUID;

public record ReviewUpdateDTO(UUID id, int rating, String comment) {}
