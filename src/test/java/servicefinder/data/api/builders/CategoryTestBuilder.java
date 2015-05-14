package servicefinder.data.api.builders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import servicefinder.data.model.Category;

public class CategoryTestBuilder {
	public final static String DEFAULT_NAME = "test";
	
	public static Category buildTestCategory() {
		return buildTestCategory(DEFAULT_NAME);
	}
	
	public static Category buildTestCategory(String name) {
		return new Category(name, Arrays.asList(new String[] {
				"test service 1", "test service 2" }));
	}

	public static List<Category> buildTestCategoryList(String... names) {
		List<Category> categoryList = new ArrayList<Category>(names.length);

		for (String name : names) {
			categoryList.add(buildTestCategory(name));
		}

		return categoryList;
	}
}
