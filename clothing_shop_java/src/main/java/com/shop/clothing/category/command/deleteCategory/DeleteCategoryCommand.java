package com.shop.clothing.category.command.deleteCategory;

import com.shop.clothing.common.Cqrs.IRequest;


public record DeleteCategoryCommand(int id) implements IRequest<Void> {



}
