package com.webofpolitics.ratelimit;

import java.lang.annotation.*;

/**
 * Rate Limiter annotation — Marks API endpoints for rate limiting
 * <p>
 * Phase 3 Implementation: Annotate controller methods with rate limits.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {
    
    /**
     * Max requests per time window (default: 60/min)
     */
    int maxRequestsPerWindow() default 60;
    
    /**
     * Time window in seconds (default: 60s)
     */
    long timeWindowInSeconds() default 60;
}
