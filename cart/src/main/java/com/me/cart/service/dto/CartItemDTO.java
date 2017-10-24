package com.me.cart.service.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the CartItem entity.
 */
public class CartItemDTO implements Serializable {

    @NotNull
    @Min(1)
    private int quantity;

    @NotNull
    private ProductDTO product;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CartItemDTO cartItemDTO = (CartItemDTO) o;
        if(cartItemDTO == null || getProduct() == null || getQuantity() <= 0) {
            return false;
        }
        return Objects.equals(getProduct(), product);
    }

    @Override
    public String toString() {
        return "CartItemDTO {" +
            "quantity='" + getQuantity() + "'" +
            ", product='" + getProduct() + "'" +
            "}";
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductDTO getProduct() {
        return this.product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}
