package com.me.catalog.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Product.
 */
@Document(collection = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @NotNull
    @Size(min = 0)
    @Field("name")
    private String name;

    @NotNull
    @Size(min = 0, max = 500)
    @Field("description")
    private String description;

    @NotNull
    @DecimalMin(value = "0")
    @Field("price")
    private BigDecimal price;

    @NotNull
    @Size(min=1)
    @Field("categories")
    private Set<Category> categories = new HashSet<>();


    @Field("tag")
    private Set<Tag> tags = new HashSet<>();

    @NotNull
    @Size(min=1)
    @Field("images")
    private Set<Image> images = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public Product categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Product addCategories(Category category) {
        this.categories.add(category);
        return this;
    }

    public Product removeCategories(Category category) {
        this.categories.remove(category);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public Product tags(Set<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Product addTags(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public Product removeTags(Tag tag) {
        this.tags.remove(tag);
        return this;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Set<Image> getImages() {
        return this.images;
    }

    public Product images(Set<Image> images) {
        this.images = images;
        return this;
    }

    public Product addImages(Image image) {
        this.images.add(image);
        return this;
    }

    public Product removeImages(Image image) {
        this.images.remove(image);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", price='" + getPrice() + "'" +
            ", categories= '" + getCategories().toString() + "'" +
            ", tags= '" + getTags().toString() + "'" +
            ", images= '" + getImages().toString() + "'" +
            "}";
    }
}
