package com.ghbt.ghbt_starbucks.product.Projection;

public interface IProductSearch {
    Long getId();
    String getName();
    Integer getPrice();
    String getDescription();
    String getThumbUrl();
    Integer getStock();
    Integer getLikeCount();
    Boolean getIsBest();
}
