# gps_conexion_serial
Aplicación en JAVA para la lectura de data de un GPS por conexion serial (arduino y dispositivo GPS con puerto serial)



#Objetivo del Proyecto:
El presente Proyecto tiene como objetivo mostrar a través de una interfaz la
información arrojada por un sensor GPS.
#Recursos:
El principal recurso es un sensor GPS. Para el desarrollo de este Proyecto, la
conexión utilizada fue serial por lo que se realizó de dos modos:
1. Modulo GPS + Arduino 1
2. GPS Magellan Meridian Gold
3. Modulo USB-ft232rl + Módulo GPS
4. Cualquier dispositivo GPS que soporte comunicación serial.
#Preparación:
Se utiliza lenguaje JAVA para el desarrollo de las interfaces, a través de
NetBeans. Y con este mismo, se realiza la conexión al sensor.
Si el sensor a utilizar es a través del Arduino, primero debe realizarse la
conexión con este, sino se conecta directamente al GPS.
Investigación sobre el protocolo NMEA, el cual define los requerimientos de
datos y tiempo de transmisión en el formato serial del dispositivo. Además define las
sentencias de datos enviadas por el GPS, por lo que es necesario conocer su
significado para una correcta interpretación.
#Justificativo:
Una de las principales razones del uso del lenguaje JAVA es por su fácil
manejo en la creación de interfaces, la cual fue necesaria para poder mostrar la
data de manera completa que se recibe del GPS. Además del uso de las bibliotecas
GiovynetDriver, la cual es utilizada para realizar conexión a puertos seriales del
computador, esta con sus respectivos drivers para arquitecturas de 32 o 64 bits, en
sistemas operativos Windows o GNU/Linux. Y uso de la biblioteca Maps.JAVA,
biblioteca correspondiente a la API de Google, para específicamente usar la
herramienta de Google Maps.
#Limitaciones:
. Para las limitaciones solo existe el hecho de la previa instalación de posibles
drivers no presentes en el proyecto, para poder realizar la conexión vía serial hacia
algunos dispositivos que lo requieran.
#Recomendaciones:
● Verificar tener todos los drivers instalados para ejecutar el Proyecto, y que los
mismos correspondan a la arquitectura utilizada (32bits o 64bits).
● Investigar sobre el protocolo NMEA.

Luis Andres Ramirez UCV - Computación
Shaira Perez UCV - Computación

Venezuela
