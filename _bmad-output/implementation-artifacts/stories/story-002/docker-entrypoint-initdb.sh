#!/bin/bash
# Neo4j Seed Script for STORY-002 Integration Testing
# Creates sample MP data from Parliament.uk API structure

echo "=== Neo4j Schema Initialization for STORY-002 ==="

# Create sample politicians with voting records and committee memberships

CALL db.createNode(:Politician {
    name: 'Andrew Smith',
    constituency: 'Manchester Central',
    party: 'Conservative Party',
    electorate: 93681,
    email: 'andrew.smith.mp@parliament.uk',
    whip: true
}) YIELD node as mp

CALL db.createNode(:Constituency {
    name: "Manchester Central",
    party: 'Conservative Party',
    electorate: 93681
}) YIELD node as constituency

CREATE (mp)-[:REPRESENTS]->(constituency) RETURN count(*)

# Add sample voting record
CREATE (:VotingRecord {
    divisionId: 'div-2024-001',
    date: date('2024-01-15'),
    motionTitle: 'Trade and Investment Bill (Second Reading)',
    aye: true,
    noes: false
}) RETURN count(*)

# Add committee memberships
CREATE (:ParliamentDivision {
    divisionId: 'div-committee-001',
    title: 'Health Committee'
}) YIELD node as committee

CREATE (mp)-[:MEMBER_OF]->(committee) RETURN count(*)

CALL db.createIndex(:Politician, ["name"]) YIELD index
CALL db.createIndex(:Constituency, ["name"]) YIELD index
CALL db.createIndex(:VotingRecord, ["divisionId"]) YIELD index
CALL db.createIndex(:ParliamentDivision, ["title"]) YIELD index

echo "✅ STORY-002: Sample data and indexes created"
