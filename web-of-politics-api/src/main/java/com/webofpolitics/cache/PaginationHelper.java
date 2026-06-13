package com.webofpolitics.cache;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Pagination Helper — Generates paginated responses for REST API endpoints
 * <p>
 * Phase 3 Implementation: Unified pagination across all REST APIs.
 */
public class PaginationHelper {
    
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;
    
    /**
     * Generate paginated list from iterable
     * 
     * @param items Items to paginate
     * @param page Requested page number (0-indexed)
     * @param size Number of items per page
     * @return Paginated response with data, metadata, and pagination info
     */
    public static <T> PaginatedResponse<T> createPaginatedResponse(
            Iterable<T> items,
            int page,
            int size) {
        
        // Convert iterable to list for indexing
        List<T> allItems = new ArrayList<>();
        for (T item : items) {
            if (item != null) {
                allItems.add(item);
            }
        }
        
        return paginate(allItems, page, size);
    }
    
    /**
     * Paginate a list of items with metadata
     */
    @SuppressWarnings("unchecked")
    public static <T> PaginatedResponse<T> paginate(
            Collection<T> allItems,
            int page,
            int size) {
        
        // Handle negative or zero page as 0
        if (page < 0) {
            page = 0;
        }
        
        // Handle oversized page size
        size = Math.min(size, MAX_PAGE_SIZE);
        if (size <= 0) {
            size = DEFAULT_PAGE_SIZE;
        }
        
        int totalItems = allItems.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        
        // If no items, return empty pagination
        if (totalItems == 0) {
            return new PaginatedResponse<>(
                Collections.emptyList(),
                createMetadata(0, size, page),
                createPaginationInfo(0, 0, 0)
            );
        }
        
        // Handle pages beyond available data
        int startPage = Math.min(page, totalPages - 1);
        if (startPage >= totalPages) {
            return new PaginatedResponse<>(
                Collections.emptyList(),
                createMetadata(0, size, page),
                createPaginationInfo(startPage, totalPages, 0)
            );
        }
        
        // Get paginated slice
        int startIndex = startPage * size;
        int endIndex = Math.min(startIndex + size, totalItems);
        
        List<T> currentPageItems = new ArrayList<>(allItems.subList(startIndex, endIndex));
        
        return new PaginatedResponse<>(
            currentPageItems,
            createMetadata(totalItems, size, page),
            createPaginationInfo(startPage, totalPages, startIndex)
        );
    }
    
    /**
     * Create metadata for paginated response
     */
    private static <T> ResponseMetadata<T> createMetadata(
            int totalItems,
            int size,
            int page) {
        
        return new ResponseMetadata<>(totalItems, size, page);
    }
    
    /**
     * Create pagination info for response
     */
    private static <T> ResponsePaginationInfo<T> createPaginationInfo(
            int currentPage,
            int totalPages,
            int startIndex) {
        
        int totalPagesToShow = (totalItems > 0) ? totalPages : 1;
        return new ResponsePaginationInfo<>(currentPage, totalPagesToShow, startIndex);
    }
    
    /**
     * PaginatedResponse DTO for REST API responses
     */
    public static class PaginatedResponse<T> {
        private List<T> data;
        private ResponseMetadata<T> metadata;
        private ResponsePaginationInfo<T> pagination;
        
        public PaginatedResponse(
                List<T> data,
                ResponseMetadata<T> metadata,
                ResponsePaginationInfo<T> pagination) {
            this.data = data;
            this.metadata = metadata;
            this.pagination = pagination;
        }
        
        // Getters and setters
        public List<T> getData() { return data; }
        public void setData(List<T> data) { this.data = data; }
        
        public ResponseMetadata<T> getMetadata() { return metadata; }
        public void setMetadata(ResponseMetadata<T> metadata) { this.metadata = metadata; }
        
        public ResponsePaginationInfo<T> getPagination() { return pagination; }
        public void setPagination(ResponsePaginationInfo<T> pagination) { this.pagination = pagination; }
    }
    
    /**
     * ResponseMetadata for paginated data
     */
    public static class ResponseMetadata<T> {
        private int totalItems;
        private int size;
        private int page;
        
        public ResponseMetadata(int totalItems, int size, int page) {
            this.totalItems = totalItems;
            this.size = size;
            this.page = page;
        }
        
        public int getTotalItems() { return totalItems; }
        public void setTotalItems(int totalItems) { this.totalItems = totalItems; }
        
        public int getSize() { return size; }
        public void setSize(int size) { this.size = size; }
        
        public int getPage() { return page; }
        public void setPage(int page) { this.page = page; }
    }
    
    /**
     * ResponsePaginationInfo for pagination info
     */
    public static class ResponsePaginationInfo<T> {
        private int currentPage;
        private int totalPages;
        private int totalElements;
        
        public ResponsePaginationInfo(int currentPage, int totalPages, int start) {
            this.currentPage = currentPage;
            this.totalPages = totalPages;
            this.totalElements = (totalItems > 0) ? totalPages * getSize() : 0;
        }
        
        public int getCurrentPage() { return currentPage; }
        public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }
        
        public int getTotalPages() { return totalPages; }
        public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
        
        public int getTotalElements() { return totalElements; }
        public void setTotalElements(int totalElements) { this.totalElements = totalElements; }
    }
}
