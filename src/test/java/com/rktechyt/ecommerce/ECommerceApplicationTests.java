package com.rktechyt.ecommerce;

import com.rktechyt.ecommerce.controller.CartController;
import com.rktechyt.ecommerce.controller.LoginController;
import com.rktechyt.ecommerce.controller.ShopController;
import com.rktechyt.ecommerce.model.Category;
import com.rktechyt.ecommerce.model.Product;
import com.rktechyt.ecommerce.model.Role;
import com.rktechyt.ecommerce.model.User;
import com.rktechyt.ecommerce.repository.RoleRepository;
import com.rktechyt.ecommerce.repository.UserRepository;
import com.rktechyt.ecommerce.service.CategoryService;
import com.rktechyt.ecommerce.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ECommerceApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private RoleRepository roleRepository;

	@MockBean
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@MockBean
	private HttpServletRequest httpServletRequest;

	@MockBean
	private ProductService productService;

	@MockBean
	private CategoryService categoryService;

	@InjectMocks
	private LoginController loginController;

	@InjectMocks
	private CartController cartController;

	@InjectMocks
	private ShopController shopController;

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testLoginGet() throws Exception {
		mockMvc.perform(get("/login"))
				.andExpect(status().isOk())
				.andExpect(view().name("login"));
	}

	@Test
	public void testRegisterGet() throws Exception {
		mockMvc.perform(get("/register"))
				.andExpect(status().isOk())
				.andExpect(view().name("register"));
	}

	@Test
	public void testAddToCart() throws Exception {
		Product product = new Product();
		product.setId(1L);
		when(productService.getProductById(1)).thenReturn(Optional.of(product));

		mockMvc.perform(get("/addToCart/1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/shop"));
	}

	@Test
	public void testCartGet() throws Exception {
		mockMvc.perform(get("/cart"))
				.andExpect(status().isOk())
				.andExpect(view().name("cart"));
	}

	@Test
	public void testCartRemove() throws Exception {
		mockMvc.perform(get("/cart/removeItem/0"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/cart"));
	}

	@Test
	public void testCheckout() throws Exception {
		mockMvc.perform(get("/checkout"))
				.andExpect(status().isOk())
				.andExpect(view().name("checkout"));
	}

	@Test
	public void testHome() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"));
	}

	@Test
	public void testShop() throws Exception {
		List<Category> categories = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		when(categoryService.getAllCategory()).thenReturn(categories);
		when(productService.getAllProducts()).thenReturn(products);

		mockMvc.perform(get("/shop"))
				.andExpect(status().isOk())
				.andExpect(view().name("shop"))
				.andExpect(model().attributeExists("categories"))
				.andExpect(model().attributeExists("products"));
	}

	@Test
	public void testShopByCategory() throws Exception {
		List<Category> categories = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		when(categoryService.getAllCategory()).thenReturn(categories);
		when(productService.getAllProductsByCategoryId(1)).thenReturn(products);

		mockMvc.perform(get("/shop/category/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("shop"))
				.andExpect(model().attributeExists("categories"))
				.andExpect(model().attributeExists("products"));
	}
}
