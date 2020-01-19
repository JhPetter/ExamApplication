# ExamApplication
1. Stepper:
 Secesita definir las dimenciones con el nombre textSizeFourteen, que tendra el tamaño del texto dentro del steper. 
 Se Creo una clase llamada StepperView, la cual extiende de View, esta clase contiene el diseño y estandares para crear un stepper, para su uso se hace referencia en el layout, tomando esta clase, asimismo se creo diferentes atributos que te permiten personalziar el diseño:
 	 * radiusStep: te permite definir el radius de cada step.
	 * selectedColor: te permite definir el color que tendra un step cuando este seleccionado.
	 * unselectedColor: te permite definir el color que tendra un step deseleccionado.
 
 2. El Shake action para este proyecto levanta un BottomSheet el que contiene información de lo que el usuario debe realizar si existe un error en la aplicacion. Para que funcione se tiene que mover el celular.
 
 3. Para los proyectos que desarrollo, manejo una arquitectura en base a Clean Architecture de Robert C. Martin (Uncle Bob) (https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html). Esta arquitectura se representa en el siguiente diagrama: 
 ![Alt text](https://github.com/JhPetter/ExamApplication/blob/master/Screenshot_4.png?raw=true "Clean architecture")
 	* View: En este paquete se encuentra la definicion de los Actitities y Fragmentos, que permiten mostrar los datos.
	* Contract: este en el contrato entre la vista y el presenter donde se definen dos interfaces, la primera se definen las peticiones de cada vista; en la segunda interfaz se define las respuestas a las primeras peticiones hacia la vista, esta clase es implementada por la vista.
	* UseCases: Este paquete contiene los casos de uso del sistema, aca se implementa todas las acciones a realizar hacia las entidades, ya sea guardar en una bd, consultar una lista de alguna entidad de un ws, asimismo en este modulo se define las interfaces del patron repository, estas definiciones contienen las acciones a realizar en una bd, ws, share preferences o cualquier fuente de datos que se requiera para el proyecto. Este modulo solo tiene conocimiento de las entidades.
	* Web Services: En esta capa se configura lo necesario para poder consumir los ws, e implementaran los repositorios de ws definidos en los casos de uso.
	* Base de datos: En este modulo se configura todo lo relacionado a base de datos y se implementa los repositorios de base de datos definidos en los casos de uso.
	* Share preferences: En esta capa se guardaran los valores que se requiera en la aplicacion y se implementa los repositorios de sp definidos en los casos de uso.
	* Entity: Este modulo contiene todas las entidades que representan el negocio, viajaran por todos los modulos, este modulo es unico no tiene conocimiento de otros modulos.
 
 
 4. Brujula: Se realizo una clase llamada CompassView, en la que consiste en dibujar cuatro agujas que van a estar apuntando a los diferentes puntos cardinales, en la punta de cada aguja se coloca el punto cardinal al que esta apuntando (N, S, E, W), estos ultimos estan definidos en el archivo string. Adicionalmente se integro el se SensorManager, para poder usar el acelerometro y magnetometro del celular, los cuales nos permiten rotar la imagen en base a donde se ubica el norte.
 
