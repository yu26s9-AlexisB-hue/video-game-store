package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.*;
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

    public ShoppingCart addProduct(User user, int productId){
        Product product = productService.getById(productId);

        if (product == null){
            throw new RuntimeException("Product not found.");
        }
        CartItem cartItem = shoppingCartRepository.findByUserIdAndProductId(user.getId(), productId);

        if (cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }else{
            cartItem = new CartItem();
            cartItem.setUserId(user.getId());
            cartItem.setProductId(productId);
            cartItem.setQuantity(1);
        }
        shoppingCartRepository.save(cartItem);

        return getByUserId(user.getId());
    }

    public ShoppingCart updateQuantity(User user, int productId, int quantity){
        CartItem cartItem = shoppingCartRepository.findByUserIdAndProductId(user.getId(), productId);

        if (cartItem == null){
            throw new RuntimeException("Product not found in cart.");
        }
        cartItem.setQuantity(quantity);
        shoppingCartRepository.save(cartItem);

        return getByUserId(user.getId());
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
