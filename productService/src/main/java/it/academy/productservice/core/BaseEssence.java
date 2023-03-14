package it.academy.productservice.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.academy.productservice.core.converters.InstantToLong;
import it.academy.productservice.core.converters.LongToInstant;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@JsonPropertyOrder({"dt_create", "dt_update"})
public abstract class BaseEssence {
    protected UUID uuid;
    @JsonProperty("dt_create")
    @JsonSerialize(converter = InstantToLong.class)
    @JsonDeserialize(converter = LongToInstant.class)
    protected Instant dtCreate;
    @JsonProperty("dt_update")
    @JsonSerialize(converter = InstantToLong.class)
    @JsonDeserialize(converter = LongToInstant.class)
    protected Instant dtUpdate;

    public BaseEssence() {
    }

    public BaseEssence(UUID uuid, Instant dt_create, Instant dt_update) {
        this.uuid = uuid;
        this.dtCreate = dt_create;
        this.dtUpdate = dt_update;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Instant getDtCreate(){
        return dtCreate;
    }
    public Instant getDtUpdate() {
        return dtUpdate;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setDtCreate(Instant dtCreate) {
        this.dtCreate = dtCreate;
    }

    public void setDtUpdate(Instant dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEssence that = (BaseEssence) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(dtCreate, that.dtCreate) && Objects.equals(dtUpdate, that.dtUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate);
    }

    @Override
    public String toString() {
        return "BaseEssence{" +
                "uuid=" + uuid +
                ", dt_create=" + dtCreate +
                ", dt_update=" + dtUpdate +
                '}';
    }
}
