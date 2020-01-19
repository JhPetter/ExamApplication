# ExamApplication
1. Stepper:
 Se Creo una clase llamada StepperView, la cual extiende de View, esta clase contiene el diseño y estandares para crear un stepper usando canvas, para su uso se hace referencia en el layout como custom view. Se creo diferentes atributos que te permiten personalizar el diseño y te ayudan a usarlo:
 	 * radiusStep: te permite definir el radius de cada step.
	 * selectedColor: te permite definir el color que tendra un step cuando este seleccionado.
	 * unselectedColor: te permite definir el color que tendra un step deseleccionado.
	 * textSizeFourteen: agregar una variable en el dimens, agregar el valor que representa el tamaño del texto por defecto del step.
	 * countSteps: Este atributo de la clase permite definir cuantos steps se tendra que construir.
	 * Método setSelected: recibe como parámetro la posición a donde te quieres mover.
 
 2. El Shake action: esta acción levanta un BottomSheet que contiene información de lo que el usuario debe realizar si existe un error en la aplicación. Para que funcione se tiene que mover el celular, y se configuro en el MainActivity haciendo uso del SensorManager y el acelerómetro.
 
 3. Para los proyectos que desarrollo, manejo una arquitectura en base a Clean Architecture de Robert C. Martin (Uncle Bob) (https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html). Esta arquitectura se representa en el siguiente diagrama: 
 ![Alt text](https://github.com/JhPetter/ExamApplication/blob/master/Screenshot_4.png?raw=true "Clean architecture")
 	* View: En este paquete se encuentra la definición de los Actitities y Fragments, que permiten mostrar los datos.
	* Contract: este en el contrato entre la vista y el presenter donde se definen dos interfaces, la primera se definen las peticiones de cada vista y es implementada por el presenter; en la segunda interfaz se define las respuestas de las primeras peticiones y es implementada por la vista.
	* UseCases: Este paquete contiene los casos de uso del sistema, se implementan todas las acciones a realizar hacia las entidades, ya sea guardar en una bd, consultar una lista de alguna entidad de un ws, etc. asimismo en este módulo se define las interfaces del patrón repository, estas definiciones contienen las acciones a realizar en una bd, ws, share preferences o cualquier fuente de datos que se requiera para el proyecto. Este modulo solo tiene conocimiento de las entidades.
	* Web Services: En esta capa se configura lo necesario para poder consumir los ws, además se implementan los repositorios de ws definidos en los casos de uso.
	* Base de datos: En este módulo se configura todo lo relacionado a base de datos y se implementa los repositorios de base de datos definidos en los casos de uso.
	* Share preferences: En esta capa se guardaran los valores que se requiera en la aplicación y se implementa los repositorios de sp definidos en los casos de uso.
	* Entity: Este modulo contiene todas las entidades que representan el negocio, viajaran por todos los modulos, este modulo es unico no tiene conocimiento de otros modulos.
 
 
 4. Brújula: Se realizó una clase llamada CompassView, consiste en dibujar cuatro agujas que apuntan a los diferentes puntos cardinales, en la punta de cada aguja se coloca el punto cardinal al que esta apuntando (N, S, E, W), estos ultimos estan definidos en el archivo string. Adicionalmente se integró el SensorManager que permite hacer uso del acelerometro y magnetometro del celular, los cuales nos permiten rotar la imagen en base a donde se ubica el norte.
 
