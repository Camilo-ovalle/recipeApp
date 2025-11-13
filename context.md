# Context del Proyecto Recipies App

## Información General
- **Nombre del proyecto**: recipies_app (Android Application)
- **Tipo**: Aplicación Android nativa
- **Lenguaje**: Java 
- **Compilación**: Gradle con Android SDK
- **Ubicación**: `/mnt/c/Users/tecon/StudioProjects/recipies_app`
- **Estado**: Repositorio Git no inicializado

## Configuración del Proyecto

### Configuración Android (app/build.gradle.kts)
- **Namespace**: `com.example.recipies_app`
- **Compile SDK**: 36
- **Min SDK**: 24
- **Target SDK**: 36
- **Java Version**: 11

### Dependencias Principales
- AppCompat
- Material Components
- Activity
- ConstraintLayout
- CardView
- RecyclerView
- JUnit (testing)
- Espresso (UI testing)

## Arquitectura de la Aplicación

### Actividades Implementadas
1. **SplashActivity** - Pantalla de inicio con duración de 3 segundos
   - Archivo: `app/src/main/java/com/example/recipies_app/SplashActivity.java`
   - Layout: `activity_splash.xml`
   - Función: Pantalla inicial que navega automáticamente a WelcomeActivity

2. **WelcomeActivity** - Pantalla de bienvenida
   - Archivo: `app/src/main/java/com/example/recipies_app/WelcomeActivity.java`
   - Layout: `activity_welcome.xml`
   - Funciones: 
     - Botón "Get Started" y "Skip" que navegan a HomeActivity
     - Diseño centrado con imagen de bienvenida
     - Texto: "¡Bienvenido a RecipeApp!"

3. **HomeActivity** - Pantalla principal
   - Archivo: `app/src/main/java/com/example/recipies_app/HomeActivity.java`
   - Layout: `activity_home.xml`
   - Funciones:
     - Header con saludo en español ("¡Hola!", "¿Qué cocinarás hoy?")
     - Íconos de navegación a SearchActivity y ProfileActivity
     - RecyclerViews para recetas destacadas y recientes (pendiente implementar adapters)

4. **SearchActivity** - Pantalla de búsqueda
   - Archivo: `app/src/main/java/com/example/recipies_app/SearchActivity.java`
   - Layout: `activity_search.xml`

5. **ProfileActivity** - Pantalla de perfil (COMPLETAMENTE IMPLEMENTADO)
   - Archivo: `app/src/main/java/com/example/recipies_app/ProfileActivity.java`
   - Layout: `activity_profile.xml`
   - Funciones:
     - Header con información del usuario y estadísticas
     - Navegación a "Mis Recetas" → MyRecipesActivity
     - Navegación a "Favoritas" → FavoritesActivity
     - Navegación a "Configuración" → SettingsActivity
     - Navegación a "Ayuda" → HelpActivity
     - Navegación a "Acerca de" → AboutActivity

6. **AboutActivity** - Pantalla de información de la app (NUEVO - COMPLETO)
   - Archivo: `app/src/main/java/com/example/recipies_app/AboutActivity.java`
   - Layout: `activity_about.xml`
   - Funciones:
     - Información detallada de RecipeApp
     - Versión de la app (obtenida dinámicamente)
     - Descripción de características principales
     - Objetivo de la aplicación
     - Mensaje de agradecimiento

7. **MyRecipesActivity** - Pantalla de recetas del usuario (NUEVO - COMPLETO)
   - Archivo: `app/src/main/java/com/example/recipies_app/MyRecipesActivity.java`
   - Layout: `activity_my_recipes.xml`, `item_my_recipe.xml`
   - Funciones:
     - Header con estadísticas de recetas del usuario
     - RecyclerView con lista de recetas personales
     - Estado vacío cuando no hay recetas
     - Navegación a RecipeDetailActivity al hacer click en receta
     - Adapter personalizado: MyRecipesAdapter

8. **HelpActivity** - Pantalla de ayuda y soporte (NUEVO - COMPLETO)
   - Archivo: `app/src/main/java/com/example/recipies_app/HelpActivity.java`
   - Layout: `activity_help.xml`
   - Funciones:
     - Soporte técnico con número ficticio: +1-800-RECIPES
     - Chat Bot "RecipeAssist" disponible 24/7
     - Sección de FAQ y tutoriales
     - Reportar problemas (envío de email)
     - Información de contacto completa

9. **FavoritesActivity** - Pantalla de favoritos (EXISTENTE)
   - Archivo: `app/src/main/java/com/example/recipies_app/FavoritesActivity.java`
   - Layout: `activity_favorites.xml`

10. **SettingsActivity** - Pantalla de configuración (EXISTENTE)
    - Archivo: `app/src/main/java/com/example/recipies_app/SettingsActivity.java`
    - Layout: `activity_settings.xml`

11. **RecipeDetailActivity** - Pantalla de detalle de recetas (EXISTENTE)
    - Archivo: `app/src/main/java/com/example/recipies_app/RecipeDetailActivity.java`
    - Layout: `activity_recipe_detail.xml`

12. **MainActivity** - Actividad base (no utilizada en el flujo principal)
    - Archivo: `app/src/main/java/com/example/recipies_app/MainActivity.java`
    - Layout: `activity_main.xml`

### Flujo de Navegación
```
SplashActivity (3s) → WelcomeActivity → HomeActivity
                                            ↓
                                    SearchActivity ← → ProfileActivity
                                                           ↓
                                                    ┌─ MyRecipesActivity
                                                    ├─ FavoritesActivity  
                                                    ├─ SettingsActivity
                                                    ├─ HelpActivity
                                                    └─ AboutActivity
```

### Modelos de Datos Implementados
1. **Recipe.java** - Modelo para recetas (NUEVO)
   - Propiedades: name, description, cookTime, calories, iconName, isUserRecipe
   - Getters y setters completos
   - Usado en MyRecipesActivity y MyRecipesAdapter

### Adapters Implementados
1. **MyRecipesAdapter.java** - Adapter para RecyclerView de recetas del usuario (NUEVO)
   - Extiende RecyclerView.Adapter
   - ViewHolder personalizado con binding de vistas
   - Click listeners para navegación a RecipeDetailActivity
   - Soporte para badge "Mi receta"

## Recursos de Diseño

### Colores Definidos (colors.xml)
- **Paleta Principal**: Naranja (`#FF8C00`, `#FFB347`, `#FF7700`, `#FFA500`)
- **Colores de Fondo**: Blanco, grises claros
- **Colores de Texto**: Grises oscuros para jerarquía visual

### Drawables Implementados
- Botones con estilos personalizados (primary_orange, transparent)
- Íconos para categorías de comida (breakfast, lunch, dinner, dessert)
- Íconos de navegación (search, profile, favorites)
- Fondos circulares y chips seleccionables
- Fondos para elementos de menú y búsqueda
- **Nuevos iconos agregados**:
  - `ic_time.xml` - Icono de reloj para tiempo de cocción
  - `ic_calories.xml` - Icono de llama para calorías
  - `badge_my_recipe.xml` - Badge naranja para "Mi receta"

### Layouts
- Diseños responsivos con ScrollView
- Uso de LinearLayout y orientación vertical/horizontal
- RecyclerViews configurados para listas horizontales y verticales
- Elementos de UI con padding y margins consistentes
- **Nuevos layouts implementados**:
  - `activity_about.xml` - Layout completo con información de la app
  - `activity_my_recipes.xml` - Layout con RecyclerView y estadísticas
  - `activity_help.xml` - Layout con opciones de ayuda y soporte
  - `item_my_recipe.xml` - Layout de item para RecyclerView usando CardView

## Características del Idioma
- **Idioma principal**: Español
- Textos de interfaz en español (saludos, botones, mensajes)

## Estado Actual del Desarrollo

### Completado ✅
- Estructura básica de la aplicación Android
- Configuración del proyecto y dependencias (incluyendo CardView y RecyclerView)
- Implementación de actividades principales
- Diseño de layouts básicos
- Sistema de colores y recursos drawable
- Flujo de navegación entre pantallas principales
- Configuración del manifest con todas las actividades
- **NUEVAS IMPLEMENTACIONES COMPLETADAS**:
  - **ProfileActivity** completamente funcional con navegación
  - **AboutActivity** con información completa de la app
  - **MyRecipesActivity** con RecyclerView y datos de ejemplo
  - **HelpActivity** con soporte técnico y chat bot
  - **Recipe.java** modelo de datos para recetas
  - **MyRecipesAdapter.java** adapter personalizado para RecyclerView
  - Iconos faltantes (`ic_time`, `ic_calories`, `badge_my_recipe`)
  - Navegación completa desde perfil a todas las sub-pantallas

### Pendiente por Implementar ⏳
- Adapters para RecyclerViews en HomeActivity (línea 57: TODO comment)
- Lógica de negocio para búsqueda en SearchActivity
- Funcionalidad completa de FavoritesActivity
- Funcionalidad completa de SettingsActivity
- Funcionalidad completa de RecipeDetailActivity
- Integración con base de datos o API
- Sistema de categorías de recetas más avanzado
- Funcionalidad de búsqueda real
- Implementación real del chat bot
- Persistencia de datos de recetas del usuario

### Archivos Clave para Continuar el Desarrollo
- `HomeActivity.java:57` - Implementar adapters para RecyclerViews
- `SearchActivity.java` - Implementar lógica de búsqueda
- `FavoritesActivity.java` - Implementar funcionalidad de favoritos
- `SettingsActivity.java` - Implementar configuraciones de usuario
- `RecipeDetailActivity.java` - Mejorar vista de detalle de recetas
- Integrar base de datos Room o SQLite para persistencia

## Estructura de Archivos del Proyecto
```
app/
├── src/main/
│   ├── java/com/example/recipies_app/
│   │   ├── MainActivity.java
│   │   ├── SplashActivity.java
│   │   ├── WelcomeActivity.java
│   │   ├── HomeActivity.java
│   │   ├── SearchActivity.java
│   │   ├── ProfileActivity.java (ACTUALIZADO)
│   │   ├── AboutActivity.java (NUEVO)
│   │   ├── MyRecipesActivity.java (NUEVO)
│   │   ├── HelpActivity.java (NUEVO)
│   │   ├── FavoritesActivity.java (EXISTENTE)
│   │   ├── SettingsActivity.java (EXISTENTE)
│   │   ├── RecipeDetailActivity.java (EXISTENTE)
│   │   ├── Recipe.java (NUEVO - Modelo)
│   │   └── MyRecipesAdapter.java (NUEVO - Adapter)
│   ├── res/
│   │   ├── drawable/ (30+ archivos de recursos gráficos)
│   │   ├── layout/ (10+ archivos de layouts)
│   │   │   ├── activity_about.xml (NUEVO)
│   │   │   ├── activity_my_recipes.xml (NUEVO)
│   │   │   ├── activity_help.xml (NUEVO)
│   │   │   └── item_my_recipe.xml (NUEVO)
│   │   ├── values/
│   │   │   ├── colors.xml
│   │   │   ├── strings.xml
│   │   │   └── themes.xml
│   │   └── mipmap/ (íconos de app)
│   └── AndroidManifest.xml (ACTUALIZADO)
├── build.gradle.kts (ACTUALIZADO)
└── gradle/libs.versions.toml (ACTUALIZADO)
```

## Notas Técnicas
- Aplicación configurada para usar EdgeToEdge display
- Compatibilidad con Android API 24+ (Android 7.0)
- Usar Handler para delays en SplashActivity (método deprecated, considerar actualizar)
- **SOLUCIONADO**: Dependencias CardView y RecyclerView agregadas al proyecto
- **SOLUCIONADO**: Botones de regresar funcionando en todas las actividades
- RecyclerView implementado en MyRecipesActivity con datos de ejemplo

## Funcionalidades Destacadas Implementadas
1. **Sistema de Perfil Completo**: Navegación desde ProfileActivity a 5 sub-pantallas
2. **Mis Recetas**: RecyclerView con lista personalizada y badge "Mi receta"
3. **Ayuda y Soporte**: Número de soporte ficticio (+1-800-RECIPES) y chat bot
4. **Información de App**: Pantalla About con versión dinámica y características
5. **Navegación Consistente**: Botones de regreso funcionando en todas las pantallas

## Próximos Pasos Recomendados
1. Implementar adapters para RecyclerViews en HomeActivity
2. Desarrollar la funcionalidad de búsqueda en SearchActivity
3. Implementar persistencia de datos (Room/SQLite)
4. Crear funcionalidad real para FavoritesActivity y SettingsActivity
5. Mejorar RecipeDetailActivity con información completa
6. Implementar chat bot real o WebView
7. Agregar sistema de autenticación de usuarios
8. Integrar API de recetas externa