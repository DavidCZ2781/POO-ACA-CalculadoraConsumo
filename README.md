# Calculadora de Consumo Eléctrico

Aplicación de escritorio en Java (Swing) para registrar usuarios, calcular el
valor de su consumo mensual de energía y clasificarlo según el nivel de uso.

Actividad de Construcción Aplicada — Programación Orientada a Objetos 1.

---

## Cómo ejecutarla

**Desde NetBeans**
1. Abrir el proyecto (`File → Open Project`).
2. Clic derecho sobre `Vista/VentanaPrincipal.java` → **Run File** (Mayús+F6).

**Desde la consola**

```
javac -d salida $(find src -name "*.java")
java -cp salida Vista.VentanaPrincipal
```

Requiere **JDK 17 o superior**.

---

## Qué contiene cada paquete

| Paquete | Clase | Para qué sirve |
|---|---|---|
| `Modelo` | `Usuario` | Clase **abstracta** con lo común a todo usuario: datos, fórmula del consumo y clasificación. |
| `Modelo` | `UsuarioResidencial` | Hereda de `Usuario`. Agrega número de habitantes y el subsidio del 10 %. |
| `Modelo` | `UsuarioComercial` | Hereda de `Usuario`. Agrega rubro, NIT y el descuento del 5 %. |
| `Modelo` | `GestorUsuarios` | Administra la lista de usuarios: agregar, buscar, contar y generar el reporte. |
| `Modelo` | `Main` | Prueba del modelo por consola, sin interfaz gráfica. |
| `Vista` | `VentanaPrincipal` | La ventana. Solo muestra y recoge datos: no calcula nada. |
| `Controlador` | `ControladorPrincipal` | Une la vista con el modelo. Aquí viven las validaciones y la lógica de los botones. |
| `Utilidades` | `ValidadorEntradas` | Las validaciones del requisito 9, reunidas en un solo sitio. |
| `Utilidades` | `Formato` | Da formato de pesos a los números (`182750` → `$182.750`). |
| `Pruebas` | `PruebaVentana` | 22 pruebas automáticas que simulan el uso real de la aplicación. |

---

## Conceptos de POO aplicados (requisito 11)

| Concepto | Dónde verlo |
|---|---|
| **Clase y objeto** | Todas las clases de `Modelo`; los objetos se crean en `ControladorPrincipal.registrarUsuario()`. |
| **Atributos** | Todos `private` en `Usuario`, `UsuarioResidencial` y `UsuarioComercial`. |
| **Métodos** | `calcularValorTotal()`, `calcularClasificacion()`, `obtenerDetalles()`. |
| **Constructores** | `Usuario(...)` y los de las subclases, que lo llaman con `super(...)`. |
| **Encapsulamiento** | Atributos privados accesibles solo por *getters* y *setters*. |
| **Herencia** | `UsuarioResidencial extends Usuario`, `UsuarioComercial extends Usuario`. |
| **Abstracción** | `Usuario` es `abstract` y declara el método abstracto `obtenerInformacionAdicional()`. |
| **Polimorfismo** | Las dos subclases sobrescriben `calcularValorTotal()` y `obtenerDetalles()`. El controlador las llama sin saber de qué tipo es cada objeto. |

**Estructuras selectivas** (requisito 10): el `if / else if / else` de
`Usuario.calcularClasificacion()`.

**Estructuras repetitivas** (requisito 10): los ciclos `for` de
`GestorUsuarios.buscarPorIdentificacion()`, `generarReporte()` y
`obtenerDatosParaTabla()`.

**Arreglos**: `COLUMNAS_REPORTE` es un arreglo unidimensional y
`obtenerDatosParaTabla()` construye un arreglo bidimensional `Object[][]`
para llenar la tabla.

---

## Reglas de negocio

**Fórmula del enunciado**

```
Valor del consumo = Consumo en kWh × Valor del kWh
```

Ejemplo: 215 kWh × $850 = **$182.750**

**Clasificación por rangos (requisito 6)**

| Consumo mensual | Clasificación |
|---|---|
| Menos de 100 kWh | Consumo bajo |
| De 100 a 199 kWh | Consumo moderado |
| De 200 a 299 kWh | Consumo alto |
| 300 kWh o más | Consumo muy alto |

**Descuentos**

Además de la fórmula, cada tipo de usuario aplica su propia rebaja. Es el
aporte propio del proyecto y el mejor ejemplo de polimorfismo que tiene:

- **Residencial**: 10 % de subsidio si la vivienda tiene más de 4 habitantes.
- **Comercial**: 5 % de descuento si el negocio consume más de 300 kWh.

En pantalla se muestran las tres cifras por separado — valor del consumo,
descuento y total a pagar — para que el número del enunciado ($182.750) se
siga viendo tal cual.

**Validaciones (requisito 9)**

- La identificación y el nombre no pueden estar vacíos ni ser solo espacios.
- El consumo no puede ser negativo. Cero **sí** es válido (una casa desocupada).
- El valor del kWh tiene que ser mayor que cero.
- No se puede registrar dos veces la misma identificación.

---

## Pruebas

```
java -cp salida Pruebas.PruebaVentana
```

Ejecuta 22 pruebas sobre la ventana real: registro del ejemplo del enunciado,
registro comercial, las seis validaciones, el caso límite de 0 kWh, la búsqueda
y el reporte.
