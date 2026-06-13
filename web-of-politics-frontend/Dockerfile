# Stage 1: Build stage
FROM node:18-alpine AS build

WORKDIR /app

# Copy package files
COPY package.json .

# Install dependencies
RUN npm ci --silent

# Copy source code
COPY src ./src
COPY public ./public

# Build the application
RUN npm run build

# Stage 2: Serve stage (using nginx for production)
FROM nginx:alpine

# Copy built files to nginx
COPY --from=build /app/build /usr/share/nginx/html

# Expose port
EXPOSE 3000

HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget --no-verbose --tries=1 --spider http://localhost:3000/ || exit 1

CMD ["nginx", "-g", "daemon off;"]
