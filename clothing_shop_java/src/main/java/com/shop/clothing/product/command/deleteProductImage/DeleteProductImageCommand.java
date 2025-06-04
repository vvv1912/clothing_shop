package com.shop.clothing.product.command.deleteProductImage;

import com.shop.clothing.common.Cqrs.IRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public record DeleteProductImageCommand(
        @NotEmpty(message = "Url is required")
                @Size(min = 1, max = 255, message = "Url must be between 1 and 255 characters")
        String url) implements IRequest<Void> {
}
