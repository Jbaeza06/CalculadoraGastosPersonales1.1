# 📱 Calculadora de Gastos Personales

Aplicación Android desarrollada en **Kotlin** con **Jetpack Compose** para registrar, visualizar y eliminar gastos personales de manera sencilla y moderna.  
Proyecto académico creado por **Juan David Baeza Navarro**.

---

## 🚀 Características principales

✅ Registro de gastos con descripción, monto y fecha.  
✅ Visualización en lista ordenada por fecha.  
✅ Cálculo automático del total de gastos.  
✅ Opción para eliminar gastos individualmente.  
✅ Opción para limpiar todos los registros.  
✅ Interfaz desarrollada con **Material 3 (Compose)**.  
✅ Persistencia local con **Room Database**.

---

## 🧱 Estructura del proyecto

app/
├── src/main/java/com/example/gastos/
│ ├── data/ → Entidades, DAO y base de datos Room
│ ├── ui/theme/ → Temas y estilos
│ ├── MainActivity.kt → Actividad principal (Jetpack Compose)
│ └── ExpenseViewModel.kt → Lógica de manejo de gastos
├── res/ → Recursos XML (iconos, colores, strings)
└── build.gradle.kts → Configuración del módulo


---

## 🧩 Tecnologías usadas

- Kotlin (Android)
- Jetpack Compose (UI)
- Room Database (persistencia local)
- ViewModel + StateFlow
- Material Design 3

---

## 📦 Instalación y ejecución

### Opción 1️⃣ – Desde Android Studio
1. Clona este repositorio:
   ```bash
   git clone https://github.com/Jbaeza06/CalculadoraGastosPersonales1.1.git


Abre el proyecto en Android Studio.

Espera a que Gradle sincronice el proyecto.

Ejecuta el proyecto en un emulador o dispositivo físico con Android 9+.

Opción 2️⃣ – Instalando el APK

También puedes probar la app directamente descargando el archivo APK:

👉 Descargar app-release.apk

Luego:

Transfiere el APK a tu dispositivo Android.

Activa la instalación desde fuentes desconocidas.

Instálalo y abre Calculadora de Gastos Personales.

🧑‍💻 Autor

Juan David Baeza Navarro
📧 jbaeza06@uan.edu.co
Johan Andres Torres Rincon 
📧 jotorres47@uan.edu.co
