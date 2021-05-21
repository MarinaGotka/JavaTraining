package by.issoft.domain.categoryFactory;

import by.issoft.domain.Category;
import by.issoft.domain.categories.FoodCategory;

public class FoodCategoryCreator implements CategoryCreator{
    @Override
    public Category createCategory() {
        return new FoodCategory();
    }
}
