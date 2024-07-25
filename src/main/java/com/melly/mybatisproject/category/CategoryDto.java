package com.melly.mybatisproject.category;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto implements ICategory {
    private Long id;
    private String name;

}
