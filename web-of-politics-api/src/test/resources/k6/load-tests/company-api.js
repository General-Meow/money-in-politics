import http from 'k6/http';
import { check, sleep } from 'k6';

/**
 * Company API Load Test for k6
 * Tests company listing, getting, creating, and deleting endpoints
 */

export const options = {
  stages: [
    { duration: '30s', target: 50 },   // Ramp up to 50 users over 30 seconds
    { duration: '2m', target: 50 },     // Hold at 50 users for 2 minutes
    { duration: '30s', target: 100 },   // Scale to 100 users
    { duration: '2m', target: 100 },    // Stable load at 100 users
    { duration: '30s', target: 50 },    // Ramp down
  ],
  thresholds: {
    http_req_duration: ['p(95)<500'],   // 95th percentile < 500ms
    http_req_failed: ['rate<0.01'],     // Error rate < 1%
  },
};

// Authentication token (can be obtained from auth.js)
const BearerToken = process.env.K6_BEARER_TOKEN || 'dummy-token';

function makeRequest(url, method, data = null) {
  let options = { headers: { 'Authorization': `Bearer ${BearerToken}` } };
  if (method === 'POST' && data) {
    options.headers['Content-Type'] = 'application/json';
  }
  
  return http[method](url, data ? JSON.stringify(data) : null, options);
}

// Test 1: List all companies
export function testListCompanies() {
  const res = makeRequest('http://localhost:8080/api/v1/companies', 'GET');
  
  check(res, {
    'list status is OK': (r) => r.status === 200,
    'list response time < 500ms': (r) => r.timings.duration < 500,
  });
}

// Test 2: Get company by ID
export function testGetCompanyById() {
  const res = makeRequest('http://localhost:8080/api/v1/companies/existing-id', 'GET');
  
  check(res, {
    'get status is OK': (r) => r.status === 200,
    'get response time < 100ms': (r) => r.timings.duration < 100,
  });
}

// Test 3: Create new company
export function testCreateCompany() {
  const res = makeRequest(
    'http://localhost:8080/api/v1/companies', 
    'POST',
    {
      "id": "load_test_company_" + __VU,
      "name": "Load Test Company",
      "industry": "Technology",
      "headquarters": "US"
    }
  );
  
  check(res, {
    'create status is CREATED': (r) => r.status === 201 || r.status === 200,
    'create response time < 500ms': (r) => r.timings.duration < 500,
  });
}

// Test 4: Search companies by industry filter
export function testGetCompaniesByIndustry() {
  const res = makeRequest(
    'http://localhost:8080/api/v1/companies?industry=Technology', 
    'GET'
  );
  
  check(res, {
    'filter status is OK': (r) => r.status === 200,
    'filter response time < 500ms': (r) => r.timings.duration < 500,
  });
}

// Test 5: Search companies by name pattern
export function testSearchCompanies() {
  const res = makeRequest(
    'http://localhost:8080/api/v1/companies/search/Tech', 
    'GET'
  );
  
  check(res, {
    'search status is OK': (r) => r.status === 200,
    'search response time < 500ms': (r) => r.timings.duration < 500,
  });
}

// Test 6: Delete a company (ADMIN only)
export function testDeleteCompany() {
  const res = makeRequest(
    'http://localhost:8080/api/v1/companies/load-test-company-id', 
    'DELETE'
  );
  
  check(res, {
    'delete status is NO CONTENT': (r) => r.status === 204,
    'delete response time < 500ms': (r) => r.timings.duration < 500,
  });
}

// Default function - mix of operations for realistic load simulation
export default function () {
  let phase = '';
  
  switch (__VU % 6) {
    case 0: phase = 'testListCompanies'; break;       // List all (read-heavy)
    case 1: phase = 'testGetCompanyById'; break;       // Get single entity
    case 2: phase = 'testCreateCompany'; break;        // Create operations
    case 3: phase = 'testGetCompaniesByIndustry'; break;// Filter queries
    case 4: phase = 'testSearchCompanies'; break;      // Search by pattern
    case 5: phase = 'testDeleteCompany'; break;        // Delete operation (ADMIN)
  }
  
  check(phase, {});
  sleep(1); // Simulate think time between requests
}

// Summary function to print test results
export function handleSummary(data) {
  return {
    'stdout': textSummary(data).replace('k6:', ''),
    'text/plain': textSummary(data).replace('k6:', ''),
  };
}
