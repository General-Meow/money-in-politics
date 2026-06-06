# STORY-001: Neo4j Graph Schema

**Epic:** EPIC-01 (Graph Engine and Data Ingestion Pipeline)  
**Priority:** P0  
**Story Points:** 5  
**Assignee:** [TBD]

## As A

Developer / Data Engineer

## I Want

A properly schema-modelled Neo4j graph database with all entity types and relationship definitions

## So That

Relationship queries perform efficiently and can support complex multi-hop traversal

---

## Acceptance Criteria

### Given the Neo4j container is running
**When** I execute the schema initialization script  
**Then** the following node labels exist:
- `:Politician` with properties: name, fullName, role, constituency, party, tenure_start, tenure_end, photo_url
- `:Company` with properties: name, legalName, industry, headquarters, ceo_id, registered_office, incorporation_date  
- `:Donation` with properties: amount, currency, date, donor_type, disclosure_reference
- `:Relationship` with properties: type (family|board|education|social|media), start_date, end_date, description
- `:Party` with properties: name, acronym, ideology

### And when the schema is created
**Then** the following relationship types exist:
- `(Politician)-[:DONATION_RECEIVED]->(Donation)`  
- `(Donation)-[:GAVE_TO]->(Politician|Company)`
- `(Politician)-[:RELATED_TO]->(:Politician {type: "family"})`
- `(Politician)-[:WORKS_AT]->(:Company)`
- `(Politician)-[:ATTENDED]->(:Institution {name: "...", type: "education"})`  
- `(Politician)-[:MEMBER_OF]->(:Party)`
- `(Politician)-[:APPEARED_WITH]->(:Politician {context: "media|event|board"})`

### And when indexes are created
**Then** labels and properties have appropriate indexes:
- `Politician` nodes indexed on `name`, `constituency`, `party`
- `Company` nodes indexed on `name`, `legalName`
- `Donation` edges indexed on `date`, `amount`
- All nodes indexed on ID for fast lookups

---

## Technical Notes

### Neo4j Graph Structure Example

```cypher
// Politician node
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
}) RETURN p;

// Donation relationship edge  
MATCH (p:Politician {id: "UK-CHS-LAB"}), (d)
WHERE d.donor_type = 'Corporate' AND d.amount > 10000
CREATE (p)-[:DONATION_RECEIVED {amount: d.amount, date: d.date, disclosure_reference: d.reference}]->(d);

// Family relationship
MATCH (p1:Politician {name: "Keir Starmer"}), (p2:Politician {name: "Susanne Starmer"})
CREATE (p1)-[:RELATED_TO {type: "spouse", start_date: "2024-01-01"}]->(p2);

// Board relationship over time
MATCH (p:Politician {name: "Keir Starmer"}), 
      (c:Company {name: "Blencoe LLP"})
CREATE (p)-[:WORKS_AT {type: "board_seat", start_date: "2015-06-01", end_date: "2019-12-31"}]->(c);
```

### Index Recommendations

```cypher
// Politician lookups by name or constituency
CREATE INDEX politician_name ON :Politician(name);
CREATE INDEX politician_constituency ON :Politician(constituency);

// Company lookups
CREATE INDEX company_name ON :Company(name, legalName);

// Donation edges for filtering  
CREATE INDEX donation_date_amount ON []-[:DONATION_RECEIVED|GAVE_TO]->(d) WHERE d.date IS NOT NULL(d.amount);
```

---

## Risks

- **Risk:** Neo4j memory requirements exceed initial VPS capacity  
  - **Mitigation:** Start with small instance; plan horizontal scaling later

- **Risk:** Schema changes required during development  
  - **Mitigation:** Use Neo4j's constraint-free schema for early iterations

---

## Definition of Done

- [ ] Node labels and properties match specification
- [ ] All relationship types created with proper property keys
- [ ] Indexes created on high-selectivity query fields
- [ ] Schema initialization script tested in Docker container
- [ ] Documentation updated in repo README with Cypher examples

---

## References

- Neo4j Graph Database Basics: https://neo4j.com/graph-database/
- UK Parliament API Documentation: https://services.parliament.uk/develop/api.html
