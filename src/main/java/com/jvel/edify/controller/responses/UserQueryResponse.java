package com.jvel.edify.controller.responses;

import com.jvel.edify.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryResponse {
    private User user;
}

