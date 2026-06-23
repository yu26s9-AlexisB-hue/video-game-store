package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

//import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId)
    {
        // load the user's cart rows, look up each product, and build the ShoppingCart
        List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId); //Gets the rows
        ShoppingCart cart = new ShoppingCart();

        for (CartItem item: cartItems){
            Product product = productService.getById(item.getProductId());

            ShoppingCartItem cartItem = new ShoppingCartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(item.getQuantity());

            cart.add(cartItem);
        }
        return cart;
    }

    // Shows items in the cart
    public CartItem getCartItem(int userId, int productId) {
        return shoppingCartRepository.findByUserIdAndProductId(userId,productId);
    }

    //allows the user to remove 1 Item from their cart.
    public ShoppingCart removeItem(int userId, int productId){
        CartItem item = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        if (item != null){
            shoppingCartRepository.delete(item);
        }
        return getByUserId(userId);
    }

    // Clears out the users shopping cart
    public ShoppingCart clearCart(int userId){
        shoppingCartRepository.deleteByUserId(userId);

        return getByUserId(userId);
    }

}
