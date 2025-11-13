package com.example.recipies_app.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * Cliente singleton de Retrofit para realizar peticiones HTTP a la API
 */
public class RetrofitClient {

    // URL base de la API
    // Para emulador Android: usa 10.0.2.2 que mapea al localhost del host
    // Para dispositivo físico: cambiar por la IP local de tu máquina (ej: 192.168.1.X:3000)
    private static final String BASE_URL = "http://10.0.2.2:3000/";

    private static RetrofitClient instance;
    private final ApiService apiService;

    private RetrofitClient() {
        // Configurar logging interceptor para debug
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // Configurar encoding UTF-8 para el logger
        loggingInterceptor.redactHeader("Accept-Charset");

        // Configurar OkHttpClient con timeouts y soporte UTF-8
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chain -> {
                    // Agregar headers para soportar UTF-8
                    return chain.proceed(
                            chain.request()
                                    .newBuilder()
                                    .addHeader("Accept-Charset", "UTF-8")
                                    .addHeader("Content-Type", "application/json; charset=UTF-8")
                                    .build()
                    );
                })
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        // Configurar Gson con soporte para caracteres especiales y UTF-8
        Gson gson = new GsonBuilder()
                .setLenient()
                .disableHtmlEscaping() // No escapar caracteres especiales como á, é, í, ó, ú, ñ
                .create();

        // Configurar Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // Crear instancia del servicio
        apiService = retrofit.create(ApiService.class);
    }

    /**
     * Obtener instancia singleton
     */
    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    /**
     * Obtener el servicio de API
     */
    public ApiService getApiService() {
        return apiService;
    }

    /**
     * Método auxiliar para cambiar la URL base en tiempo de ejecución
     * Útil para testing o para dispositivos físicos
     */
    public static void setBaseUrl(String baseUrl) {
        // Nota: Este método requeriría recrear la instancia de Retrofit
        // Para simplificar, puedes cambiar la constante BASE_URL manualmente
    }
}
