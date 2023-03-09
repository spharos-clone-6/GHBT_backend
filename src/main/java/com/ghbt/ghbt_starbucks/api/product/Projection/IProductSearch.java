package com.ghbt.ghbt_starbucks.api.product.Projection;

public interface IProductSearch {
    Long getId();
    String getName();
    Integer getPrice();
    String getDescription();
    String getThumbnail_url();
    Integer getStock();
    Integer getLike_count();
    Boolean getIs_best();
    Boolean getIs_new();
}
