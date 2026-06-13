import http from 'k6/http';
import { parse } from 'url';

/**
 * Authentication helper for k6 load tests
 * Handles OAuth2 token requests and stores in environment variable
 */

// Store the access token (for use in other test files)
export let getBearerToken = () => process.env.K6_BEARER_TOKEN;

/**
 * Function to obtain OAuth2 access token
 * POST /oauth/token
 * Content-Type: application/x-www-form-urlencoded
 * 
 * Parameters:
 * - username: user identifier (e.g., email or ID)
 * - password: user password  
 * - grant_type: always 'password' for password grant flow
 */
export function requestToken(username, password) {
  const res = http.post(
    'http://localhost:8080/oauth/token',
    new URLSearchParams({
      username: username || process.env.TEST_USERNAME || 'admin',
      password: password || process.env.TEST_PASSWORD || 'password',
      grant_type: 'password'
    }),
    { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }
  );
  
  if (res.status === 200 || res.status === 201) {
    const data = JSON.parse(res.body);
    if (data.access_token) {
      process.env.K6_BEARER_TOKEN = data.access_token;
      console.log('Access token obtained and stored in environment');
    }
  }
  
  return res;
}

/**
 * Request OAuth2 token with specific credentials
 * Convenience function for common test scenarios
 */
export function requestTokenForUser(username, password) {
  const res = requestToken(username, password);
  
  if (res.status !== 200 && res.status !== 201) {
    console.log('Failed to obtain access token:', res.body);
    process.exit(1); // Fail test immediately if authentication fails
  }
  
  return res;
}

/**
 * Check if bearer token is available
 */
export function hasToken() {
  return getBearerToken() !== undefined && getBearerToken().length > 0;
}

/**
 * Get the access token value
 */
export function getToken() {
  return getBearerToken();
}
