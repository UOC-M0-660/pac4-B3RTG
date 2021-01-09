# PARTE TEORICA

### Arquitecturas de UI: MVP, MVVM y MVI

#### MVVM

##### ¿En qué consiste esta arquitectura?
La arquitectura MVVM (Model-View-ViewModel) busca la separación de lo máximo posible de la interfaz de usuario y la lógica de la aplicación. Así pues, divide las capas de la aplicación en 3:
- Vista (View): Muestra la UI y informa a las otras capas de las acciones del usuario
- VistaModelo(ViewModel): expone la información a la vista que obtiene a partir del modelo.
- Modelo (Model): Obtiene la información de nuestro origen de datos y la expone para la VistaModelo.

La diferencia principal entre este patrón y los otros(MVP y MVC) es que este intenta  enfatizar que no haya referencias a la Vista en la VistaModelo.

##### ¿Cuáles son sus ventajas?
Sus principales ventajas respecto a los otros patrones son:
- Al manejar todos los datos desde la VistaModelo, los test unitarios son mucho más fáciles que las anteriores ya que no debemos tener referencias a las vistas
- La separación de la VistaModelo hace que no haya demasiado código en las otras capas, simplificando.

##### ¿Qué inconvenientes tiene?
Por contrapartida, este patrón puede ser muy complejo para aplicaciones con un UI muy simple.

#### MVP

##### ¿En qué consiste esta arquitectura?
La arquitectura MVP (Model-View-Presenter) tiene como objetivo la separación de capas y aumentar la testabilidad.
A diferencia de otros patrones (MVC) donde el punto de entrada es el controlador, aquí el punto de entrada será la vista. Esta se encarga de mostrar la Interfaz de usuario e informar al presenter de las acciones del usuario. El Presenter avisa al modelo para actualizar los datos y avisa a la UI de las actualizaciones que debe hacer. Por su parte, el Modelo, es la capa de datos y se encarga de la lógica de negocio.


##### ¿Cuáles son sus ventajas?
Escribe tu respuesta aquí

##### ¿Qué inconvenientes tiene?
Escribe aquí tu respuesta

#### MVI

##### ¿En qué consiste esta arquitectura?
La arquitectura MVI (Model-View-Intent) es uno de los patrones mas actuales de android. Se inspira en la unidireccionalidad y ciclica naturaleza del framework Cicle.js
Las capas que observamos y sus roles son los siguientes:
- Modelo: Representa un estado. Los modelos en MVI son inmutables para asegurar la unidireccionalidad del flujo entre ellos y las otras capas.
- Intent: Representa la intención o deseo de realizar alguna acción por el usuario. Para cada acción del usuario, la vista va a recibir un intent, que será observado por el presente y traducido a un nuevo estado en nuestro modelo.
- Vista: Son representados por los interfaces que serán implementados en mas de una actividad o fragmento.


##### ¿Cuáles son sus ventajas?
Las ventajas de este modelo son:
- Unidireccionalidad y ciclo del flujo de nuestra aplicación
- Estado consistente durante todo el ciclo de vida de nuestras vistas
- Modelos inmutables que brindan un comportamiento confiable y seguridad para subprocesos en aplicaciones grandes.


##### ¿Qué inconvenientes tiene?
El problema principal de este patrón será su curva de aprendizaje.

---

### Testing
En android los test de la aplicación los clasificamos en tres tipos:
- **Unit testing**: Son los más livianos. Testean pequeñas partes de código de nuestros componentes o clases y no necesitan ningún emulador o hardware específico para ejecutarse.
- **Integration testing**: Nos ayudan a comprobar cómo interactúa el código con las otras partes del framework de android. Normalmente se ejecutan después de completar los test unitarios de nuestros componentes. Con estos verificamos que las cosas se comportan correctamente entre los diferentes componentes.
- **UI Testing**: Son los test más largos y costosos y esto es debido a que se emula el comportamiento de las interfaces de usuario y se hace un chequeo del resultado de los mismos. Para realizarlos se necesita un emulador o un dispositivo físico.


#### ¿Qué tipo de tests se deberían incluir en cada parte de la pirámide de test? Pon ejemplos de librerías de testing para cada una de las partes. 
Cuando hablamos de **“Unit testing”** estamos hablando de aquellos test que nos ayudarán a testear sobre todo el modelo de datos. Así pues, los datos son la parte más importante a testear. En primer lugar, son los test menos costosos de los tres de la pirámide, y además, asegurarnos que la capa inferior de datos es correcta nos llevará de hecho a una menor cantidad de errores posteriormente. Además de esto, los costes de ejecución serán los más livianos. Algunas librerías para este tipos de test son **JUnit y Mockito**
Los **testeos de integración (Integration testing)**, podemos decir que son aquellos tes que validan la colaboración o interacción de un grupo de unidades. Algunos ejemplos donde deberíamos usar estos test son: 
1. las interacciones entre una vista y su modelo (como por ejemplo testear un Fragment, validar el xml del diseño o evaluar la lógica de vinculación de datos de un ViewModel).
1. Pruebas de capa de repositorio (como por ejemplo el acceso a las diferentes fuentes de datos)
1. Proporciones verticales de la app.
Testeo de fragmentos que evalúan un área específica de la app
1. Para realizar este tipo de pruebas podemos utilizar librerías como **Roboelectric** o Intents de **Espresso**

Los **UI Testing**, son los más costosos de todos los anteriores, con ellas valoraremos los flujos de trabajo de extremo a extremo, que guían al usuario a través de varios módulos y funciones. Las herramientas más utilizadas para este tipo de test son **Espresso y UI Automator**


#### ¿Por qué los desarrolladores deben centrarse sobre todo en los Unido Tests?
A medida que vamos creando los test unitarios desde el nivel inferior (Unit testing) hasta la parte superior de la piramide (UI Testing), la complejidad de los test va en aumento y el esfuerzo por mantener y depurar estos test también crece. Así pues se nos recomienda por varios factores, escribir mas test unitarios que de los otros tipos. 
En primer lugar, son menos costosos en tiempo de ejecución ya que no necesitamos ningún emulador para llevarlos a cabo. Además su implementación suele ser menos difícil.
En proporción (siempre dependiendo de la app) se nos recomienda que el 70% de los test sean de tipo unitario.


---

### Inyección de dependencias

#### Explica en qué consiste y por qué nos ayuda a mejorar nuestro código.
Las dependencias son aquellas “ataduras” que hacen que nuestras clases dependan unas de otras y nos fuerzan un comportamiento que no debería ser así entre sus relaciones y obligan a una clase que hace referencia a otra a tener un comportamiento y otro dependiendo de algo que no le pertoca.

Así pues, la inyección de dependencias lo que intenta es quitar esta relación de “atadura” para permitir que una clase que instancia a otra no se vea ligada a un comportamiento que no es parte de su lógica.

Esto nos ayudará a simplificar el código y a evitar problemas relacionados con las dependencias entre clases y componentes de nuestra app.


#### Explica cómo se hace para aplicar inyección de dependencias de forma manual a un proyecto (sin utilizar librerías externas).
Supongamos que tenemos la clase **“A”** y **“B”**.  estas clases están directamente relacionadas de forma que B contiene una instancia de B

![alt text](https://github.com/UOC-M0-660/pac4-B3RTG/blob/master/image/img_inyeccion_1.png?raw=true)

Como vemos en este caso **B**, se ve obligada a instanciar la clase **A** en su init (Cosa que no tiene nada que ver con su lógica de clase), para tener la instancia de la misma. Esto nos crea esa dependencia y nos obliga a crear una instancia de **A** para cada clase de **B**  aunque en realidad esa A sea la misma para ambas además de forzar una lógica en **B** de la cual no debería hacerse cargo.

Lo que deberíamos hacer para corregir este problema de dependencia y forzar la inyección de la misma, sería mover la propiedad “**a**” de la clase **B** a  su constructor. De esta forma lo que haremos es que la lógica de creación de la propiedad **B.a** pase al nivel superior, es decir, a aquel código que necesite B deberá crear la instancia de **A**

![alt text](https://github.com/UOC-M0-660/pac4-B3RTG/blob/master/image/img_inyeccion_2.png?raw=true)

---

### Análisis de código estático
#### Ejecutar Lint
![alt text](https://github.com/UOC-M0-660/pac4-B3RTG/blob/master/image/img_lint_1.png?raw=true)

#### Haz una lista con 5 de ellos y explica de qué problema te avisan y cómo corregirlos.

![alt text](https://github.com/UOC-M0-660/pac4-B3RTG/blob/master/image/img_lint_2.png?raw=true)
![alt text](https://github.com/UOC-M0-660/pac4-B3RTG/blob/master/image/img_lint_3.png?raw=true)

Se debe eliminar el safe call de response, no es necesario.

![alt text](https://github.com/UOC-M0-660/pac4-B3RTG/blob/master/image/img_lint_4.png?raw=true)

La función está definida como pública cuando no es usada fuera, declarar como privada.

![alt text](https://github.com/UOC-M0-660/pac4-B3RTG/blob/master/image/img_lint_5.png?raw=true)

Se debe definir el content Description, es un warning de accesibilidad.

![alt text](https://github.com/UOC-M0-660/pac4-B3RTG/blob/master/image/img_lint_6.png?raw=true)

Se deben eliminar los recursos no usados.

![alt text](https://github.com/UOC-M0-660/pac4-B3RTG/blob/master/image/img_lint_7.png?raw=true)

No es necesario el modificador “public”

![alt text](https://github.com/UOC-M0-660/pac4-B3RTG/blob/master/image/img_lint_8.png?raw=true)

Eliminar la variable que no va a ser usada y hacer un return inline

