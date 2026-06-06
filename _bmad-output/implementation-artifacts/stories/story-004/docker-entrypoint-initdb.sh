#!/bin/bash
# Neo4j Seed Script for STORY-004 Integration Testing
# Creates sample company data for Companies House API integration

echo "=== Neo4j Schema Initialization for STORY-004 ==="

CALL db.createNode(:Company {
    name: "John Smith Ltd",
    registrationNumber: "12345678",
    sicCode: 70220,
    industryDescription: "Computer programming activities",
    addressLine1: "123 Business Park"
}) YIELD node as company

CALL db.createNode(:DonationRecipientCompany {
    name: "John Smith Ltd",
    donationReceived: true
}) YIELD node as recipient

CREATE (company)-[:HAS_DONATIONS]->(recipient) RETURN count(*)

CALL db.createIndex(:Company, ["registrationNumber"]) YIELD index
CALL db.createIndex(:DonationRecipientCompany, ["name"]) YIELD index

echo "✅ STORY-004: Sample company data and indexes created"
