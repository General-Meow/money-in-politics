package com.webofpolitics.mappers;

import com.webofpolitics.dto.RelationshipDto;
import com.webofpolitics.entities.Relationship;

/**
 * Relationship Mapper - Converts between Entity and DTO
 */
public class RelationshipMapper {
    
    /**
     * Convert Relationship entity to API DTO
     * 
     * @param relationship entity object
     * @return API response DTO
     */
    public static RelationshipDto toDto(Relationship relationship) {
        if (relationship == null) {
            return null;
        }
        
        RelationshipDto dto = new RelationshipDto();
        dto.setId(relationship.getId());
        dto.setType(relationship.getType() != null ? 
                   relationship.getType().name() : null);
        dto.setSubType(relationship.getSubType() != null ? 
                       relationship.getSubType().name() : null);
        dto.setSourceType(relationship.getSourceType() != null ? 
                          relationship.getSourceType().name() : null);
        dto.setTargetType(relationship.getTargetType() != null ? 
                          relationship.getTargetType().name() : null);
        dto.setSourceId(relationship.getSourceId());
        dto.setTargetId(relationship.getTargetId());
        dto.setAmount(relationship.getAmount());
        dto.setCurrency(relationship.getCurrency() != null ? 
                        relationship.getCurrency().name() : null);
        dto.setStartDate(relationship.getStartDate());
        dto.setEndDate(relationship.getEndDate());
        dto.setDescription(relationship.getDescription());
        dto.setEvidenceSource(relationship.getEvidenceSource());
        dto.setRelationshipOrigin(relationship.getRelationshipOrigin());
        
        return dto;
    }
    
    /**
     * Convert Relationship DTO to Entity for database storage
     * 
     * @param dto API request DTO
     * @return entity object
     */
    public static Relationship toEntity(RelationshipDto dto) {
        if (dto == null) {
            return null;
        }
        
        Relationship.EntityType sourceType = 
            dto.getSourceType() != null ? 
            dto.getSourceType() : Relationship.EntityType.COMPANY;
        Relationship.EntityType targetType = 
            dto.getTargetType() != null ? 
            dto.getTargetType() : Relationship.EntityType.POLITICIAN;
        
        return Relationship.builder()
            .id(dto.getId())
            .type(toRelationshipType(dto.getType()))
            .subType(dto.getSubType() != null ? 
                     toRelationshipSubType(dto.getSubType()) : null)
            .sourceType(sourceType)
            .targetType(targetType)
            .sourceId(dto.getSourceId())
            .targetId(dto.getTargetId())
            .amount(dto.getAmount())
            .currency(toCurrency(dto.getCurrency()))
            .startDate(dto.getStartDate())
            .endDate(dto.getEndDate())
            .description(dto.getDescription())
            .evidenceSource(dto.getEvidenceSource())
            .relationshipOrigin(dto.getRelationshipOrigin())
            .build();
    }
    
    /**
     * Convert DTO type string to RelationshipType enum
     */
    private static Relationship.RelationshipType toRelationshipType(String typeName) {
        if (typeName == null) {
            return null;
        }
        for (Relationship.RelationshipType type : Relationship.RelationshipType.values()) {
            if (type.name().equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        return null;
    }
    
    /**
     * Convert DTO sub-type string to RelationshipSubType enum
     */
    private static Relationship.RelationshipSubType toRelationshipSubType(String subTypeName) {
        if (subTypeName == null) {
            return null;
        }
        for (Relationship.RelationshipSubType type : Relationship.RelationshipSubType.values()) {
            if (type.name().equalsIgnoreCase(subTypeName)) {
                return type;
            }
        }
        return null;
    }
    
    /**
     * Convert DTO currency string to Currency enum
     */
    private static Relationship.Currency toCurrency(String currencyName) {
        if (currencyName == null) {
            return null;
        }
        for (Relationship.Currency currency : Relationship.Currency.values()) {
            if (currency.name().equalsIgnoreCase(currencyName)) {
                return currency;
            }
        }
        return null;
    }
}
