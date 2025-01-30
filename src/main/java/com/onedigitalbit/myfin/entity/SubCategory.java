package com.onedigitalbit.myfin.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "SubCategories")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer subCategoryId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String subCategoryName;
}
