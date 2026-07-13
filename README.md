# 🌐 Convertidor Universal de Grafos

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/)
[![NetBeans](https://img.shields.io/badge/NetBeans-17-orange.svg)](https://netbeans.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

## 📌 Descripción

El **Convertidor Universal de Grafos** es una aplicación Java con interfaz gráfica que permite convertir automáticamente un grafo entre sus cuatro representaciones fundamentales:

- **Matriz de Adyacencia** → **Lista de Adyacencia**
- **Matriz de Adyacencia** → **Matriz de Incidencia**
- **Matriz de Adyacencia** → **Lista de Aristas**

Además, evalúa y muestra el **consumo de memoria** de cada representación, permitiendo al usuario elegir la más eficiente.

---

## 🎯 Funcionalidades

- ✅ Ingreso de grafos mediante matriz de adyacencia
- ✅ Conversión automática a las 3 representaciones alternativas
- ✅ Cálculo del consumo de memoria en bytes
- ✅ Validación de matrices (cuadradas, simétricas, solo 0 y 1)
- ✅ Interfaz gráfica intuitiva con Java Swing
- ✅ Exportación de resultados a archivo de texto
- ✅ Botón de ejemplo para pruebas rápidas
- ✅ Recomendación de la representación más eficiente

---

## 📊 Teoría de Grafos Aplicada

| Representación | Notación | Complejidad | Uso Recomendado |
|----------------|----------|-------------|-----------------|
| Matriz de Adyacencia | O(V²) | | Grafos densos |
| Lista de Adyacencia | O(V + E) | | Grafos dispersos |
| Matriz de Incidencia | O(V × E) | | Grafos con pocas aristas |
| Lista de Aristas | O(E) | | Algoritmos de búsqueda |

Donde:
- **V** = Número de vértices
- **E** = Número de aristas

---

## 🖥️ Requisitos del Sistema

- **Java JDK 8** o superior
- **Apache NetBeans 12** o superior (recomendado)
- **Mínimo 2GB de RAM**
- **Sistema Operativo**: Windows / Linux / macOS

---

## 🚀 Instalación y Ejecución

### Clonar el repositorio

```bash
git clone https://github.com/TU_USUARIO/convertidor-grafos.git
cd convertidor-grafos
