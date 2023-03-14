package it.academy.productservice.repositories.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "recipe", schema = "fitness")
public class RecipeEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID uuid;
    @Column(name = "dt_create")
    private Instant dtCreate;
    @Column(name = "dt_update")
    @Version
    private Instant dtUpdate;
    private String title;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "recipe_product", schema = "fitness",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "product_model_id"))
    @NotNull
    @Size(min = 1)
    private List<ProductModelEntity> composition;

    public RecipeEntity() {
    }

    public RecipeEntity(UUID uuid, Instant dtCreate, Instant dtUpdate, String title,
                        List<ProductModelEntity> composition) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.title = title;
        this.composition = composition;
    }

    public void addComposition(ProductModelEntity productModel){

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Instant getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Instant dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Instant getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(Instant dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ProductModelEntity> getComposition() {
        return composition;
    }

    public void setComposition(List<ProductModelEntity> composition) {
        this.composition = composition;
    }
}
