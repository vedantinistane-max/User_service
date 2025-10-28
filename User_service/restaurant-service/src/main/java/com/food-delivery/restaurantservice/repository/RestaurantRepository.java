package com.fooddelivery.restaurantservice.repository;

import com.fooddelivery.restaurantservice.entity.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByIsActive(Boolean isActive);

    List<Restaurant> findByCuisineTypeAndIsActive(String cuisineType, Boolean isActive);

    @Query("SELECT r FROM Restaurant r WHERE r.isActive = true ORDER BY r.rating DESC")

    List<Restaurant> findActiveRestaurantsOrderByRating();

}
