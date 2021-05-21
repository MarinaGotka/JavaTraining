package by.issoft.domain.categoryFactory;

import by.issoft.domain.Category;
import by.issoft.domain.categories.BookCategory;

public class BookCategoryCreator implements CategoryCreator{
    @Override
    public Category createCategory() {
        return new BookCategory();
    }
}
