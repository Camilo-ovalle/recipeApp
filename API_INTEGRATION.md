# Integración con API REST - RecipiesApp

Este documento describe cómo la aplicación Android RecipiesApp consume datos de la API REST en Express.js.

## 🔧 Configuración Realizada

### 1. Dependencias Agregadas (app/build.gradle.kts)

```kotlin
// Retrofit para consumir API REST
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")

// OkHttp para logging (debug)
implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

// Gson para parsear JSON
implementation("com.google.code.gson:gson:2.10.1")
```

### 2. Permisos (AndroidManifest.xml)

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

Y en el tag `<application>`:
```xml
android:usesCleartextTraffic="true"
```

**Nota**: `usesCleartextTraffic="true"` permite conexiones HTTP (no HTTPS) necesarias para desarrollo local.

## 📁 Estructura de Archivos Creados

```
app/src/main/java/com/example/recipies_app/
├── api/
│   ├── ApiResponse.java      # Clase genérica para respuestas de la API
│   ├── ApiService.java        # Interfaz Retrofit con endpoints
│   └── RetrofitClient.java    # Cliente singleton de Retrofit
├── Recipe.java                # Modelo actualizado con campos de la API
├── Category.java              # Modelo actualizado con id
├── Ingredient.java            # Nuevo modelo para ingredientes
└── User.java                  # Nuevo modelo para usuarios
```

## 🌐 Configuración de URL

### Para Emulador Android
La URL base está configurada como: `http://10.0.2.2:3000/`

**10.0.2.2** es una IP especial del emulador de Android que mapea al `localhost` de tu máquina host.

### Para Dispositivo Físico
Si vas a probar en un dispositivo físico conectado a la misma red WiFi:

1. Obtén tu IP local:
   ```bash
   # En Windows
   ipconfig

   # En Linux/Mac
   ifconfig
   ```

2. Cambia la URL en `RetrofitClient.java`:
   ```java
   private static final String BASE_URL = "http://192.168.1.X:3000/";
   ```
   Reemplaza `192.168.1.X` con tu IP local.

## 📡 Endpoints Implementados

El archivo `ApiService.java` contiene todos los endpoints disponibles:

### Recetas
- `GET /api/recipes` - Todas las recetas
- `GET /api/recipes/{id}` - Receta por ID con ingredientes
- `GET /api/recipes/user/{userId}` - Recetas de un usuario
- `GET /api/recipes/category/{category}` - Recetas por categoría

### Categorías
- `GET /api/categories` - Todas las categorías
- `GET /api/categories/{id}` - Categoría por ID
- `GET /api/categories/{id}/recipes` - Recetas de una categoría

### Usuarios
- `GET /api/users` - Todos los usuarios
- `GET /api/users/{id}` - Usuario por ID

### Lista de Compras
- `GET /api/shopping-list` - Todos los items
- `GET /api/shopping-list/{userId}` - Items de un usuario

### Planificador de Comidas
- `GET /api/meal-plans` - Todos los planes
- `GET /api/meal-plans/{userId}` - Planes de un usuario
- `GET /api/meal-plans/{userId}/week/{date}` - Planes de una semana

### Favoritos
- `GET /api/favorites/{userId}` - Favoritos del usuario

## 🎯 Activities Actualizadas

### CategoriesActivity
Ahora carga las categorías desde la API en lugar de datos hardcodeados.

```java
Call<ApiResponse<List<Category>>> call = RetrofitClient.getInstance()
        .getApiService()
        .getAllCategories();
```

### MyRecipesActivity
Carga las recetas del usuario (userId=1 por defecto) desde la API.

```java
Call<ApiResponse<List<Recipe>>> call = RetrofitClient.getInstance()
        .getApiService()
        .getRecipesByUser(CURRENT_USER_ID);
```

## 💡 Cómo Usar la API en Otras Activities

### Ejemplo: Cargar todas las recetas

```java
import com.example.recipies_app.api.ApiResponse;
import com.example.recipies_app.api.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Hacer la llamada
Call<ApiResponse<List<Recipe>>> call = RetrofitClient.getInstance()
        .getApiService()
        .getAllRecipes();

call.enqueue(new Callback<ApiResponse<List<Recipe>>>() {
    @Override
    public void onResponse(Call<ApiResponse<List<Recipe>>> call, Response<ApiResponse<List<Recipe>>> response) {
        if (response.isSuccessful() && response.body() != null) {
            ApiResponse<List<Recipe>> apiResponse = response.body();

            if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                List<Recipe> recipes = apiResponse.getData();
                // Usar las recetas aquí
                // Por ejemplo: actualizar RecyclerView
            } else {
                // Manejar error de la API
                Toast.makeText(context, apiResponse.getError(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFailure(Call<ApiResponse<List<Recipe>>> call, Throwable t) {
        // Manejar error de conexión
        Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
    }
});
```

## 🚀 Pasos para Probar

### 1. Levantar la API
En la carpeta de la API Express:

```bash
# Asegurarse de que el contenedor MySQL esté corriendo
docker-compose up -d

# Iniciar la API
npm run dev
```

La API debe estar corriendo en `http://localhost:3000`

### 2. Sincronizar Gradle
En Android Studio:
1. Click en **File > Sync Project with Gradle Files**
2. Esperar a que descargue las dependencias

### 3. Ejecutar la App
1. Iniciar el emulador o conectar un dispositivo físico
2. Click en **Run** (▶️) o presiona `Shift + F10`

### 4. Verificar Conexión
1. Navega a **Categorías** desde el menú
2. Si ves las 12 categorías de la base de datos, ¡la integración funciona!
3. Navega a **Mis Recetas** para ver las recetas del usuario

## 🐛 Troubleshooting

### Error: "Failed to connect to /10.0.2.2:3000"

**Problema**: La API no está corriendo o el emulador no puede alcanzarla.

**Solución**:
1. Verifica que la API esté corriendo: `curl http://localhost:3000/api/categories`
2. Asegúrate de estar usando el emulador de Android (no un dispositivo físico)
3. Revisa que la API esté en el puerto 3000

### Error: "Cleartext HTTP traffic not permitted"

**Problema**: Android bloquea conexiones HTTP por defecto.

**Solución**: Ya está configurado con `android:usesCleartextTraffic="true"` en el AndroidManifest.xml

### No se muestran los datos

**Problema**: Puede que la base de datos esté vacía.

**Solución**:
```bash
# Re-ejecutar el script SQL
docker exec -i recipies_mysql mysql -u recipies_user -precipies_pass recipies_db < init.sql
```

### Error: "Unable to resolve host"

**Problema**: Sin conexión a internet o problemas de DNS.

**Solución**:
1. Verifica que el emulador tenga acceso a internet
2. En el emulador: Settings > Network & Internet > verifica conexión

## 📊 Logs de Debug

Para ver los logs de las peticiones HTTP en Logcat:

1. Abre Logcat en Android Studio
2. Filtra por "OkHttp" para ver las peticiones
3. Verás:
   - URL de la petición
   - Headers
   - Request body
   - Response body
   - Códigos de estado HTTP

## 🔄 Próximos Pasos (TODO)

- [ ] Implementar sistema de login y guardar userId en SharedPreferences
- [ ] Agregar HomeActivity para cargar recetas destacadas
- [ ] Implementar ShoppingListActivity con la API
- [ ] Implementar MealPlannerActivity con la API
- [ ] Implementar FavoritesActivity con la API
- [ ] Agregar RecipeDetailActivity para mostrar ingredientes desde la API
- [ ] Implementar búsqueda de recetas (SearchActivity)
- [ ] Agregar indicador de carga (ProgressBar) mientras se cargan los datos
- [ ] Implementar cache local de datos (Room Database)
- [ ] Agregar manejo de errores más robusto

## 📖 Recursos

- [Retrofit Documentation](https://square.github.io/retrofit/)
- [Android Network Security Config](https://developer.android.com/training/articles/security-config)
- [Gson User Guide](https://github.com/google/gson/blob/master/UserGuide.md)

---

**Nota**: Esta integración está configurada para desarrollo. Para producción, considera:
- Usar HTTPS en lugar de HTTP
- Implementar autenticación (tokens, OAuth)
- Agregar retry policies
- Implementar cache de datos
- Manejar diferentes estados de red
