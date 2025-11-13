# Base de Datos MySQL - RecipiesApp

Este directorio contiene la configuración de Docker para levantar una base de datos MySQL para la API de RecipiesApp.

## 🚀 Inicio Rápido

### Requisitos Previos
- Docker instalado
- Docker Compose instalado

### Levantar la Base de Datos

```bash
# Levantar los contenedores
docker-compose up -d

# Ver logs
docker-compose logs -f mysql

# Verificar que están corriendo
docker-compose ps
```

### Detener los Contenedores

```bash
# Detener
docker-compose stop

# Detener y eliminar (los datos persisten en el volumen)
docker-compose down

# Detener, eliminar y borrar datos
docker-compose down -v
```

## 📊 Servicios Incluidos

### MySQL Server
- **Host**: localhost
- **Puerto**: 3306
- **Base de Datos**: recipies_db
- **Usuario**: recipies_user
- **Contraseña**: recipies_pass
- **Usuario Root**: root
- **Contraseña Root**: root123456

### phpMyAdmin (Interfaz Web)
- **URL**: http://localhost:8080
- **Usuario**: recipies_user
- **Contraseña**: recipies_pass

phpMyAdmin te permite administrar la base de datos desde el navegador.

## 🔧 Configuración para la API

Copia el archivo `.env.example` y crea `.env` en tu proyecto de la API:

```bash
cp .env.example .env
```

Las credenciales ya están configuradas para conectarse al contenedor de MySQL.

## 📁 Estructura de Archivos

```
.
├── docker-compose.yml       # Configuración de contenedores
├── .env.example            # Variables de entorno de ejemplo
├── init-db/                # Scripts SQL de inicialización
│   └── 01-init.sql        # Script que se ejecuta al crear el contenedor
└── DATABASE_README.md      # Este archivo
```

## 🗃️ Persistencia de Datos

Los datos se almacenan en un volumen de Docker llamado `mysql_data`. Esto significa que:
- Los datos persisten incluso si detienes los contenedores
- Solo se borran si ejecutas `docker-compose down -v`

## 🔍 Comandos Útiles

```bash
# Conectarse a MySQL desde línea de comandos
docker exec -it recipies_mysql mysql -u recipies_user -precipies_pass recipies_db

# Ver bases de datos
docker exec -it recipies_mysql mysql -u recipies_user -precipies_pass -e "SHOW DATABASES;"

# Backup de la base de datos
docker exec recipies_mysql mysqldump -u recipies_user -precipies_pass recipies_db > backup.sql

# Restaurar backup
docker exec -i recipies_mysql mysql -u recipies_user -precipies_pass recipies_db < backup.sql

# Ver logs en tiempo real
docker-compose logs -f mysql

# Reiniciar MySQL
docker-compose restart mysql
```

## 🛠️ Personalización

### Cambiar Credenciales

Edita el archivo `docker-compose.yml` en la sección `environment` del servicio `mysql`:

```yaml
environment:
  MYSQL_ROOT_PASSWORD: tu_password_root
  MYSQL_DATABASE: tu_nombre_db
  MYSQL_USER: tu_usuario
  MYSQL_PASSWORD: tu_password
```

**Importante**: Si cambias las credenciales, también actualiza el archivo `.env` de tu API.

### Cambiar Puerto

Si el puerto 3306 está ocupado, cambia el mapeo de puertos:

```yaml
ports:
  - "3307:3306"  # Acceder en localhost:3307
```

## 📝 Scripts de Inicialización

Los archivos SQL en `init-db/` se ejecutan automáticamente cuando se crea el contenedor por primera vez. Puedes agregar más scripts:

```bash
init-db/
├── 01-init.sql       # Ya existe
├── 02-seeds.sql      # Agregar datos de prueba
└── 03-users.sql      # Usuarios iniciales
```

Los scripts se ejecutan en orden alfabético.

## 🐛 Troubleshooting

### El contenedor no inicia
```bash
# Ver logs detallados
docker-compose logs mysql

# Eliminar y recrear
docker-compose down -v
docker-compose up -d
```

### No puedo conectarme desde la API
1. Verifica que el contenedor esté corriendo: `docker-compose ps`
2. Verifica las credenciales en tu archivo `.env`
3. Si la API está en Docker, usa `mysql` como host en lugar de `localhost`
4. Verifica que el puerto 3306 no esté bloqueado por firewall

### Permisos en Windows/WSL
Si estás en Windows con WSL, asegúrate de que Docker Desktop esté corriendo.

## 🔗 Conectar la API

Tu API Express debe usar estas credenciales. Ejemplo con Sequelize:

```javascript
const { Sequelize } = require('sequelize');

const sequelize = new Sequelize(
  process.env.DB_NAME,
  process.env.DB_USER,
  process.env.DB_PASSWORD,
  {
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    dialect: 'mysql',
    logging: false
  }
);
```

## 📮 Acceso desde la App Android

Para que tu app Android se conecte a la API que usa esta base de datos:

1. **Desarrollo local**:
   - Usa `http://10.0.2.2:3000` (emulador Android)
   - Usa `http://TU_IP_LOCAL:3000` (dispositivo físico)

2. **Producción**: Despliega la API en un servidor con la base de datos

---

**Nota**: Este setup es para desarrollo. Para producción, considera usar servicios administrados como AWS RDS, Google Cloud SQL, o Azure Database.
