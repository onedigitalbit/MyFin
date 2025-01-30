package com.onedigitalbit.myfin.service;

import com.onedigitalbit.myfin.entity.SubCategory;
import com.onedigitalbit.myfin.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryService(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    public SubCategory createSubCategory(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    public SubCategory getSubCategoryById(Long id) {
        return subCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("SubCategory not found"));
    }

    public SubCategory updateSubCategory(Long id, SubCategory subCategory) {
        SubCategory existingSubCategory = getSubCategoryById(id);
        existingSubCategory.setSubCategoryName(subCategory.getSubCategoryName());
        return subCategoryRepository.save(existingSubCategory);
    }

    public void deleteSubCategory(Long id) {
        subCategoryRepository.deleteById(id);
    }
}