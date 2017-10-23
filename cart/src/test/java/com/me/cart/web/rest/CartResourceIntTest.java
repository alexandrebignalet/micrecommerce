package com.me.cart.web.rest;

import com.me.cart.CartApp;
import com.me.cart.domain.Cart;
import com.me.cart.domain.CartItem;
import com.me.cart.domain.Product;
import com.me.cart.service.CartService;
import com.me.cart.service.dto.CartItemDTO;
import com.me.cart.service.mapper.CartItemMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the LogsResource REST controller.
 *
 * @see LogsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CartApp.class)
public class CartResourceIntTest {

    private MockMvc restCartMockMvc;

    private static final Product DEFAULT_PRODUCT = new Product().id("1").name("AAAA")
        .price(BigDecimal.TEN).fileContentType("image/png").image(TestUtil.createByteArray(1, "0"));

    private static final int DEFAULT_QUANTITY = 1;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemMapper cartMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        CartResource cartResource = new CartResource(cartService);
        this.restCartMockMvc = MockMvcBuilders
            .standaloneSetup(cartResource)
            .build();
    }

    @Test
    public void addCartItemToUserCartInSession() throws Exception {
        CartItem cartItem = new CartItem(DEFAULT_QUANTITY, DEFAULT_PRODUCT);
        int cartSizeBeforeAdding = cartService.getCurrentUserCart().getCartItems().size();

        CartItemDTO cartItemDTO = cartMapper.toDto(cartItem);

        restCartMockMvc.perform(post("/api/cart")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartItemDTO)))
            .andExpect(status().isCreated());

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.addAll(cartService.getCurrentUserCart().getCartItems());
        assertThat(cartItemList).hasSize(cartSizeBeforeAdding + 1);
        CartItem testCartItem = cartItemList.get(cartItemList.size() - 1);
        assertThat(testCartItem.getProduct()).isEqualTo(DEFAULT_PRODUCT);
        assertThat(testCartItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    public void checkProductIsRequired() throws Exception {
        int cartSizeBeforeTest = cartService.getCurrentUserCart().size();

        CartItem cartItem = new CartItem();
        cartItem.quantity(1);
        // set the field null
        cartItem.product(null);

        // Create the Image, which fails.
        CartItemDTO cartItemDTO = cartMapper.toDto(cartItem);

        restCartMockMvc.perform(post("/api/cart")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartItemDTO)))
            .andExpect(status().isBadRequest());

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.addAll(cartService.getCurrentUserCart().getCartItems());
        assertThat(cartItemList).hasSize(cartSizeBeforeTest);
    }

    @Test
    public void checkQuantityIsRequired() throws Exception {
        int cartSizeBeforeTest = cartService.getCurrentUserCart().size();

        CartItem cartItem = new CartItem();
        cartItem.product(DEFAULT_PRODUCT);
        // set the field null

        // Create the CarItem, which fails.
        CartItemDTO cartItemDTO = cartMapper.toDto(cartItem);

        restCartMockMvc.perform(post("/api/cart")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartItemDTO)))
            .andExpect(status().isBadRequest());

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.addAll(cartService.getCurrentUserCart().getCartItems());
        assertThat(cartItemList).hasSize(cartSizeBeforeTest);
    }

    @Test
    public void addindAnAlreadyAddedItemIncrementOnlyHisQuantity() throws Exception {
        CartItem cartItem = new CartItem(DEFAULT_QUANTITY, DEFAULT_PRODUCT);
        Cart cartBeforeAdding = cartService.getCurrentUserCart();
        int cartSizeBeforeAdding = cartBeforeAdding.size();
        BigDecimal cartTotalBeforeAdding = cartBeforeAdding.getTotal();

        CartItemDTO cartItemDTO = cartMapper.toDto(cartItem);

        restCartMockMvc.perform(post("/api/cart")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartItemDTO)))
            .andExpect(status().isCreated());

        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.addAll(cartService.getCurrentUserCart().getCartItems());
        assertThat(cartItemList).hasSize(cartSizeBeforeAdding);
        Optional<CartItem> testCartItem = cartItemList.stream().filter((item) -> item.getProduct().equals(DEFAULT_PRODUCT)).findFirst();
        assertThat(testCartItem.get().getQuantity()).isEqualTo(DEFAULT_QUANTITY + 1);
        assertThat(testCartItem.get().getTotal()).isEqualTo(cartItem.getProduct().getPrice().add(cartTotalBeforeAdding));
    }

    @Test
    public void deleteCartItem() throws Exception {
        Cart cartItemBeforeDelete = cartService.getCurrentUserCart();
        cartItemBeforeDelete.addCartItem(new CartItem(1, DEFAULT_PRODUCT));
        int cartItemSizeBeforeDelete = cartItemBeforeDelete.size();

        // Delete a cartItem
        restCartMockMvc.perform(delete("/api/cart/items/{id}", DEFAULT_PRODUCT.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        boolean cartItemExistsInSessionCart = cartService.getCurrentUserCart().getCartItems().contains(new CartItem(1, DEFAULT_PRODUCT));
        assertThat(cartItemExistsInSessionCart).isFalse();

        // Validate cart is empty
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.addAll(cartService.getCurrentUserCart().getCartItems());
        assertThat(cartItemList).hasSize(cartItemSizeBeforeDelete - 1);
    }
}
