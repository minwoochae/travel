package com.travel.Dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
    private List<Long> selectedProductIds;

    public List<Long> getSelectedProductIds() {
        return selectedProductIds;
    }

    public void setSelectedProductIds(List<Long> selectedProductIds) {
        this.selectedProductIds = selectedProductIds;
    }
}
