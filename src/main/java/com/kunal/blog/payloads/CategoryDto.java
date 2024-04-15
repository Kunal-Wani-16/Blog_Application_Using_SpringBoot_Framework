package com.kunal.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

	private Integer categoryId;
	
	@NotBlank
	@Size(min = 2, message = "Size must be more than 1")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 6, message = "Size must be more than 9")
	private String categoryDescription;
}
