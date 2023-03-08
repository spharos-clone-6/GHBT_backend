package com.ghbt.ghbt_starbucks.product.Projection;

public interface IProductSearch {
    Long getId();
    String getName();
    Integer getPrice();
    String getDescription();
    String getThumb_url();
    Integer getStock();
    Integer getLike_count();
    Boolean getIs_best();
}
