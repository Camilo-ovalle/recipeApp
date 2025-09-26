# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is **recipies_app**, a native Android application built in Java that provides a recipe management platform. The app features a Spanish-language interface and follows a traditional Android Activity-based architecture.

## Build Commands

### Development Commands
```bash
# Build the project
./gradlew build

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Clean project
./gradlew clean

# Check for dependencies updates
./gradlew dependencyUpdates
```

### Installation Commands
```bash
# Install debug APK to connected device
./gradlew installDebug

# Uninstall from device
./gradlew uninstallDebug
```

## Architecture Overview

### Application Flow
The app follows a linear navigation pattern:
```
SplashActivity (3s delay) → WelcomeActivity → HomeActivity
                                               ↓
                                      SearchActivity ←→ ProfileActivity
                                                           ↓
                                                    ┌─ MyRecipesActivity
                                                    ├─ FavoritesActivity
                                                    ├─ SettingsActivity
                                                    ├─ HelpActivity
                                                    └─ AboutActivity
```

### Key Components

**Main Activities (Completed)**:
- `SplashActivity`: Entry point with 3-second delay using deprecated Handler
- `WelcomeActivity`: Onboarding screen with skip functionality
- `HomeActivity`: Main hub with RecyclerViews (adapters not implemented)
- `ProfileActivity`: Fully functional profile hub with navigation
- `MyRecipesActivity`: RecyclerView implementation with `MyRecipesAdapter`
- `AboutActivity`: App information with dynamic version display
- `HelpActivity`: Support system with fictional contact information

**Data Models**:
- `Recipe.java`: Core recipe model with properties: name, description, cookTime, calories, iconName, isUserRecipe

**Adapters**:
- `MyRecipesAdapter`: Custom RecyclerView adapter for user recipes with CardView layouts

### Incomplete Components
- `SearchActivity`: Search functionality not implemented
- `FavoritesActivity`: Favorites management pending
- `SettingsActivity`: User preferences pending
- `RecipeDetailActivity`: Recipe detail view needs enhancement
- HomeActivity RecyclerView adapters: TODO comment at line 57

## Project Configuration

### Android Configuration
- **Namespace**: `com.example.recipies_app`
- **Compile SDK**: 36
- **Min SDK**: 24 (Android 7.0+)
- **Target SDK**: 36
- **Java Version**: 11

### Dependencies
Key libraries already included:
- AppCompat, Material Design Components
- ConstraintLayout, CardView, RecyclerView
- JUnit, Espresso for testing

## Development Guidelines

### Patterns to Follow
1. **Activity Structure**: Each activity follows the pattern: `initViews()` → `setupClickListeners()` → `setupRecyclerViews()`
2. **Spanish Language**: All user-facing text is in Spanish
3. **Navigation**: Use explicit Intent navigation between activities
4. **Back Button**: All child activities implement back navigation to ProfileActivity
5. **RecyclerView Pattern**: Horizontal layout for featured content, vertical for lists

### Known Technical Issues
1. **Deprecated Handler**: SplashActivity uses deprecated Handler.postDelayed() - consider updating to Handler.postDelayed(Runnable, long) with proper lifecycle handling
2. **Missing Adapters**: HomeActivity has configured RecyclerViews but no adapters (line 64-67)
3. **Hardcoded Data**: MyRecipesActivity uses sample data instead of persistent storage

### Next Development Priorities
1. Implement RecyclerView adapters for HomeActivity featured and recent recipes
2. Add search functionality to SearchActivity
3. Implement data persistence (Room database or SharedPreferences)
4. Complete FavoritesActivity and SettingsActivity functionality
5. Enhance RecipeDetailActivity with complete recipe information

### File Structure Notes
- All Activities are in `app/src/main/java/com/example/recipies_app/`
- Layouts follow naming convention: `activity_[name].xml`
- RecyclerView items use: `item_[name].xml`
- Spanish color scheme uses orange primary colors defined in `colors.xml`

### Testing
- Unit tests use JUnit framework
- UI tests use Espresso framework
- No custom test configurations currently implemented