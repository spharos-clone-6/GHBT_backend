package com.ghbt.ghbt_starbucks.api.search_category.model;

import com.ghbt.ghbt_starbucks.api.product.Projection.IProductDetail;
import com.ghbt.ghbt_starbucks.api.product.dto.ResponseProduct;
import com.ghbt.ghbt_starbucks.api.product.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product productId;

    private String bigType;
    private String subType;
    private String season;
    private String volume;

}
