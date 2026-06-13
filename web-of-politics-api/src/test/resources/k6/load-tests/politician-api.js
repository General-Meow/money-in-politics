import http from 'k6/http';
import { check, sleep } from 'k6';
import { textSummary } from 'https://jslib.k6.io/k6-utils/1.4.0/index.js';

// Import common functions file (keep in same directory)
import { getBearerToken } from './auth.js';

export const options = {
  stages: [
    // Ramp up test with gradual increase in vusers
    { duration: '30s', target: 50 },   // Gradual increase to 50 users over 30 seconds
    { duration: '1m', target: 50 },     // Hold at 50 users for 1 minute
    { duration: '30s', target: 100 },   // Scale to 100 users over 30 seconds
    { duration: '2m', target: 100 },    // Stable load at 100 users for 2 minutes
    { duration: '30s', target: 50 },    // Ramp down to 50 users
  ],
  // Performance thresholds
  thresholds: {
    http_req_duration: ['p(95)<500'],  // 95th percentile < 500ms for API calls
    http_req_failed: ['rate<0.01'],    // Error rate < 1%
  },
};

// Get authentication token (modify as needed)
const BearerToken = getBearerToken();
const baseUrl = 'http://localhost:8080';

// Helper function to include auth header
function makeRequest(url, method, data = null) {
  let options = { headers: { 'Authorization': `Bearer ${BearerToken}` } };
  if (method === 'POST' && data) {
    options.headers['Content-Type'] = 'application/json';
  }
  
  return http[method](url, data ? JSON.stringify(data) : null, options);
}

// Test 1: List all politicians (paginated)
export function testListPoliticians() {
  const res = makeRequest(`${baseUrl}/api/v1/politicians`, 'GET');
  
  check(res, {
    'list status is OK': (r) => r.status === 200,
    'list response time < 500ms': (r) => r.timings.duration < 500,
  });
}

// Test 2: Get politician by ID
export function testGetPoliticianById() {
  const res = makeRequest(`${baseUrl}/api/v1/politicians/existing-id`, 'GET');
  
  check(res, {
    'get status is OK': (r) => r.status === 200,
    'get response time < 100ms': (r) => r.timings.duration < 100,
  });
}

// Test 3: Create new politician
export function testCreatePolitician() {
  const res = makeRequest(
    `${baseUrl}/api/v1/politicians`, 
    'POST', 
    {
      "id": "load_test_politician_" + __VU,
      "name": "Load Test Politician",
      "fullName": "Jane Load Test Smith"
    }
  );
  
  check(res, {
    'create status is CREATED': (r) => r.status === 201 || r.status === 200,
    'create response time < 500ms': (r) => r.timings.duration < 500,
  });
}

// Test 4: Search politicians by name pattern
export function testSearchPoliticians() {
  const res = makeRequest(
    `${baseUrl}/api/v1/politicians/search/Smith`, 
    'GET'
  );
  
  check(res, {
    'search status is OK': (r) => r.status === 200,
    'search response time < 500ms': (r) => r.timings.duration < 500,
  });
}

// Test 5: Get politician by name filter
export function testGetPoliticiansByName() {
  const res = makeRequest(
    `${baseUrl}/api/v1/politicians?name=Smith`, 
    'GET'
  );
  
  check(res, {
    'filter status is OK': (r) => r.status === 200,
    'filter response time < 500ms': (r) => r.timings.duration < 500,
  });
}

// Default function - mix of operations for realistic load simulation
export default function () {
  let phase = '';
  
  switch (__VU % 5) {
    case 0: phase = 'testListPoliticians'; break;      // List operations (read-heavy)
    case 1: phase = 'testGetPoliticianById'; break;     // Get single entity
    case 2: phase = 'testCreatePolitician'; break;      // Create operations (write)
    case 3: phase = 'testSearchPoliticians'; break;     // Search by pattern
    case 4: phase = 'testGetPoliticiansByName'; break;  // Filter queries
  }
  
  check(phase, {});
  sleep(1); // Simulate think time between requests (adjust as needed)
}

// Summary function to print test results
export function handleSummary(data) {
  return {
    'stdout': textSummary(data),
    'text/plain': textSummary(data).replace('k6:', ''),
  };
}
