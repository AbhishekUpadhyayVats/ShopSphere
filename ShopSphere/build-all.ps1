param(
    [switch]$StartDocker = $false
)

Write-Host "This script optionally pre-builds all microservices locally to save time avoiding downloading Maven dependencies multiple times."

$services = @(
    "eureka-service",
    "config-service",
    "api-gateway-1",
    "auth-service",
    "catalog-service",
    "order-service",
    "admin-service",
    "email-service",
    "payment-service"
)

foreach ($svc in $services) {
    Write-Host "Building $svc locally..." -ForegroundColor Cyan
    Push-Location $svc
    # Build jar locally skipping tests using Maven wrapper
    ./mvnw clean package -DskipTests
    Pop-Location
}

if ($StartDocker) {
    Write-Host "Starting Docker Compose..." -ForegroundColor Green
    docker-compose up -d --build
} else {
    Write-Host "Done. You can now start Docker Compose using: docker-compose up -d --build" -ForegroundColor Green
}