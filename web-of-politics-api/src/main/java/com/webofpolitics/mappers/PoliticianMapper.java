package com.webofpolitics.mappers;

import com.webofpolitics.dto.PoliticianDto;
import com.webofpolitics.entities.Politician;

/**
 * Politician Mapper — Converts between Entity and DTO
 */
public class PoliticianMapper {
    
    /**
     * Convert Politician entity to API DTO
     */
    public static PoliticianDto toDto(Politician politician) {
        if (politician == null) return null;
        
        PoliticianDto dto = new PoliticianDto();
        dto.setId(politician.getId());
        dto.setName(politician.getName());
        dto.setFullName(politician.getFullName());
        dto.setRole(politician.getRole());
        dto.setConstituency(politician.getConstituency());
        dto.setParty(politician.getParty());
        dto.setPartyShortName(politician.getPartyShortName());
        dto.setTenureStart(politician.getTenureStart());
        dto.setTenureEnd(politician.getTenureEnd());
        dto.setPhotoUrl(politician.getPhotoUrl());
        dto.setTwitterHandle(politician.getTwitterHandle());
        dto.setWebsite(politician.getWebsite());
        dto.setShadowMinisterRole(politician.getShadowMinisterRole());
        dto.setIsShadowMinister(politician.isShadowMinister());
        return dto;
    }
    
    /**
     * Convert Politician DTO to Entity for database storage
     */
    public static Politician toEntity(PoliticianDto dto) {
        if (dto == null) return null;
        
        return Politician.builder()
            .id(dto.getId())
            .name(dto.getName())
            .fullName(dto.getFullName())
            .role(dto.getRole())
            .constituency(dto.getConstituency())
            .party(dto.getParty())
            .partyShortName(dto.getPartyShortName())
            .tenureStart(dto.getTenureStart())
            .tenureEnd(dto.getTenureEnd())
            .photoUrl(dto.getPhotoUrl())
            .twitterHandle(dto.getTwitterHandle())
            .website(dto.getWebsite())
            .shadowMinisterRole(dto.getShadowMinisterRole())
            .isShadowMinister(dto.isShadowMinister())
            .build();
    }
}
