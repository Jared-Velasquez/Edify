package com.jvel.edify.controller.requests.user_requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NameRequest {
    private String firstName;
    private String lastName;
}
