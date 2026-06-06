// Neo4j Schema Validation Queries for STORY-001 Integration Testing

// VALIDATION 1: Check all node labels exist
SHOW LABELS;

// VALIDATION 2: Count nodes by label
MATCH (p:Politician) RETURN count(p) as politicianCount;
MATCH (c:Company) RETURN count(c) as companyCount;
MATCH (d:Donation) RETURN count(d) as donationCount;
MATCH (r:Relationship) RETURN count(r) as relationshipCount;
MATCH (p:Party) RETURN count(p) as partyCount;

// VALIDATION 3: Check all relationship types exist
CALL db.labels() YIELD label 
WHERE label IN ['Politician']
RETURN label as label, 
       count(()-[:DONATION_RECEIVED]->()) as donationReceivedCount,
       count(()-[:RELATED_TO]->()) as relatedToCount,
       count(()-[:WORKS_AT]->()) as worksAtCount,
       count(()-[:MEMBER_OF]->()) as memberOfCount,
       count(()-[:APPEARED_WITH]->()) as appearedWithCount;

// VALIDATION 4: Check indexes exist
SHOW INDEXES YIELD index, label, properties, name, version, unique, updated, lastAccessed WHERE properties IS NOT NULL LIMIT 10;
