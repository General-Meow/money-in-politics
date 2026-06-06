# STORY-005: Fallback Scrapers for API Failures

**Epic:** EPIC-01 (Graph Engine and Data Ingestion Pipeline)  
**Priority:** P0  
**Story Points:** 8  
**Assignee:** [TBD]

## As A

Backend Developer / DevOps Engineer

## I Want

Web scraping implementations using Scrapy/BeautifulSoup for data sources without functional APIs

## So That

API unavailability or rate limiting doesn't prevent complete historical data ingestion

---

## Acceptance Criteria

### Given a government website with no API
**When** the scraper runs  
**Then** HTML pages are fetched with proper rate limiting (5s minimum between requests)

### Given robots.txt exists
**When the scraper initializes  
**Then** robots.txt is parsed and respected; blocked paths return 403 or skip silently

### When scraping succeeds
**Then** extracted data matches API schema (same Neo4j node/edge structure)
- **And when extraction completes**
**Then** response includes metadata: `source_type: "scraper"`, `extraction_method: "html_parsing"`

---

## Target Scrape Sites

### Parliament.uk Scraper Targets

- Individual MP profile pages (for historical data not in API)
- Voting records on parliament website (historical archive)

### Electoral Commission Historical Data

- EC disclosure statements PDFs or HTML archives (2005-2010 era)
- Government gazette publications for donation disclosures

### Companies House (if beyond free tier limits)
- Official company profile pages via web interface
- Gazette notifications for director appointments

---

## Scrapy Project Structure

```
the-web-of-politics/
├── scrapers/
│   ├── parliament_profile.py
│   ├── historical_donations.py
│   └── companies_gazette.py
├── middlewares/
│   ├── rate_limit.py      # Respectful request delays
│   └── retry.py           # Automatic retries with backoff
├── pipelines/
│   ├── mp_pipeline.py     # Extract MP data to JSON
│   ├── donation_pipeline.py
│   └── company_pipeline.py
├── items/
│   ├── MpItem.py
│   ├── DonationItem.py
│   └── CompanyItem.py
└── settings.py            # Scrapy settings (ROBOTSTXT_OBEY = True)
```

### Example: Rate Limiting Middleware

```python
# middlewares/rate_limit.py
import time
import random
from scrapy import signals
from scrapy.downloadermiddlewares.retry import RetryMiddleware
from scrapy.exceptions import IgnoreRequest

class PoliteRateLimiterMiddleware:
    def __init__(self, crawler):
        self.last_request_time = {}
        
    @classmethod
    from_crawler(cls, crawler):
        middleware = cls(crawler)
        crawler.signals.connect(middleware.spider_opened, signal=signals.spider_opened)
        return middleware
    
    def spider_opened(self, spider):
        spider.start_time = time.time()
        
    def process_request(self, request, spider):
        domain = request.url.split('/')[2]
        current_time = time.time()
        
        if domain in self.last_request_time:
            time_since_last = current_time - self.last_request_time[domain]
            if time_since_last < 5.0:  # Minimum 5 seconds between requests
                delay = 5.0 - time_since_last + random.uniform(0, 2)
                return {'delay': delay}
        
        self.last_request_time[domain] = current_time
        return None
```

---

## HTML Extraction Example (BeautifulSoup)

```python
# Extracting MP profile data from Parliament.uk
from bs4 import BeautifulSoup
import re

def extract_mp_data(soup):
    mp_data = {
        'name': '',
        'constituency': '',
        'party': '',
        'tenure_start': None,
        'photo_url': None
    }
    
    # Name extraction
    name_div = soup.find('span', class_='member-name')
    if name_div:
        mp_data['name'] = clean_text(name_div.get_text(strip=True))
    
    # Constituency
    const_div = soup.find('p', string=re.compile(r'constituency.*?'))
    if const_div:
        text = const_div.get_text(strip=True)
        match = re.search(r'constituency:\s*(.+)', text, re.IGNORECASE)
        if match:
            mp_data['constituency'] = match.group(1).strip()
    
    # Party
    party_div = soup.find('a', href=re.compile(r'/people'))
    if party_div and 'href' in party_div.attrs:
        link_text = party_div.get_text(strip=True)
        if '(' in link_text:
            mp_data['party'] = link_text.split('(')[-1].strip()
    
    # Tenure start date
    tenure_start = soup.find('span', class_='date-range-start')
    if tenure_start:
        date_str = tenure_start.get_text(strip=True)
        try:
            mp_data['tenure_start'] = parse_date(date_str)  # Parse to ISO-8601
        except ValueError:
            pass
    
    return mp_data
```

---

## Error Handling and Logging

### Retry Logic with Exponential Backoff

```python
# In scraper configuration
SCrapy.conf.get('RETRY_HTTPCODES', (500, 502, 503, 504, -1))
SCrapy.conf.set('RETRY_TIMES', 3)
SCrapy.conf.set('RETRY_PRIORITY_DELTA', 0.5)
```

### Failure Logging for Data Quality Tracking

```python
@logging.capture()
def extract_donations_from_page(url):
    try:
        response = yield from crawl(url, timeout=30)  # 30s timeout
        items = parse_donations(response.text)
        for item in items:
            item['source_url'] = url
            item['extraction_status'] = 'success'
            yield item
    except Exception as e:
        logger.error(f"Failed to extract from {url}: {e}")
        # Log failure; don't crash scraper
        return {'status': 'failed', 'error': str(e), 'source_url': url}
```

---

## Risks

- **Risk:** HTML structure changes between scrapes  
  - **Mitigation:** Add XPath selectors as fallback; alert on extraction failures
  
- **Risk:** Websites add CAPTCHA or anti-bot measures  
  - **Mitigation:** Start with respectful scraping; use headless browser only if necessary

- **Risk:** Scraped data is incomplete without API  
  - **Acceptance:** Accept partial data coverage; focus on quality over quantity

---

## Definition of Done

- [ ] Scrapy projects created for each target scraper
- [ ] Rate limiting middleware implemented and configured (5s minimum between requests)
- [ ] robots.txt parsing enabled and enforced
- [ ] Extracted data matches Neo4j schema requirements
- [ ] Error handling catches and logs all failures without crashing scraper
- [ ] Unit tests cover HTML parsing edge cases
- [ ] Integration test verifies end-to-end scrape to Neo4j import

---

## Success Metrics

- Scrape success rate: >80% requests complete successfully (excluding intentional 403s from robots.txt)
- Data quality: Extracted records match schema completeness target (>70%)
- Processing time: All pages scraped within scheduled window
- Error handling: All failures logged to monitoring endpoint with stack traces
