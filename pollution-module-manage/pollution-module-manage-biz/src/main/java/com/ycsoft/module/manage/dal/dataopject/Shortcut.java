package com.ycsoft.module.manage.dal.dataopject;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shortcut {
    @Schema(description = "快捷档位名称", example = "shortcutName")
    private String shortcutName;

    @Schema(description = "档位值", example = "shortcutValue")
    private Integer shortcutValue;
}
