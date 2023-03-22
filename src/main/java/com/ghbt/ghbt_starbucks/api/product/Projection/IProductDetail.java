package com.ghbt.ghbt_starbucks.api.product.Projection;

public interface IProductDetail {

    Long getProductId();

    String getName();

    Integer getPrice();

    String getDescription();

    String getThumbnailUrl();

    Integer getStock();

    Integer getLikeCount();

    Boolean getIsBest();

    Boolean getIsNew();

    String getBigType();

    String getSubType();

    String getSeason();

    String getVolume();


}
