# Configuración UTF-8 para Caracteres Especiales (Tildes, Ñ, etc.)

Este documento explica cómo configurar correctamente UTF-8 en toda la aplicación para soportar caracteres especiales en español.

---

## ✅ **Android App - YA CONFIGURADO**

### **RetrofitClient.java**
Se ha actualizado con:

1. **Headers UTF-8** en todas las peticiones:
   ```java
   .addHeader("Accept-Charset", "UTF-8")
   .addHeader("Content-Type", "application/json; charset=UTF-8")
   ```

2. **Gson configurado** para no escapar caracteres especiales:
   ```java
   Gson gson = new GsonBuilder()
       .setLenient()
       .disableHtmlEscaping() // á, é, í, ó, ú, ñ, etc.
       .create();
   ```

---

## 🔧 **Backend (API Express.js) - VERIFICAR**

### **1. Archivo principal (index.js o app.js)**

Agrega estos middlewares al inicio:

```javascript
const express = require('express');
const app = express();

// ✅ Configurar charset UTF-8 para respuestas
app.use((req, res, next) => {
  res.setHeader('Content-Type', 'application/json; charset=utf-8');
  next();
});

// ✅ Body parser con encoding UTF-8
app.use(express.json({
  charset: 'utf-8',
  limit: '10mb'
}));
app.use(express.urlencoded({
  extended: true,
  charset: 'utf-8'
}));
```

### **2. Conexión a MySQL (Sequelize)**

Si usas **Sequelize**, configura charset en la conexión:

```javascript
const sequelize = new Sequelize(
  process.env.DB_NAME,
  process.env.DB_USER,
  process.env.DB_PASSWORD,
  {
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    dialect: 'mysql',
    charset: 'utf8mb4',           // ✅ Importante!
    collate: 'utf8mb4_unicode_ci', // ✅ Importante!
    logging: false
  }
);
```

Si usas **mysql2** directo:

```javascript
const connection = mysql.createConnection({
  host: process.env.DB_HOST,
  port: process.env.DB_PORT,
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
  charset: 'utf8mb4'  // ✅ Importante!
});
```

---

## 🗄️ **MySQL Database - VERIFICAR**

### **1. Verificar charset de la base de datos**

Conecta a MySQL y ejecuta:

```sql
SHOW CREATE DATABASE recipies_db;
```

Debe mostrar:
```sql
CREATE DATABASE `recipies_db`
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

Si no es así, ejecuta:
```sql
ALTER DATABASE recipies_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

### **2. Verificar charset de las tablas**

```sql
SELECT table_name, table_collation
FROM information_schema.tables
WHERE table_schema = 'recipies_db';
```

Si alguna tabla no tiene `utf8mb4_unicode_ci`, ejecuta:

```sql
ALTER TABLE recipes
CONVERT TO CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

ALTER TABLE categories
CONVERT TO CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

ALTER TABLE ingredients
CONVERT TO CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- Repetir para todas las tablas
```

### **3. Actualizar init.sql (para futuras instalaciones)**

En tu archivo `init-db/01-init.sql`, asegúrate de que tenga:

```sql
CREATE DATABASE IF NOT EXISTS recipies_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE recipies_db;

CREATE TABLE recipes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  -- ... otros campos
) ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_unicode_ci;

-- Repetir para todas las tablas
```

---

## 🐳 **Docker MySQL - VERIFICAR**

### **docker-compose.yml**

Agrega variables de entorno para UTF-8:

```yaml
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root123456
      MYSQL_DATABASE: recipies_db
      MYSQL_USER: recipies_user
      MYSQL_PASSWORD: recipies_pass
      # ✅ Agregar estas líneas:
      MYSQL_CHARSET: utf8mb4
      MYSQL_COLLATION: utf8mb4_unicode_ci
    command: --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
      - ./init-db:/docker-entrypoint-initdb.d
```

### **Reiniciar contenedor con nueva configuración**

```bash
# Detener y eliminar contenedores
docker-compose down

# Eliminar volumen (CUIDADO: borra datos)
docker-compose down -v

# Levantar con nueva configuración
docker-compose up -d
```

---

## 🧪 **Cómo Probar**

### **1. Insertar datos de prueba con tildes**

Ejecuta en MySQL:

```sql
INSERT INTO recipes (name, description, cook_time, calories, category, difficulty)
VALUES
  ('Paella Española', 'Deliciosa paella con mariscos y azafrán', '45 min', 650, 'almuerzo', 'media'),
  ('Arroz con Pollo', 'Plato típico con especias y vegetales', '30 min', 450, 'almuerzo', 'fácil'),
  ('Tostadas Francesas', 'Pan tostado con canela y azúcar', '15 min', 300, 'desayuno', 'fácil');
```

### **2. Verificar en la API**

```bash
curl http://localhost:3000/api/recipes
```

Debe mostrar:
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Paella Española",
      "description": "Deliciosa paella con mariscos y azafrán"
    }
  ]
}
```

✅ **NO debe mostrar**: `Paella Espa\u00f1ola` o caracteres raros

### **3. Verificar en la App Android**

1. Ejecuta la app
2. Ve a HomeActivity
3. Verifica que las recetas muestren correctamente:
   - ✅ "Paella Española"
   - ✅ "Deliciosa paella con mariscos y azafrán"
   - ❌ NO "Paella Espa?ola" ni "Paella Espa├▒ola"

---

## 🔍 **Troubleshooting**

### **Problema: Sigue mostrando caracteres raros**

1. **Limpia caché de la app**:
   ```bash
   # En Android Studio
   Build > Clean Project
   Build > Rebuild Project
   ```

2. **Reinicia la API**:
   ```bash
   # Ctrl+C para detener
   npm run dev
   ```

3. **Verifica logs de Logcat**:
   - Filtra por "OkHttp"
   - Verifica que el JSON tenga caracteres correctos

### **Problema: Funciona en la API pero no en la app**

- Reinstala la app completamente
- Verifica que `RetrofitClient.java` tenga los cambios guardados
- Sincroniza Gradle

### **Problema: Funciona en la app pero no se guarda en DB**

- Verifica charset de las tablas (paso 2 de MySQL)
- Re-inserta los datos después de convertir tablas

---

## 📋 **Checklist Final**

- [ ] ✅ Android: `RetrofitClient.java` actualizado
- [ ] Backend: Middlewares con UTF-8 configurados
- [ ] Backend: Conexión MySQL con `charset: 'utf8mb4'`
- [ ] MySQL: Base de datos con `utf8mb4_unicode_ci`
- [ ] MySQL: Tablas convertidas a `utf8mb4_unicode_ci`
- [ ] Docker: `command` con charset configurado
- [ ] Prueba: Datos insertados con tildes
- [ ] Prueba: API devuelve caracteres correctos
- [ ] Prueba: App muestra caracteres correctos

---

## 🎯 **Resumen de Cambios**

### **Android (YA HECHO)**
- ✅ `RetrofitClient.java`: Headers UTF-8 + Gson disableHtmlEscaping

### **Backend (PENDIENTE - VERIFICAR)**
- 🔧 Express: Middlewares con charset UTF-8
- 🔧 MySQL connection: charset utf8mb4

### **Database (PENDIENTE - VERIFICAR)**
- 🔧 ALTER DATABASE con utf8mb4
- 🔧 ALTER TABLES con utf8mb4
- 🔧 Docker command con charset

---

**Nota**: Los caracteres especiales (á, é, í, ó, ú, ñ, ¿, ¡) deben funcionar en todo el flujo:
`MySQL → API → Retrofit → Android UI`
