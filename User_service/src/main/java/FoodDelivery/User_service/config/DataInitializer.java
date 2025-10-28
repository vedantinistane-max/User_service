package FoodDelivery.User_service.config;

import FoodDelivery.User_service.entity.Role;
import FoodDelivery.User_service.entity.User;
import FoodDelivery.User_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
// Check if users already exist
        if (userRepository.count() == 0) {
// Create admin user
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@fooddelivery.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFullName("System Administrator");
            admin.setPhoneNumber("+1-555-0001");
            admin.setAddress("123 Admin Street, Admin City, AC 12345");
            admin.setRole(Role.ADMIN);
            admin.setCreatedAt(LocalDateTime.now());
// Create sample regular user
            User user1 = new User();
            user1.setUsername("john_doe");
            user1.setEmail("john.doe@email.com");
            user1.setPassword(passwordEncoder.encode("password123"));
            user1.setFullName("John Doe");
            user1.setPhoneNumber("+1-555-0002");
            user1.setAddress("456 User Street, User City, UC 67890");
            user1.setRole(Role.USER);
            user1.setCreatedAt(LocalDateTime.now());
// Create sample restaurant owner
            User restaurantOwner = new User();
            restaurantOwner.setUsername("restaurant_owner");
            restaurantOwner.setEmail("owner@restaurant.com");
            restaurantOwner.setPassword(passwordEncoder.encode("owner123"));
            restaurantOwner.setFullName("Restaurant Owner");
            restaurantOwner.setPhoneNumber("+1-555-0003");
            restaurantOwner.setAddress("789 Restaurant Ave, Food City, FC 13579");
            restaurantOwner.setRole(Role.RESTAURANT_OWNER);
            restaurantOwner.setCreatedAt(LocalDateTime.now());

            userRepository.save(admin);
            userRepository.save(user1);
            userRepository.save(restaurantOwner);

            System.out.println("Sample users created:");
            System.out.println("Admin Username: admin, Password: admin123");
            System.out.println("User Username: john doe, Password: password123");
            System.out.println("Restaurant Owner - Username: restaurant_owner, Password: owner123");
        }
    }
}