package com.zosh.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @ManyToOne
    private User user;
    private String image;
    private String description;
    private boolean vegetarian;
    private LocalDateTime createdAt;
    @ManyToMany
    @JoinTable(
            name = "recipe_likes",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likes = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(Long id, String title, User user, String image, String description, boolean vegetarian, LocalDateTime createdAt, List<User> likes) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.image = image;
        this.description = description;
        this.vegetarian = vegetarian;
        this.createdAt = createdAt;
        this.likes = likes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<User> getLikes() {
        return likes;
    }

    public void setLikes(List<User> likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", user=" + user +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", vegetarian=" + vegetarian +
                ", createdAt=" + createdAt +
                ", likes=" + likes +
                '}';
    }
}
