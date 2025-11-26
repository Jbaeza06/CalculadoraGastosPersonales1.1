# 💰 Calculadora de Gastos Personales: Gestor Financiero Inteligente 🚀

Bienvenido al repositorio de **Calculadora de Gastos Personales**, tu aplicación móvil para tomar el control total de tus finanzas.  
Registra ingresos y egresos, establece metas de ahorro y recibe recordatorios para mantener tu salud financiera al 100%.

---

## ✨ Características principales

Esta aplicación está diseñada para ofrecer una experiencia de gestión financiera **completa y fácil de usar**, con las siguientes funcionalidades clave:

- **📈 Registro de movimientos:** Añade rápidamente tus ingresos y gastos con categorías personalizables para un seguimiento detallado.
- **🎯 Metas de ahorro:** Define objetivos financieros y monitoriza tu progreso.
- **📅 Calendario financiero:** Visualiza tus movimientos por fecha e identifica patrones de gasto.
- **🔔 Sistema de recordatorios:** Programa recordatorios de pagos o aportes y recibe notificaciones locales.
- **👤 Autenticación segura:** Inicio de sesión mediante **Firebase Authentication**.
- **🌙 Tema oscuro/claro:** Modo de visualización adaptable.
- **📊 Interfaz intuitiva:** Diseño limpio con navegación fluida entre Inicio, Movimientos, Metas y Perfil.

---

## 🛠️ Tecnologías utilizadas

| Categoría                 | Tecnología                                                                                                                              | Descripción                                                                                                       |
|---------------------------|-----------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------|
| **Lenguaje**              | ![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)                                 | Lenguaje principal de programación Android.                                                                       |
| **Plataforma**            | ![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)                              | Sistema operativo móvil de destino.                                                                               |
| **Arquitectura**          | **MVVM**                                                                                                                               | Separación de responsabilidades con ViewModel y LiveData/Flow.                                                    |
| **Persistencia local**    | ![Room](https://img.shields.io/badge/Room-000000?style=for-the-badge&logo=android&logoColor=white)                                     | Base de datos local para movimientos, metas y recordatorios.                                                      |
| **Backend/Autenticación** | ![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)                           | Autenticación con servicios en la nube.                                                                           |
| **Notificaciones**        | **AlarmManager + BroadcastReceiver**                                                                                                   | Programación de recordatorios locales.                                                                            |
| **Dependencias**          | **Android Jetpack & Google Services**                                                                                                  | Componentes modernos de navegación, ciclo de vida, servicios, etc.                                                |
| **Compilación**           | ![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)                                 | Sistema de automatización con Kotlin DSL.                                                                         |

---

## ⚙️ Estructura del proyecto

```bash
.
├── app
│   ├── data/                 # Modelos y lógica de persistencia (Room, DAOs, Repositorios).
│   ├── ui/                   # Pantallas y adaptadores.
│   │   ├── home/             # Inicio, movimientos, metas y perfil.
│   │   ├── login/            # Autenticación.
│   │   ├── transaction/      # Creación y edición de movimientos.
│   │   └── theme/            # Temas y estilos (Dark/Light Mode).
│   └── viewmodel/            # Lógica de UI (ViewModels).
└── gradle/                   # Configuración de Gradle.
