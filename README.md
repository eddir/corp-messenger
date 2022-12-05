# corp-messenger
[![Project check](https://github.com/eddir/corp-messenger/actions/workflows/deployment.yml/badge.svg)](https://github.com/eddir/corp-messenger/actions/workflows/deployment.yml)
![Codacy grade](https://img.shields.io/codacy/grade/9edfafacb39f4a688102ee109f155140)
## Установка

```bash
git clone https://github.com/eddir/corp-messenger.git
cd corp-messenger
docker-compose up -d
```

## Использование

### База данных
```
host: localhost
port: 5432
user: messenger
password: messenger
```

### PGAdmin
http://localhost:8000
```
user: admin@messenger.rostkov.me
password: admin
```

### Spring
http://localhost:8080/api

### Веб-приложение Vue
http://localhost:8080
