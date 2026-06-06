// Neo4j Graph Schema Initialization Script
// Story: STORY-001 — Neo4j Graph Schema
// File Location: _bmad-output/implementation-artifacts/stories/story-001/schema-initialization.cypher

// =============================================
// 1. CREATE NODE LABELS WITH PROPERTIES
// =============================================

// Create Politician node label with properties
CREATE (p:Politician {
    id: "UK-CHS-LAB",
    name: "Keir Starmer",
    fullName: "Sir Keir Rodney Starmer",
    role: "Leader of the House of Commons",
    constituency: "Holborn and St Pancras",
    party: "Labour",
    tenure_start: "2024-05-05",
    tenure_end: null,
    photo_url: "https://parliament.uk/.../starmer.jpg"
});

// Create Company node label with properties
CREATE (c:Company {
    id: "COMP-001",
    name: "Blencoe LLP",
    legalName: "Blencoe LLP",
    industry: "Legal Services",
    headquarters: "London, England",
    ceo_id: null,
    registered_office: "123 Fleet Street, London EC4A 2BQ",
    incorporation_date: "2005-06-15"
});

// Create Party node label with properties
CREATE (part:Party {
    id: "LABOUR",
    name: "Labour Party",
    acronym: "Lab",
    ideology: "Social Democracy"
});

// =============================================
// 2. CREATE RELATIONSHIP TYPES WITH PROPERTIES
// =============================================

// Create DONATION_RECEIVED relationship between Politician and Donation
MATCH (p:Politician {id: "UK-CHS-LAB"}), (d)
WHERE d.donor_type = 'Corporate' AND d.amount > 10000
CREATE (p)-[:DONATION_RECEIVED {
    amount: d.amount, 
    currency: 'GBP',
    date: d.date, 
    disclosure_reference: d.reference
}]->(d);

// Create RELATED_TO relationship (family) between Politicians
MATCH (p1:Politician {name: "Keir Starmer"}), 
      (p2:Politician {name: "Susanne Starmer"})
CREATE (p1)-[:RELATED_TO {
    type: "spouse", 
    start_date: "2024-01-01"
}]->(p2);

// Create WORKS_AT relationship over time between Politician and Company
MATCH (p:Politician {name: "Keir Starmer"}), 
      (c:Company {name: "Blencoe LLP"})
CREATE (p)-[:WORKS_AT {
    type: "board_seat", 
    start_date: "2015-06-01", 
    end_date: "2019-12-31"
}]->(c);

// Create MEMBER_OF relationship between Politician and Party
MATCH (p:Politician {id: "UK-CHS-LAB"}),
      (part:Party {acronym: "Lab"})
CREATE (p)-[:MEMBER_OF {}]->(part);

// Create APPEARED_WITH relationship with context between two Politicians
MATCH (p1:Politician {name: "Keir Starmer"}), 
      (p2:Politician {name: "Rishi Sunak"})
CREATE (p1)-[:APPEARED_WITH {
    context: "media",
    start_date: "2024-07-05"
}]->(p2);

// =============================================
// 3. CREATE INDEXES FOR HIGH-SSELECTIVITY QUERIES
// =============================================

// Politician nodes indexed on name property
CREATE INDEX IF NOT EXISTS politician_name ON :Politician(name);

// Politician nodes indexed on constituency property  
CREATE INDEX IF NOT EXISTS politician_constituency ON :Politician(constituency);

// Politician nodes indexed on party property
CREATE INDEX IF NOT EXISTS politician_party ON :Politician(party);

// Company nodes indexed on name property
CREATE INDEX IF NOT EXISTS company_name ON :Company(name, legalName);

// Donation edges indexed on date and amount properties
CREATE INDEX IF NOT EXISTS donation_date_amount ON []-[:DONATION_RECEIVED|GAVE_TO]->(d) 
    WHERE d.date IS NOT NULL AND d.amount >= 100;

// All nodes indexed on ID for fast lookups
CREATE INDEX IF NOT EXISTS node_id_lookup ON :Politician(id);
CREATE INDEX IF NOT EXISTS company_id_lookup ON :Company(id);
CREATE INDEX IF NOT EXISTS party_id_lookup ON :Party(id);

// =============================================
// 4. EXAMPLE RELATIONSHIP TYPES (COMMENTS ONLY)
// =============================================

// Note: Other relationship types not included in initial schema:
// - ATTENDED (Politician -> Institution with education type)
// - Additional relationship variants for media, event, board contexts on APPEARED_WITH

// =============================================
// 5. SCHEMA VALIDATION QUERY
// =============================================

// Query to validate schema completeness
MATCH (p:Politician)-[:DONATION_RECEIVED|RELATED_TO|WORKS_AT|MEMBER_OF|APPEARED_WITH]->()
RETURN COUNT(p) as politicianCount, 
       relationships as relationshipCount;

