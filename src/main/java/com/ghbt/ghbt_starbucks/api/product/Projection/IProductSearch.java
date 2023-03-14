package com.ghbt.ghbt_starbucks.api.product.Projection;

public interface IProductSearch {

    Long getId();

    String getName();

    Integer getPrice();

    String getDescription();

    String getThumbnailUrl();

    Integer getStock();

    Integer getLikeCount();

    Boolean getIsBest();

    Boolean getIsNew();
}
