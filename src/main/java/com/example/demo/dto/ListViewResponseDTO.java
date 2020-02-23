package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created on 2/21/20
 *
 * @author manhvan.nguyen
 */
public class ListViewResponseDTO {

    private long total;
    @JsonProperty("current_page")
    private int currentPage;

    @JsonProperty("page_size")
    private int pageSize;

    private List<ItemViewResponseDTO> items;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<ItemViewResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemViewResponseDTO> items) {
        this.items = items;
    }
}
