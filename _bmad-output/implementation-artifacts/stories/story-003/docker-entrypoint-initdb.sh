#!/bin/bash
# Neo4j Seed Script for STORY-003 Integration Testing
# Creates sample donation data for Electoral Commission API integration

echo "=== Neo4j Schema Initialization for STORY-003 ==="

CALL db.createNode(:Donor {
    name: "John Smith Ltd",
    address: "123 Business Park, Manchester"
}) YIELD node as donor

CALL db.createNode(:Politician {
    name: "Andrew Smith MP",
    constituency: "Manchester Central"
}) YIELD node as mp

CREATE (mp)-[:RECEIVED_FROM]->(donor) RETURN count(*)

CALL db.createIndex(:Donor, ["name"]) YIELD index
CALL db.createIndex(:Politician, ["name"]) YIELD index

echo "✅ STORY-003: Sample donation data and indexes created"
