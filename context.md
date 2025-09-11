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

5. **ProfileActivity** - Pantalla de perfil
   - Archivo: `app/src/main/java/com/example/recipies_app/ProfileActivity.java`
   - Layout: `activity_profile.xml`

6. **MainActivity** - Actividad base (no utilizada en el flujo principal)
   - Archivo: `app/src/main/java/com/example/recipies_app/MainActivity.java`
   - Layout: `activity_main.xml`

### Flujo de Navegación
```
SplashActivity (3s) → WelcomeActivity → HomeActivity
                                            ↓
                                    SearchActivity ← → ProfileActivity
```

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

### Layouts
- Diseños responsivos con ScrollView
- Uso de LinearLayout y orientación vertical/horizontal
- RecyclerViews configurados para listas horizontales y verticales
- Elementos de UI con padding y margins consistentes

## Características del Idioma
- **Idioma principal**: Español
- Textos de interfaz en español (saludos, botones, mensajes)

## Estado Actual del Desarrollo

### Completado ✅
- Estructura básica de la aplicación Android
- Configuración del proyecto y dependencias
- Implementación de actividades principales
- Diseño de layouts básicos
- Sistema de colores y recursos drawable
- Flujo de navegación entre pantallas principales
- Configuración del manifest con todas las actividades

### Pendiente por Implementar ⏳
- Adapters para RecyclerViews en HomeActivity (línea 57: TODO comment)
- Lógica de negocio para búsqueda en SearchActivity
- Funcionalidad del perfil en ProfileActivity
- Modelos de datos para recetas
- Integración con base de datos o API
- Implementación de favoritos
- Sistema de categorías de recetas
- Funcionalidad de búsqueda real

### Archivos Clave para Continuar el Desarrollo
- `HomeActivity.java:57` - Implementar adapters para RecyclerViews
- `SearchActivity.java` - Implementar lógica de búsqueda
- `ProfileActivity.java` - Implementar funcionalidad de perfil
- Crear modelos de datos para recetas
- Crear adapters personalizados para RecyclerViews

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
│   │   └── ProfileActivity.java
│   ├── res/
│   │   ├── drawable/ (27 archivos de recursos gráficos)
│   │   ├── layout/ (6 archivos de layouts)
│   │   ├── values/
│   │   │   ├── colors.xml
│   │   │   ├── strings.xml
│   │   │   └── themes.xml
│   │   └── mipmap/ (íconos de app)
│   └── AndroidManifest.xml
└── build.gradle.kts
```

## Notas Técnicas
- Aplicación configurada para usar EdgeToEdge display
- Compatibilidad con Android API 24+ (Android 7.0)
- Usar Handler para delays en SplashActivity (método deprecated, considerar actualizar)
- RecyclerViews preparados pero sin datos de ejemplo

## Próximos Pasos Recomendados
1. Implementar modelos de datos para recetas
2. Crear adapters para los RecyclerViews
3. Implementar datos de ejemplo o mock data
4. Desarrollar la funcionalidad de búsqueda
5. Crear sistema de navegación con Bottom Navigation o Navigation Drawer
6. Implementar persistencia de datos (Room/SQLite)
7. Agregar funcionalidad de favoritos
8. Mejorar el sistema de categorías