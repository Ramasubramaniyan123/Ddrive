package com.xcelevate.demo.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private Boolean active;
    private Long departmentId;
    private String departmentName;
}
