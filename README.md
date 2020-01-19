# ExamApplication
1. Stepper:
 Secesita definir las dimenciones con el nombre textSizeFourteen, que tendra el tamaño del texto dentro del steper. 
 Se Creo una clase llamada StepperView, la cual extiende de View, esta clase contiene el diseño y estandares para crear un stepper, para su uso se hace referencia en el layout, tomando esta clase, asimismo se creo diferentes atributos que te permiten personalziar el diseño:
 	 * radiusStep: te permite definir el radius de cada step.
	 * selectedColor: te permite definir el color que tendra un step cuando este seleccionado.
	 * unselectedColor: te permite definir el color que tendra un step deseleccionado.
 
 2. El Shake action para este proyecto levanta un BottomSheet el que contiene información de lo que el usuario debe realizar si existe un error en la aplicacion. Para que funcione se tiene que mover el celular.
 
 3. Para los proyectos que desarrollo, manejo una arquitectura en base a Clean Architecture de Robert C. Martin (Uncle Bob) (https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html). Esta arquitectura se representa en el siguiente diagrama 
 
 
 4. Brujula: Se realizo una clase llamada CompassView, en la que consiste en dibujar cuatro agujas que van a estar apuntando a los diferentes puntos cardinales, en la punta de cada aguja se coloca el punto cardinal al que esta apuntando (N, S, E, W), estos ultimos estan definidos en el archivo string. Adicionalmente se integro el se SensorManager, para poder usar el acelerometro y magnetometro del celular, los cuales nos permiten rotar la imagen en base a donde se ubica el norte.
 
