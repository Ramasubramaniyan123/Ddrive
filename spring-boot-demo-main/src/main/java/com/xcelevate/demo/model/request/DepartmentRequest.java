package com.xcelevate.demo.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentRequest {
    @NotBlank(message = "Department name is required")
    private String name;

    @NotBlank(message = "Department description is required")
    private String description;

    private Boolean active;
}
