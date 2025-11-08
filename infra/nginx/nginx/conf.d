upstream frontend {
  server host.docker.internal:3000;
}

upstream backend {
  server host.docker.internal:8080;
}

server {
  listen 80;
  server_name _;

  # /app → Next.js
  location / {
    proxy_pass http://frontend/app;
    proxy_http_version 1.1;
    proxy_set_header Host $host;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Proto $scheme;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection "upgrade";
  }

  # /api → 로컬 API
  location /api/ {
    proxy_pass http://backend/api/;
    proxy_set_header Host $host;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
  }

  # 정적 자산 캐시(선택)
  location ~* \.(css|js|png|jpg|jpeg|gif|svg|ico|woff2?)$ {
    expires 7d;
    add_header Cache-Control "public, max-age=604800, immutable";
  }
}