package com.jvel.edify.controller.responses;

import com.jvel.edify.entity.Module;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModuleQueryMultipleResponse {
    private List<Module> modules;
}
